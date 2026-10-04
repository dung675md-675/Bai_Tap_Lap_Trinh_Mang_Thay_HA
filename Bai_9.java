package TCP;

import java.util.*;
import java.net.*;
import java.io.*;

class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;
    int id;
    String code;
    String name;
    int quantity;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
}

public class Bai_9 {
    public static void main(String args[]) {
        // 1. Đúng cổng 2209
        try (Socket socket = new Socket("36.50.135.242", 2209)) {
            socket.setSoTimeout(5000);

            // 2. Tạo OutputStream trước và flush để tránh nghẽn luồng
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Câu a: Gửi chuỗi định dạng studentCode;qCode
            out.writeObject("B23DCCN205;dWUFMWz5");
            out.flush();

            // Câu b: Nhận đối tượng Laptop
            Laptop laptop = (Laptop) in.readObject();

            // Câu c: Sửa tên (dùng regex "\\s+")
            String[] words = laptop.name.trim().split("\\s+");
            if (words.length > 1) {
                String tcm = words[0];
                words[0] = words[words.length - 1];
                words[words.length - 1] = tcm;
                laptop.name = String.join(" ", words);
            }

            // Đảo ngược số lượng
            String r = new StringBuilder(String.valueOf(laptop.quantity)).reverse().toString();
            laptop.quantity = Integer.parseInt(r);

            // Câu d: Gửi đối tượng Laptop bằng writeObject
            out.writeObject(laptop);
            out.flush();

        } catch (Exception e) { // Bắt Exception để bao gồm cả ClassNotFoundException
            e.printStackTrace();
        }
    }
}