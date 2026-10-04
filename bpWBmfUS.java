import java.net.*;
import java.util.*;

public class bpWBmfUS {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000); // Thêm timeout tránh treo vô hạn nếu rớt gói tin
            InetAddress serverIP = InetAddress.getByName("36.50.135.242");

            // a. Gửi thông điệp khởi tạo
            String tn = ";B23DCCN205;bpWBmfUS"; // Chú ý kiểm tra lại đúng mã SV
            byte[] ttn = tn.getBytes();
            DatagramPacket d = new DatagramPacket(ttn, ttn.length, serverIP, 2208);
            socket.send(d);

            // b. Nhận thông điệp từ server
            byte[] r = new byte[1024];
            DatagramPacket rt = new DatagramPacket(r, r.length);
            socket.receive(rt);

            String a = new String(rt.getData(), 0, rt.getLength()).trim();
            String[] b = a.split(";", 2);
            String requestId = b[0];
            String rawData = b[1].trim();

            // c. Chuẩn hóa chuỗi
            String[] words = rawData.split("\\s+");
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                String w = words[i].toLowerCase();
                if (!w.isEmpty()) {
                    result.append(Character.toUpperCase(w.charAt(0)));
                    if (w.length() > 1) {
                        result.append(w.substring(1));
                    }
                    if (i < words.length - 1) {
                        result.append(" ");
                    }
                }
            }

            // Gộp requestId và chuỗi đã chuẩn hóa theo định dạng requestId;data
            String re = requestId + ";" + result.toString();
            byte[] rre = re.getBytes();

            // Gửi lại đúng địa chỉ và port mà server vừa phản hồi
            DatagramPacket kk = new DatagramPacket(rre, rre.length, rt.getAddress(), rt.getPort());
            socket.send(kk);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}