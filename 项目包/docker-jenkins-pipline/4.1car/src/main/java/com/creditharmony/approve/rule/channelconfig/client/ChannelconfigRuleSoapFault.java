
package com.creditharmony.approve.rule.channelconfig.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.3.2
 * 2016-03-11T14:56:54.414+08:00
 * Generated source version: 2.3.2
 * 
 */

@WebFault(name = "Channelconfig_ruleException", targetNamespace = "http://www.ibm.com/rules/decisionservice/Channelconfig_ruleapp/Channelconfig_rule")
public class ChannelconfigRuleSoapFault extends Exception {
    public static final long serialVersionUID = 20160311145654L;
    
    private com.creditharmony.approve.rule.channelconfig.client.ChannelconfigRuleException channelconfigRuleException;

    public ChannelconfigRuleSoapFault() {
        super();
    }
    
    public ChannelconfigRuleSoapFault(String message) {
        super(message);
    }
    
    public ChannelconfigRuleSoapFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelconfigRuleSoapFault(String message, com.creditharmony.approve.rule.channelconfig.client.ChannelconfigRuleException channelconfigRuleException) {
        super(message);
        this.channelconfigRuleException = channelconfigRuleException;
    }

    public ChannelconfigRuleSoapFault(String message, com.creditharmony.approve.rule.channelconfig.client.ChannelconfigRuleException channelconfigRuleException, Throwable cause) {
        super(message, cause);
        this.channelconfigRuleException = channelconfigRuleException;
    }

    public com.creditharmony.approve.rule.channelconfig.client.ChannelconfigRuleException getFaultInfo() {
        return this.channelconfigRuleException;
    }
}
