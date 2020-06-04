package com.creditharmony.approve.phone.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class DhzhDhbrshp extends DataEntity<DhzhDhbrshp> {
	
	private static final long serialVersionUID = 1L;
	
	private String rCustomerCoborrowerId;
	private String homeTel;
	private String dhgxshAssessResult;
	private String isRepeat;
	private String isInPool;
	private String createBy;
	private Date createTime;
	private Date modifyTime;
	private String modifyBy;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.r_customer_coborrower_id
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.r_customer_coborrower_id
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getrCustomerCoborrowerId() {
        return rCustomerCoborrowerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.r_customer_coborrower_id
     *
     * @param rCustomerCoborrowerId the value for jk.t_jk_dhzh_dhbrshp.r_customer_coborrower_id
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setrCustomerCoborrowerId(String rCustomerCoborrowerId) {
        this.rCustomerCoborrowerId = rCustomerCoborrowerId == null ? null : rCustomerCoborrowerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.home_tel
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.home_tel
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getHomeTel() {
        return homeTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.home_tel
     *
     * @param homeTel the value for jk.t_jk_dhzh_dhbrshp.home_tel
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel == null ? null : homeTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.dhgxsh_assess_result
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.dhgxsh_assess_result
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getDhgxshAssessResult() {
        return dhgxshAssessResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.dhgxsh_assess_result
     *
     * @param dhgxshAssessResult the value for jk.t_jk_dhzh_dhbrshp.dhgxsh_assess_result
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setDhgxshAssessResult(String dhgxshAssessResult) {
        this.dhgxshAssessResult = dhgxshAssessResult == null ? null : dhgxshAssessResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.is_repeat
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.is_repeat
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getIsRepeat() {
        return isRepeat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.is_repeat
     *
     * @param isRepeat the value for jk.t_jk_dhzh_dhbrshp.is_repeat
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat == null ? null : isRepeat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.is_in_pool
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.is_in_pool
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getIsInPool() {
        return isInPool;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.is_in_pool
     *
     * @param isInPool the value for jk.t_jk_dhzh_dhbrshp.is_in_pool
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setIsInPool(String isInPool) {
        this.isInPool = isInPool == null ? null : isInPool.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.create_by
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.create_by
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.create_by
     *
     * @param createBy the value for jk.t_jk_dhzh_dhbrshp.create_by
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.create_time
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.create_time
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.create_time
     *
     * @param createTime the value for jk.t_jk_dhzh_dhbrshp.create_time
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.modify_time
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.modify_time
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.modify_time
     *
     * @param modifyTime the value for jk.t_jk_dhzh_dhbrshp.modify_time
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jk.t_jk_dhzh_dhbrshp.modify_by
     *
     * @return the value of jk.t_jk_dhzh_dhbrshp.modify_by
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jk.t_jk_dhzh_dhbrshp.modify_by
     *
     * @param modifyBy the value for jk.t_jk_dhzh_dhbrshp.modify_by
     *
     * @mbggenerated Wed Mar 02 20:17:19 CST 2016
     */
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }
}