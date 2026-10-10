package RMI;
public class TicketSla implements java.io.Serializable{
    private static final long serialVersionUID = 20260517L;
    
    private String ticketId, priority, action;
    private boolean breached;
    private int openedHoursAgo;
    public TicketSla(){}
    public TicketSla(String ticketId, String priority, int openedHoursAgo){
        this.ticketId = ticketId;
        this.priority = priority;
        this.openedHoursAgo = openedHoursAgo;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isBreached() {
        return breached;
    }

    public void setBreached(boolean breached) {
        this.breached = breached;
    }

    public int getOpenedHoursAgo() {
        return openedHoursAgo;
    }

    public void setOpenedHoursAgo(int openedHoursAgo) {
        this.openedHoursAgo = openedHoursAgo;
    }
    
}