import java.net.*;
import java.util.*;

public class cKuhlpSN {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);
            InetAddress serverIP = InetAddress.getByName("36.50.135.242");
            int serverPort = 2208;

            // 1. Gửi bản tin khởi tạo
            String a = ";B23DCCN205;cKuhlpSN";
            byte[] t = a.getBytes();
            DatagramPacket rp = new DatagramPacket(t, t.length, serverIP, serverPort);
            socket.send(rp);

            // 2. Nhận dữ liệu từ server
            byte[] k = new byte[1024];
            DatagramPacket rr = new DatagramPacket(k, k.length);
            socket.receive(rr);

            String re = new String(rr.getData(), 0, rr.getLength()).trim();
            String[] parts = re.split(";", 2);
            String req = parts[0];
            String data = parts[1];

            // 3. Xử lý tìm ký tự xuất hiện nhiều nhất
            Map<Character, Integer> dem = new HashMap<>();
            Map<Character, String> po = new HashMap<>();
            char mc = ' ';
            int mi = 0;

            for (int i = 0; i < data.length(); i++) {
                char ch = data.charAt(i);
                int pos = i + 1;

                // Nối chuỗi vị trí
                po.put(ch, po.getOrDefault(ch, "") + pos + ",");

                // Cập nhật số lần đếm
                int d = dem.getOrDefault(ch, 0) + 1;
                dem.put(ch, d);

                // Cập nhật ký tự xuất hiện nhiều nhất
                if (d > mi) {
                    mi = d;
                    mc = ch;
                }
            }

            // 4. Đóng gói kết quả và gửi lại server
            String kq = req + ";" + mc + ":" + po.get(mc);
            byte[] outData = kq.getBytes();
            DatagramPacket sendResult = new DatagramPacket(outData, outData.length, serverIP, serverPort);
            socket.send(sendResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}