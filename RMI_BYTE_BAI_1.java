package RMI;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMI_BYTE_BAI_1{
    public static void main(String[] args){
        String serverIP = "36.50.135.242";
        String qCode = "MBwqpfl0";
        String msv = "B23DCCN205";
        int port = 1099;
        try{
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            //cau a
            byte[] inputData = service.requestData(msv, qCode);
            //cau b 
            Map<Byte,Integer> fmap = new LinkedHashMap<>();
            for(byte b : inputData){
                fmap.put(b, fmap.getOrDefault(b, 0)+1);
            }
            byte minb = inputData[0];
            int minc = Integer.MAX_VALUE;
            for(Map.Entry<Byte,Integer> entry : fmap.entrySet()){
                if(entry.getValue()<minc){
                    minc = entry.getValue();
                    minb = entry.getKey();
                }
            } 
            byte[] result = new byte[] {minb,(byte) minc};
            //cau c
            service.submitData(msv, qCode, result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}