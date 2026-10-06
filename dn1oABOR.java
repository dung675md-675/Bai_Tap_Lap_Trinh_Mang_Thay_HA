package UDP;

import java.io.*;
import java.net.*;

class Student implements Serializable {
    private static final long serialVersionUID = 20171107L;
    public String id, code, name, email;

    public Student(String code) {
        this.code = code;
    }

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }
}

public class dn1oABOR {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverIp = InetAddress.getByName("36.50.135.242");
            int serverPort = 2209;

            // a. Gửi mã sinh viên và mã câu hỏi
            String m = ";B23DCCN205;dn1oABOR";
            byte[] sendData = m.getBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, serverIp, serverPort));

            // b. Nhận dữ liệu từ server
            byte[] buffer = new byte[4096];
            DatagramPacket pa = new DatagramPacket(buffer, buffer.length);
            socket.receive(pa);

            // Tách 8 byte requestId
            byte[] reqId = new byte[8];
            System.arraycopy(buffer, 0, reqId, 0, 8);

            // Đọc đối tượng Student từ byte thứ 8
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer, 8, pa.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student st = (Student) ois.readObject();

            // c. Chuẩn hóa tên và tạo email
            String[] words = st.name.trim().toLowerCase().split("\\s+");

            // 1. Chuẩn hóa tên: chữ cái đầu viết hoa + CÓ KHOẢNG TRẮNG
            StringBuilder standardName = new StringBuilder();
            standardNam
            for (String w : words) {
                standardName.append(Character.toUpperCase(w.charAt(0)))
                            .append(w.substring(1))
                            .append(" ");
            }
            st.name = standardName.toString().trim();

            // 2. Tạo email: [tên cuối][chữ đầu họ & tên đệm]@ptit.edu.vn
            StringBuilder email = new StringBuilder(words[words.length - 1]);
            for (int i = 0; i < words.length - 1; i++) {
                email.append(words[i].charAt(0));
            }
            email.append("@ptit.edu.vn");
            st.email = email.toString();

            // Đóng gói: Tuần tự hóa Student sang mảng byte độc lập trước
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(st);
            oos.flush();
            byte[] stBytes = baos.toByteArray();

            // Ghép 8 byte requestId lên đầu, nối tiếp là stBytes
            byte[] finalData = new byte[8 + stBytes.length];
            System.arraycopy(reqId, 0, finalData, 0, 8);
            System.arraycopy(stBytes, 0, finalData, 8, stBytes.length);

            // Gửi trả server
            socket.send(new DatagramPacket(finalData, finalData.length, serverIp, serverPort));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}