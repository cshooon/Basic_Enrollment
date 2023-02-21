package hoon.jsp.d;

public class RegisterDTO {
    private String sid;
    private String suid;
    private String cid;

    public RegisterDTO() {}

    public RegisterDTO(String sid, String suid, String cid) {
        this.sid = sid;
        this.suid = suid;
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}
