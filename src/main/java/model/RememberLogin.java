package model;

public class RememberLogin {
    private String idAccount;
    private String ipAddress;

    public RememberLogin(String idAccount, String ipAddress) {
        this.idAccount = idAccount;
        this.ipAddress = ipAddress;
    }

    public RememberLogin() {
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
