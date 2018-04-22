package sjj.bean;

import org.nutz.dao.entity.annotation.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
@Table("user")
public class User extends BaseBean {
    /**
     * 主键id
     */
    @Id
    @org.nutz.dao.entity.annotation.Id
    @GeneratedValue(generator = "JDBC")
    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 员工编号
     */
    private String uno;

    /**
     * 员工姓名
     */
    private String fullname;

    /**
     * 部门id
     */
    private Integer did;
    @Transient
    private String deptName;
    /**
     * 职务
     */
    private Integer jid;

    /**
     * 直属上级uid
     */
    private Integer superior;
    @Transient
    private String superiorName;
    @Transient
    private String jnames;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * email
     */
    private String email;

    /**
     * 0未禁用，1已禁用
     */
    private Integer isDisabled;
    @Transient
    private String state;

    /**
     * 0未删除，1已删除
     */
    private Integer isDeleted;

    /**
     * 最近登录时间
     */
    private Date loginTime;

    private Date insTime;
    private Date updTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getSuperior() {
        return superior;
    }

    public void setSuperior(Integer superior) {
        this.superior = superior;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getInsTime() {
        return insTime;
    }

    public void setInsTime(Date insTime) {
        this.insTime = insTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getJnames() {
        return jnames;
    }

    public void setJnames(String jnames) {
        this.jnames = jnames;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

}