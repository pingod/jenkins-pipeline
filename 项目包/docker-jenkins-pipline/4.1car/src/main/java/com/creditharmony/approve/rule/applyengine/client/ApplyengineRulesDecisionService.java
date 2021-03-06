package com.creditharmony.approve.rule.applyengine.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 3.0.0-milestone2
 * 2016-04-06T14:46:43.578+08:00
 * Generated source version: 3.0.0-milestone2
 * 
 */
@WebService(targetNamespace = "http://www.ibm.com/rules/decisionservice/Applyengine_ruleapp/Applyengine_rules", name = "Applyengine_rulesDecisionService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ApplyengineRulesDecisionService {

    @WebResult(name = "Applyengine_rulesResponse", targetNamespace = "http://www.ibm.com/rules/decisionservice/Applyengine_ruleapp/Applyengine_rules", partName = "Applyengine_rulesResponse")
    @WebMethod(operationName = "Applyengine_rules", action = "Applyengine_rules")
    public ApplyengineRulesResponse applyengineRules(
        @WebParam(partName = "Applyengine_rulesRequest", name = "Applyengine_rulesRequest", targetNamespace = "http://www.ibm.com/rules/decisionservice/Applyengine_ruleapp/Applyengine_rules")
        ApplyengineRulesRequest applyengineRulesRequest
    ) throws ApplyengineRulesSoapFault;
}
