
package com.creditharmony.approve.rule.channelconfig.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{http://www.ibm.com/rules/decisionservice/Channelconfig_ruleapp/Channelconfig_rule}channelConfigParam"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "params"
})
@XmlRootElement(name = "params", namespace = "http://www.ibm.com/rules/decisionservice/Channelconfig_ruleapp/Channelconfig_rule/param")
public class Params {

    @XmlElement(required = true)
    protected ChannelConfigParam params;

    /**
     * Gets the value of the params property.
     * 
     * @return
     *     possible object is
     *     {@link ChannelConfigParam }
     *     
     */
    public ChannelConfigParam getParams() {
        return params;
    }

    /**
     * Sets the value of the params property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChannelConfigParam }
     *     
     */
    public void setParams(ChannelConfigParam value) {
        this.params = value;
    }

}
