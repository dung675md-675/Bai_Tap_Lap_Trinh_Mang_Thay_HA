package RMI;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMI_Character_BAI_2{
    public static void main(String[] args){
        String msv = "B23DCCN205", qCode = "CBKVFq0N", serverIP = "36.50.135.242";
        try{
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            //cau a
            String a = service.requestCharacter(msv, qCode);
            //cau b
            int num = Integer.parseInt(a);
            StringBuilder sb = new StringBuilder();
            int[] value = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
            String[] symbol = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
            for(int i =0;i<value.length;i++){
                while(num >= value[i]){
                    num -= value[i];
                    sb.append(symbol[i]);
                }
            }
            service.submitCharacter(msv, qCode, sb.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}