import java.io.*;
import java.net.Socket;

public class Bai_4 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("36.50.135.242", 2208)) {
            socket.setSoTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // cau a
            out.write("B23DCCN205;FPtch9f0");
            out.newLine();
            out.flush();

            // cau b
            String s = in.readLine();

            // cau c
            int[] count = new int[128];
            for (char c : s.toCharArray()) count[c]++;

            StringBuilder res = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (Character.isLetterOrDigit(c) && count[c] > 1) {
                    res.append(c).append(":").append(count[c]).append(",");
                    count[c] = 0; // Đánh dấu để không in lại ký tự trùng phía sau
                }
            }

            out.write(res.toString() + "\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}