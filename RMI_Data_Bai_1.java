package RMI;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMI_Data_Bai_1 {
    public static void main(String[] args){
        String msv ="B23DCCN205", qCode="dvtgYdb2", serverIP ="36.50.135.242";
        try{
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
            DataService service = (DataService) registry.lookup("RMIDataService");
            //cau b
            int[] a =(int[]) service.requestData(msv, qCode);
            //cau c
            List<Integer> d = new ArrayList<>();
            int n= a.length;
            if(n == 1){
                d.add(1);
            }else {
                for(int i =0;i<n;i++){
                    if(i==0){
                        if(a[i] >a[i+1]){
                            d.add(i+1);
                        }
                    } else if(i== n-1){
                        if(a[i] >a[i-1]){
                            d.add(i+1);
                        }
                    } else {
                        if(a[i]>a[i-1]&&a[i]>a[i+1]){
                            d.add(i+1);
                        }
                    }
                }
            }
            service.submitData(msv, qCode, d);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
