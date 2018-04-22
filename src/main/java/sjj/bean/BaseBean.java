package sjj.bean;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class BaseBean {
	@Transient
	protected Integer isEditable;
	@Transient
	protected Integer pass;// 1通过，2拒绝
	@Transient
	protected String passContent;// 审批意见
	@Transient
	protected List<String> approveInfos;
	@Transient
	private Integer isDeleted;
	@Transient
	private Integer isFW;// 是否是法务

	/**
	 * 操作人id
	 */
	@Transient
	private Integer operator;

	/**
	 * 操作人名称
	 */
	@Transient
	private String operatorName;
	/**
	 * 插入时间
	 */
	@Transient
	private Date insTime;
	/**
	 * 更新时间
	 */
	@Transient
	private Date updTime;

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getInsTime() {
		return insTime;
	}

	public void setInsTime(Date insTime) {
		this.insTime = insTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Integer getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Integer isEditable) {
		this.isEditable = isEditable;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public String getPassContent() {
		return passContent;
	}

	public void setPassContent(String passContent) {
		this.passContent = passContent;
	}

	public List<String> getApproveInfos() {
		return approveInfos;
	}

	public void setApproveInfos(List<String> approveInfos) {
		this.approveInfos = approveInfos;
	}
	public Integer getIsFW() {
		return isFW;
	}

	public void setIsFW(Integer isFW) {
		this.isFW = isFW;
	}

}
