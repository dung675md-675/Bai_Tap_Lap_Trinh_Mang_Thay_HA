package TCP;

import java.io.*;
import java.net.*;

class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;

    private int id;
    private String code;
    private String name;
    private String dayOfBirth; // Phải đúng tên dayOfBirth
    private String userName;

    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDayOfBirth() { return dayOfBirth; }
    public void setDayOfBirth(String dayOfBirth) { this.dayOfBirth = dayOfBirth; }
    public void setUserName(String userName) { this.userName = userName; }
}

public class Bai_10 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("36.50.135.242", 2209)) {
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // 1. Gửi chuỗi yêu cầu
            out.writeObject("B23DCCN205;B4PRI9wW");
            out.flush();

            // 2. Nhận đối tượng
            Customer cus = (Customer) in.readObject();

            // 3. Chuẩn hóa họ tên & username
            // Cần toLowerCase() để xử lý triệt để chữ hoa lộn xộn
            String[] p = cus.getName().trim().toLowerCase().split("\\s+");
            int n = p.length;

            String lastName = p[n - 1].toUpperCase();
            String userName = "";
            String middle = "";

            for (int i = 0; i < n - 1; i++) {
                middle += Character.toUpperCase(p[i].charAt(0)) + p[i].substring(1) + " ";
                userName += p[i].charAt(0);
            }
            userName += p[n - 1]; // Đã toLowerCase từ đầu nên phần tên này là chữ thường

            cus.setName(lastName + ", " + middle.trim());
            cus.setUserName(userName);

            // 4. Chuẩn hóa ngày sinh mm-dd-yyyy -> dd/mm/yyyy
            String[] d = cus.getDayOfBirth().split("-");
            cus.setDayOfBirth(d[1] + "/" + d[0] + "/" + d[2]);

            // 5. Gửi lại đối tượng đã sửa đổi
            out.writeObject(cus);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}