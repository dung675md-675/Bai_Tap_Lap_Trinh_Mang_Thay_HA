import java.util.*;
import java.net.*;

public class eInP6vQN{
    public static void main(String[] args){
        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress serverIP = InetAddress.getByName("36.50.135.242");
            byte[] m = ";B23DCCN205;eInP6vQN".getBytes();
            socket.send(new DatagramPacket(m,m.length,serverIP, 2207));
            
            byte[] r = new byte[1024];
            DatagramPacket R = new DatagramPacket(r,r.length);
            socket.receive(R);
            
            String k = new String(R.getData(),0,R.getLength()).trim();
            String[] t = k.split(";");
            String re = t[0];
            int n = Integer.parseInt(t[1]);
            String[] a = t[2].split(",");
            boolean[] e = new boolean[n+1];
            for(String i : a){
                e[Integer.parseInt(i)] = true;
            }
            String missing = "";
            for(int i =1;i<=n;i++){
                if(e[i] != true){
                    missing += ((missing.isEmpty())?"":",")+i;
                }
            }
            missing = re +";"+ missing;
            byte[] c = missing.getBytes();
            socket.send(new DatagramPacket(c,c.length,serverIP, 2207));
            

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}