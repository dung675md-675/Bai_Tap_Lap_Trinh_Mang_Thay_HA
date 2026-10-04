import java.util.*;
import java.io.*;
import java.net.*;

public class Bai_8{
    public static void main(String args[]){
        try(Socket socket = new Socket("36.50.135.242",2207)){
            socket.setSoTimeout(5000);
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //cau a
            out.writeUTF("B23DCCN205;JJ3SivEc");
            out.flush();
            //cau b
            String receivedStr = in.readUTF();
            int s = in.readInt();
            //cau c
            StringBuilder result = new StringBuilder();
            int t = s%26;
            for(char c : receivedStr.toCharArray()){
                if(Character.isLowerCase(c)){
                    char d = (char) ((c - 'a' - t + 26)%26 +'a');
                    result.append(d);
                } else if (Character.isUpperCase(c)){
                    char d = (char) ((c - 'A' - t + 26)%26 + 'A');
                    result.append(d);
                }else {
                    result.append(c);
                }
            }
            String k = result.toString();
            out.writeUTF(k);
            out.flush();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}