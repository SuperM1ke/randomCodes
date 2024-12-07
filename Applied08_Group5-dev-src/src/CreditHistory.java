import java.util.Date;

/**
 * This Class is for credit history.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class CreditHistory {
    public String memberEmail;
    public Date datetime;
    public WClass wClass;
    public CreditsUpdateAction action;

    public CreditHistory() {}

    public CreditHistory(String memberEmail, Date datetime, WClass wClass, CreditsUpdateAction action) {
        this.memberEmail = memberEmail;
        this.datetime = datetime;
        this.wClass = wClass;
        this.action = action;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public WClass getwClass() {
        return wClass;
    }

    public void setwClass(WClass wClass) {
        this.wClass = wClass;
    }

    public CreditsUpdateAction getAction() {
        return action;
    }

    public void setAction(CreditsUpdateAction action) {
        this.action = action;
    }
}
