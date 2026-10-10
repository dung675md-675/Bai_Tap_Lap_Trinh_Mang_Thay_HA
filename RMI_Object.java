package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMI_Object {
    public static void main(String[] args){
        String serverIP = "36.50.135.242";
        String msv = "B23DCCN205";
        String qCode = "DR9fjr6N";
        try{
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
            
            TicketSla ticket = (TicketSla) service.requestObject(msv,qCode);
            String p = ticket.getPriority();
            int hours = ticket.getOpenedHoursAgo();
            boolean isBreached = false;
            if("CRITICAL".equalsIgnoreCase(p) && hours > 2){
                isBreached = true;
            } else if("HIGH".equalsIgnoreCase(p) && hours>8 ){
                isBreached = true;
                
            } else  if("MEDIUM".equalsIgnoreCase(p) && hours > 24){
                isBreached = true;
            } else if("LOW".equalsIgnoreCase(p) && hours > 72){
                isBreached = true;
            }
            ticket.setBreached(isBreached);
            
            if(!isBreached){
                ticket.setAction("MONITOR");
            } else{
                if("CRITICAL".equalsIgnoreCase(p) || hours > 96){
                    ticket.setAction("ESCALATE_L2");
                } else {
                    ticket.setAction("ESCALATE_L1");
                }
            }
            service.submitObject(msv, qCode, ticket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
