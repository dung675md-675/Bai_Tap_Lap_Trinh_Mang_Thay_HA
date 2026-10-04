import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class TCP_ByteStream {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2206;
        String studentCode = "B23DCCN205"; 
        String qCode = "vZPR86H3";

        // Sử dụng try-with-resources để tự động đóng Socket (Bước d)
        try (Socket socket = new Socket(host, port)) {
            // Cài đặt thời gian giao tiếp tối đa là 5s
            socket.setSoTimeout(5000);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // Bước a: Gửi mã sinh viên và mã câu hỏi
            String request = studentCode + ";" + qCode;
            os.write(request.getBytes());
            os.flush();
            System.out.println("Đã gửi yêu cầu: " + request);

            // Bước b: Nhận dữ liệu từ server
            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);
            if (bytesRead != -1) {
                String receivedData = new String(buffer, 0, bytesRead).trim();
                System.out.println("Dữ liệu nhận được: " + receivedData);

                // Bước c: Tìm khoảng cách nhỏ nhất và hai số lớn nhất tạo nên khoảng cách đó
                String[] strNumbers = receivedData.split(",");
                int[] numbers = new int[strNumbers.length];
                for (int i = 0; i < strNumbers.length; i++) {
                    numbers[i] = Integer.parseInt(strNumbers[i].trim());
                }

                // Sắp xếp mảng tăng dần để dễ dàng đối chiếu
                Arrays.sort(numbers);

                int minDiff = Integer.MAX_VALUE;
                int num1 = 0, num2 = 0;

                // Do mảng đã sắp xếp, khoảng cách nhỏ nhất sẽ nằm giữa 2 phần tử kề nhau
                for (int i = 0; i < numbers.length - 1; i++) {
                    int diff = numbers[i + 1] - numbers[i];
                    
                    // Dùng <= để đảm bảo lấy được cặp số có giá trị lớn nhất 
                    // nếu có nhiều cặp mang cùng khoảng cách nhỏ nhất
                    if (diff <= minDiff) {
                        minDiff = diff;
                        num1 = numbers[i];
                        num2 = numbers[i + 1];
                    }
                }

                // Gửi kết quả lên server
                String response = minDiff + "," + num1 + "," + num2;
                System.out.println("Kết quả gửi đi: " + response);
                os.write(response.getBytes());
                os.flush();
            }

        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra trong quá trình kết nối: " + e.getMessage());
        }
    }
}