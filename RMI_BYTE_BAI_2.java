package RMI;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_BYTE_BAI_2{
    public static void main(String[] args){
        String qCode = "Psonbew1";
        String serverIP = "36.50.135.242";
        String msv = "B23DCCN205";
        int port = 1099;
        try{
            Registry registry =  LocateRegistry.getRegistry(serverIP, port);
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            //
            byte[] inputData = service.requestData(msv, qCode);
            Map<Byte,Integer> fmap = new LinkedHashMap<>();
            byte maxb = inputData[0];
            int maxc = Integer.MIN_VALUE;
            for(byte b : inputData){
                fmap.put(b, fmap.getOrDefault(b, 0)+1);
            }
            for(Map.Entry<Byte,Integer> entry : fmap.entrySet() ){
                if(entry.getValue() > maxc){
                    maxc = entry.getValue();
                    maxb = entry.getKey();
                }
            }
            byte[] result = new byte[] {maxb,(byte) maxc};
            service.submitData(msv, qCode, result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}