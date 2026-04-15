/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author ASUS
 */
import java.io.FileOutputStream;
import java.io.IOException;

public class NewClass {
    public static void main(String[] args) {
        String fileName = "T:/text.txt";
        int fileSize = 10 * 1024; // 10KB = 10,240 byte
        byte[] data = new byte[fileSize];

        // Ghi dữ liệu bất kỳ (ví dụ: toàn là ký tự 'A')
        for (int i = 0; i < fileSize; i++) {
            data[i] = 'A';
        }

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(data);
            System.out.println("Đã tạo file " + fileName + " với kích thước đúng 10KB.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}