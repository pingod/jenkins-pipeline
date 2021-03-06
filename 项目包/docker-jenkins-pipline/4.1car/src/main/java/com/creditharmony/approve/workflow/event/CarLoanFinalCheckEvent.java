package com.creditharmony.approve.workflow.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.approve.base.service.CarCommonService;
import com.creditharmony.approve.carloan.dao.CarLoanInfoDao;
import com.creditharmony.approve.carloan.dao.CustomerConsultationDao;
import com.creditharmony.approve.carloan.entity.CarAuditResult;
import com.creditharmony.approve.carloan.entity.CarLoanInfo;
import com.creditharmony.approve.carloan.service.CarAuditResultService;
import com.creditharmony.approve.carloan.service.CarContractVersionService;
import com.creditharmony.approve.carloan.service.CarLoanInfoService;
import com.creditharmony.approve.carloan.view.CarVerifyBusinessView;
import com.creditharmony.approve.carloan.view.FlowProperties;
import com.creditharmony.approve.workflow.constants.CarLoanCheckRouteConstants;
import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;

/**
 * 终审处理流程回调
 * @Class Name CarLoanFinalCheckEvent
 * @author 申诗阔
 * @Create In 2016年2月1日
 */
@Service("Car_Loan_Final_Check_Flow")
public class CarLoanFinalCheckEvent extends CarCommonService implements ExEvent {

	@Autowired
	private CarLoanInfoService carLoanInfoService;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	@Autowired
	private CarAuditResultService carAuditResultService;
	@Autowired
	private CustomerConsultationDao customerConsultationDao;
	@Autowired
	private CarContractVersionService contractVers;

	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		String response = workItem.getResponse();
		// 取出在controller中放入的业务数据，需要向下转换
		CarVerifyBusinessView flowView = (CarVerifyBusinessView) workItem.getBv();
		CarLoanInfo loanInfo = carLoanInfoDao.findByApplyId(flowView.getApplyId());	// 借款信息
		String isextensionId = loanInfo.getIsextension() != null ? loanInfo.getIsextension() : "";
		String stepName = workItem.getStepName();
		flowView.setAuditTime(new Date());
		if (response.equals(CarLoanCheckRouteConstants.BACK_RECHECK)) { // 退回复审
			String loanCode = flowView.getLoanCode();
			CarLoanInfo carLoanInfo = carLoanInfoService.findByLoanCode(loanCode);
			if (CarLoanStatus.SUPPLY_PENDING_FINAL_AUDIT.getCode().equals(carLoanInfo.getDictLoanStatus())) {
				flowView.setDictStatus(CarLoanStatus.SUPPLY_BACK_PENDING_FINAL_AUDIT.getCode());	// 设置借款状态
				flowView.setOperResultName(CarLoanOperateResult.SUPPLY_FINALCHECK_BACK_SUPPLY_RECHECK.getCode());
			} else {
				flowView.setDictStatus(CarLoanStatus.FINAL_AUDIT_BACK.getCode());	// 设置借款状态
				flowView.setOperResultName(CarLoanOperateResult.FINALCHECK_BACK_RECHECK.getCode());
			}
			redTopBack(workItem, flowView, CarLoanSteps.FINAL_AUDIT.getName());
		} else if (response.equals(CarLoanCheckRouteConstants.FINAL_CHECK_PASS)){ // 终审通过到待审核费率
			String loanCode = flowView.getLoanCode();
			flowView.setContractVersion(contractVers.getFlowContractVersion(loanCode, isextensionId));
			flowView.setDictStatus(CarLoanStatus.AUDIT_INTEREST_RATE.getCode());
			flowView.setOperResultName(CarLoanOperateResult.TO_INTEREST_RATE.getCode());
			flowView.setAuditBorrowProductName(CarLoanProductType.parseByCode(flowView.getAuditBorrowProductCode()).getName());
			redTopCommit(workItem, flowView, stepName);
		} else if (response.equals(CarLoanCheckRouteConstants.TO_AUDIT_CONTRACT)){ // 补传终审通过到合同审核
			flowView.setDictStatus(CarLoanStatus.PENDING_CONTRACT_REVIEW.getCode());
			flowView.setTimeOutFlag("0");
			flowView.setOperResultName(CarLoanOperateResult.SUPPLY_FINALCHECK_TO_CONTRACT_REVIEW.getCode());
			String loanCode = flowView.getLoanCode();
			CarAuditResult carRet = carAuditResultService.selectLastByLoanCodeAndCheckType(loanCode, CarLoanSteps.FINAL_AUDIT.getCode());
			if (carRet != null) {
				flowView.setAuditAmount(carRet.getAuditAmount());
				flowView.setAuditBorrowProductCode(carRet.getDictProductType());
				flowView.setAuditLoanMonths(carRet.getDictAuditMonths());
				flowView.setGrossRate(carRet.getGrossRate());
				flowView.setFirstServiceTariffing(carRet.getFirstServiceTariffing());
				flowView.setFinalEvaluatedPrice(carRet.getFinalEvaluatedPrice());
			}
			redTopCommit(workItem, flowView, stepName);
		} else if (response.equals(CarLoanCheckRouteConstants.FINAL_CHECK_PASS_CONDICTION)){ // 终审附条件通过到待审核费率
			String loanCode = flowView.getLoanCode();
			flowView.setContractVersion(contractVers.getFlowContractVersion(loanCode, isextensionId));			
			flowView.setDictStatus(CarLoanStatus.AUDIT_INTEREST_RATE.getCode());
			flowView.setOperResultName(CarLoanOperateResult.FINALCHECK_CONDITION_TO_INTEREST_RATE.getCode());
			flowView.setAuditBorrowProductName(CarLoanProductType.parseByCode(flowView.getAuditBorrowProductCode()).getName());
			redTopCommit(workItem, flowView, stepName);
		}  else if (response.equals(CarLoanCheckRouteConstants.FINAL_CHECK_REFUSED)){ // 终审拒绝 结束
			flowView.setDictStatus(CarLoanStatus.FINAL_AUDIT_REJECT.getCode());
			flowView.setOperResultName(CarLoanOperateResult.FINALCHECK_REFUSE.getCode());
		} else if(response.equals(CarLoanCheckRouteConstants.FINAL_CHECK_ABANDON)){ // 客户放弃
			if ("车借展期流程".equals(workItem.getFlowName())) {
				flowView.setDictStatus(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
			} else {
				flowView.setDictStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			}
			flowView.setOperResultName(CarLoanOperateResult.FINALCHECK_GIVEUP.getCode());
		}
		auditHandle(flowView, stepName);
	}
	
	// 标红置顶退回业务
	private void redTopBack(WorkItemView workItem, CarVerifyBusinessView bv,
			String cureentStep) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
					|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
							.equals(flowProperties.getFirstBackSourceStep())) {
				bv.setFirstBackSourceStep(cureentStep);
			}
			bv.setOrderField("0," + sdf.format(new Date()));
		}
	}

	// 标红置顶提交业务
	private void redTopCommit(WorkItemView workItem, CarVerifyBusinessView bv,
			String currentStepName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (currentStepName.equals(flowProperties.getFirstBackSourceStep())) {
				bv.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
				bv.setOrderField("1," + sdf.format(new Date()));
			} else {
				if (StringUtils
						.isEmpty(flowProperties.getFirstBackSourceStep())
						|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
								.equals(flowProperties.getFirstBackSourceStep())) {
					bv.setOrderField("1," + sdf.format(new Date()));
				} else {
					bv.setOrderField("0," + sdf.format(new Date()));
				}
			}
		}
	}
}
