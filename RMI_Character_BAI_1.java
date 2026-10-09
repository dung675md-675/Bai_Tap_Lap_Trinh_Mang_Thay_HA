package RMI;
import java.net.*;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMI_Character_BAI_1{
    public static void main(String[] args){
        String qCode = "7OKjHZ9y", msv = "B23DCCN205", serverIP = "36.50.135.242";
        try{
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            //cau a
            String a = service.requestCharacter(msv, qCode);
            //cau b
            URI uri = new URI(null,null,a,null);
            String result = uri.toASCIIString();
            //cau c
            service.submitCharacter(msv, qCode, result);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}