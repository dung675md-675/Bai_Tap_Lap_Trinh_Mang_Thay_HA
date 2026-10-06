package UDP;

import java.io.*;
import java.net.*;

// 1. Định nghĩa lớp UDP.Customer đúng theo yêu cầu đề bài
class Customer implements Serializable {
    private static final long serialVersionUID = 20151107L;
    public String id, code, name, dayOfBirth, userName;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }
}

public class b3DXRM2oG {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverIp = InetAddress.getByName("36.50.135.242"); 
            int serverPort = 2209;

            // BƯỚC 1: Gửi mã sinh viên và mã câu hỏi
            String sendMsg = ";B23DCCN205;3DXRM2oG";
            byte[] sendData = sendMsg.getBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, serverIp, serverPort));

            // BƯỚC 2: Nhận dữ liệu từ Server (8 byte requestId + Đối tượng Customer)
            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);

            // Tách 8 byte đầu lấy requestId
            byte[] reqId = new byte[8];
            System.arraycopy(buffer, 0, reqId, 0, 8);

            // Đọc đối tượng Customer từ byte thứ 8 trở đi
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer, 8, receivePacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Customer customer = (Customer) ois.readObject();

            // BƯỚC 3: Xử lý dữ liệu
            // 3.1. Chuẩn hóa tên và tạo userName
            String[] words = customer.name.trim().toLowerCase().split("\\s+");
            int n = words.length;

            // a. Chuẩn hóa tên: TÊN_IN_HOA, Họ Và Tên Đệm (Viết hoa chữ cái đầu)
            // Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
            StringBuilder formattedName = new StringBuilder();
            formattedName.append(words[n - 1].toUpperCase()).append(", ");
            for (int i = 0; i < n - 1; i++) {
                formattedName.append(Character.toUpperCase(words[i].charAt(0)))
                             .append(words[i].substring(1))
                             .append(" ");
            }
            customer.name = formattedName.toString().trim();

            // b. Tạo userName: chữ cái đầu của họ/tên đệm + tên đầy đủ (in thường)
            // Ví dụ: nguyen van hai duong -> n + v + h + duong = nvhduong
            StringBuilder uName = new StringBuilder();
            for (int i = 0; i < n - 1; i++) {
                uName.append(words[i].charAt(0));
            }
            uName.append(words[n - 1]);
            customer.userName = uName.toString();

            // c. Đổi định dạng ngày sinh từ mm-dd-yyyy sang dd/mm/yyyy
            // Ví dụ: 10-11-2012 -> 11/10/2012
            String[] dateParts = customer.dayOfBirth.trim().split("-");
            // dateParts[0] là mm, dateParts[1] là dd, dateParts[2] là yyyy
            customer.dayOfBirth = dateParts[1] + "/" + dateParts[0] + "/" + dateParts[2];

            // BƯỚC 4: Đóng gói và gửi trả Server
            // Tuần tự hóa đối tượng Customer thành mảng byte riêng
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(customer);
            oos.flush();
            byte[] custBytes = baos.toByteArray();

            // Ghép 8 byte requestId lên đầu + custBytes ở sau
            byte[] finalData = new byte[8 + custBytes.length];
            System.arraycopy(reqId, 0, finalData, 0, 8);
            System.arraycopy(custBytes, 0, finalData, 8, custBytes.length);

            // Bắn gói tin về server
            socket.send(new DatagramPacket(finalData, finalData.length, serverIp, serverPort));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}