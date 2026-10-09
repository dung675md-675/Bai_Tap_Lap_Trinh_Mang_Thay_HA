package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Locale;

public class RMI_Data_Bai_2 {
    public static void main(String[] args) {
        String serverIP = "36.50.135.242"; 
        int port = 1099;
        String studentCode = "B23DCCN205";  
        String qCode = "M5DWmcxt";          

        try {
            
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            DataService service = (DataService) registry.lookup("RMIDataService");

            
            String csvData = (String) service.requestData(studentCode, qCode);
            String[] parts = csvData.trim().split(",");
            int n = parts.length;
            double[] arr = new double[n];

            double sum = 0.0;
            for (int i = 0; i < n; i++) {
                arr[i] = Double.parseDouble(parts[i].trim());
                sum += arr[i];
            }

            
            double avg = sum / n;

            double varianceSum = 0.0;
            for (double val : arr) {
                varianceSum += Math.pow(val - avg, 2);
            }
            double stddev = Math.sqrt(varianceSum / n);

            Arrays.sort(arr);
            int p95Index = (int) Math.ceil(n * 0.95) - 1;
            double p95 = arr[p95Index];

            String result = String.format(
                Locale.US,
                "average=%.2f;stddev=%.2f;p95=%.2f",
                avg, stddev, p95
            );

            service.submitData(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}