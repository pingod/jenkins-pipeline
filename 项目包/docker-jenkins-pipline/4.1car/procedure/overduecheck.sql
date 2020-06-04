DROP FUNCTION jk.overduecheck();

CREATE OR REPLACE FUNCTION jk.overduecheck()
RETURNS void AS
$BODY$
BEGIN
--更新期供表
UPDATE JK.T_JK_PAYBACK_MONTH M SET IS_OVERDUE='1',MONTH_OVERDUE_DAYS=(CURRENT_DATE-B.MONTH_PAY_DAY::DATE),DICT_MONTH_STATUS='1',MODIFY_TIME=NOW(),MODIFY_BY='OVERDUE_BATCH',
				   MONTH_INTEREST_PUNISHSHOULD=COALESCE(B.MONTH_INTEREST_PUNISHSHOULD,0)+ROUND(CASE WHEN C.CONTRACT_VERSION>='4' AND (CURRENT_DATE-B.MONTH_PAY_DAY::DATE)=1 
				                               		THEN (COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)-COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)-COALESCE(B.MONTH_INTEREST_PAYACTUAL,0))*0.103
				                               WHEN C.CONTRACT_VERSION>='4' THEN (COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)-COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)-COALESCE(B.MONTH_INTEREST_PAYACTUAL,0))*0.003
								               ELSE (COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)-COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)-COALESCE(B.MONTH_INTEREST_PAYACTUAL,0))*0.003
							                   END,2),--罚息
				   MONTH_PENALTY_SHOULD=ROUND(CASE WHEN C.CONTRACT_VERSION<'4' AND COALESCE(B.MONTH_PENALTY_SHOULD,0)=0 AND (COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)-COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)-COALESCE(B.MONTH_INTEREST_PAYACTUAL,0))>1000 
				                                   THEN (COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)-COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)-COALESCE(B.MONTH_INTEREST_PAYACTUAL,0))*0.1
                                                   WHEN C.CONTRACT_VERSION<'4' AND COALESCE(B.MONTH_PENALTY_SHOULD,0)=0 AND (COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)-COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)-COALESCE(B.MONTH_INTEREST_PAYACTUAL,0))<=1000 THEN 100
                                                   WHEN C.CONTRACT_VERSION<'4' AND COALESCE(B.MONTH_PENALTY_SHOULD,0)>0 THEN B.MONTH_PENALTY_SHOULD
							            ELSE B.MONTH_PENALTY_SHOULD END,2),--违约金
				   MONTH_LATE_FEE=COALESCE(B.MONTH_LATE_FEE,0)+ROUND(CASE WHEN C.CONTRACT_VERSION>='4' AND (CURRENT_DATE-B.MONTH_PAY_DAY::DATE)=1 THEN (COALESCE(B.MONTH_FEE_SERVICE,0)-COALESCE(B.ACTUAL_MONTH_FEE_SERVICE,0))*0.103
				                                         WHEN C.CONTRACT_VERSION>='4' THEN (COALESCE(B.MONTH_FEE_SERVICE,0)-COALESCE(B.ACTUAL_MONTH_FEE_SERVICE,0))*0.003
						       			ELSE 0 END,2),--滞纳金
				   OVERDUETIME=NOW(),
				   OVERDUECOLUMNBAK=CONCAT_WS(',',B.IS_OVERDUE,B.MONTH_OVERDUE_DAYS,B.DICT_MONTH_STATUS,B.MONTH_INTEREST_PUNISHSHOULD,B.MONTH_PENALTY_SHOULD,B.MONTH_LATE_FEE)
FROM JK.T_JK_PAYBACK A INNER JOIN JK.T_JK_PAYBACK_MONTH B ON A.CONTRACT_CODE = B.CONTRACT_CODE 
                                AND A.EFFECTIVE_FLAG='1' AND (A.DICT_PAY_STATUS='0' OR A.DICT_PAY_STATUS='1')
                                AND (CURRENT_DATE-B.MONTH_PAY_DAY::DATE)>0 AND (B.DICT_MONTH_STATUS='0' OR B.DICT_MONTH_STATUS='1')
                                AND ((COALESCE(B.MONTH_PAY_AMOUNT,0)+COALESCE(B.MONTH_INTEREST_BACKSHOULD,0)+COALESCE(B.MONTH_FEE_SERVICE,0))-(COALESCE(B.MONTH_CAPITAL_PAYACTUAL,0)+COALESCE(B.MONTH_INTEREST_PAYACTUAL,0)+COALESCE(B.ACTUAL_MONTH_FEE_SERVICE,0)))>0
					   INNER JOIN JK.T_JK_CONTRACT C ON A.CONTRACT_CODE=C.CONTRACT_CODE
WHERE B.CONTRACT_CODE=M.CONTRACT_CODE AND B.MONTHS=M.MONTHS;
--更新还款主表
UPDATE JK.T_JK_PAYBACK M SET PAYBACK_MAX_OVERDUEDAYS=CASE WHEN COALESCE(PAYBACK_MAX_OVERDUEDAYS,0)<COALESCE(A.MAX_MONTH_OVERDUE_DAYS,0) THEN COALESCE(A.MAX_MONTH_OVERDUE_DAYS,0) ELSE COALESCE(PAYBACK_MAX_OVERDUEDAYS,0) END,
                             DICT_PAY_STATUS='1',MODIFY_BY='OVERDUE_BATCH',MODIFY_TIME=NOW()                           
FROM (SELECT A.CONTRACT_CODE,MAX(B.MONTH_OVERDUE_DAYS) MAX_MONTH_OVERDUE_DAYS
      FROM JK.T_JK_PAYBACK A INNER JOIN JK.T_JK_PAYBACK_MONTH B ON A.CONTRACT_CODE = B.CONTRACT_CODE 
                                AND A.EFFECTIVE_FLAG='1' AND (A.DICT_PAY_STATUS='0' OR A.DICT_PAY_STATUS='1')
                                AND (CURRENT_DATE-B.MONTH_PAY_DAY::DATE)>0 AND B.DICT_MONTH_STATUS='1'
      GROUP BY A.CONTRACT_CODE) A 
WHERE A.CONTRACT_CODE=M.CONTRACT_CODE;
--更新借款信息表
UPDATE JK.T_JK_LOAN_INFO M SET DICT_LOAN_STATUS='88',MODIFY_BY='OVERDUE_BATCH',MODIFY_TIME=NOW()
FROM JK.T_JK_PAYBACK A INNER JOIN JK.T_JK_CONTRACT B ON A.CONTRACT_CODE=B.CONTRACT_CODE AND A.EFFECTIVE_FLAG='1' AND DICT_PAY_STATUS='1'
WHERE B.LOAN_CODE=M.LOAN_CODE;
END;
$BODY$
  LANGUAGE plsrsql VOLATILE
  COST 100;
ALTER FUNCTION jk.overduecheck()
  OWNER TO srdb;