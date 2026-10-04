import java.net.*;

public class EbxCl9Bq{
    public static void main(String[] args){
        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress ip = InetAddress.getByName("36.50.135.242");
            //cau a
            String mGui1 = ";B23DCCN205;EbxCl9Bq";
            byte[] dataGui1 = mGui1.getBytes();
            DatagramPacket pGui1 = new DatagramPacket(dataGui1,dataGui1.length,ip, 2207);
            socket.send(pGui1);
            //cau b
            byte[] buffer = new byte[2048];
            DatagramPacket pNhan = new DatagramPacket(buffer,buffer.length);
            socket.receive(pNhan);
            String mNhan = new String(pNhan.getData(),0,pNhan.getLength()).trim();
            //cau c
            String[] parts = mNhan.split(";");
            String r = parts[0];
            String[] a = parts[1].split(",");
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (String i : a){
                int x = Integer.parseInt(i.trim());
                if(x > max) max = x;
                if(x < min) min = x;
            }
            String mGui2 = r +";"+max+","+min;
            byte[] dataGui2 = mGui2.getBytes();
            DatagramPacket pGui2 = new DatagramPacket(dataGui2, dataGui2.length, pNhan.getAddress(),pNhan.getPort());
            socket.send(pGui2);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}