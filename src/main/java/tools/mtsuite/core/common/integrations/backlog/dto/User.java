package tools.mtsuite.core.common.integrations.backlog.dto;

public class User {

    private Long id;
    private String userId;
    private String name;
    private Integer roleType;
    private String lang;
    private String mailAddress;
    private NulabAccount nulabAccount;
    private String keyword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public NulabAccount getNulabAccount() {
        return nulabAccount;
    }

    public void setNulabAccount(NulabAccount nulabAccount) {
        this.nulabAccount = nulabAccount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
