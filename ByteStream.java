package tcp;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ByteStream {
    public static void main(String[] args){
        String serverHost = "36.50.135.242";
        int serverPort = 2206;
        String studentCode ="B23DCCN205";
        String qCode ="BDW4cAiQ";
        
        try(Socket socket = new Socket(serverHost, serverPort)){
            socket.setSoTimeout(5000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            //cau a
            String request = studentCode + ";" + qCode;
            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();
            //cau b
            byte[] buffer =new byte[1024];
            int bytesRead = in.read(buffer);
            if(bytesRead == -1){
                return;
            }
            String receivedData = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
            
            //cau c
            String[] parts = receivedData.split(",");
            int[] nums = new int[parts.length];
            for ( int i = 0; i < parts.length; i ++){
                nums[i] = Integer.parseInt(parts[i].trim());
            }
            
            int max1 = Integer.MIN_VALUE;
            for(int num : nums){
                if(num > max1){
                    max1 = num;
                }
            }
            
            int max2 = Integer.MIN_VALUE;
            int pos = -1;
            for(int i =0; i<nums.length;i++ ){
                if (nums[i] <max1 && nums[i] > max2){
                    max2 = nums[i];
                    pos = i;
                }
            }
            
            String response = max2 +" ,"+pos;
            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch ( Exception e){
            e.printStackTrace();
        }
    }
}