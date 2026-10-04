import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    // Thêm throws Exception vào hàm main để tránh lỗi biên dịch IO
    public static void main(String args[]) throws Exception {
        
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        // a. Gửi mã sinh viên và mã câu hỏi (Dùng out.write, thêm newLine và flush)
        out.write("B23DCCN205;J7WD6fne\n");
        out.flush();
        
        // b. Nhận chuỗi từ server (Dùng in.readLine)
        String receivedStr = in.readLine();
        System.out.println("Nhan tu server: " + receivedStr);
        
        // c. Lọc các domain .edu và gửi lại
        if (receivedStr != null && !receivedStr.isEmpty()) {
            String domains[] = receivedStr.split(",\\s*");
            List<String> eduDomain = new ArrayList<>();
            
            for (String domain : domains) {
                // Đã sửa thêm dấu đóng ngoặc tròn ')'
                if (domain.trim().endsWith(".edu")) {
                    eduDomain.add(domain.trim());
                }
            }
            String result = String.join(", ", eduDomain);
            
            // Gửi kết quả lên server
            out.write(result);
            out.newLine();
            out.flush(); // Bắt buộc phải có flush để đẩy dữ liệu đi
            System.out.println("Da gui: " + result);
        }
        
        // d. Đóng các luồng và socket[cite: 1]
        in.close();
        out.close();
        socket.close();
    }
}