import java.io.*;
import java.net.*;
import java.util.*;

public class Bai_5{
    public static void main(String args[]){
        try(Socket socket = new Socket("36.50.135.242",2207)){
            socket.setSoTimeout(5000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out= new DataOutputStream(socket.getOutputStream());
            //cau a
            out.writeUTF("B23DCCN205;b00kB8qn");
            out.flush();
            //cau b
            int a = in.readInt();
            int b = in.readInt();
            
            //cau c
            int sum = a + b;
            int mul = a*b;
            out.writeInt(sum);
            out.writeInt(mul);
            out.flush();
            
           
            
        }catch( IOException e){
            e.printStackTrace();
        }
        
        
    }
}