/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author nguyenducthao
 */
public class DESEncryptExample {

    public static SecretKey secretkey(String pass) throws Exception {
        byte[] passbyte = pass.getBytes();
        DESKeySpec keyspec = new DESKeySpec(passbyte);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey Deskey = keyFactory.generateSecret(keyspec);
        return Deskey;
    }

    public static void desEncrypt(String f1, String f2, String pass) throws Exception {
        SecretKey DesKey = secretkey(pass);

        // Create a cipher
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, DesKey);

        // Read and encrypt file.
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f1));
        CipherOutputStream out = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(f2)), cipher);

        // Read the file and encrypt
        int i;
        do {
            i = in.read();
            if (i != -1) {
                out.write(i);
            }
        } while (i != -1);
        in.close();
        out.close();
    }

    public static void desDecrypt(String f1, String f2, String pass) throws Exception {
        // Open the key file -- It must be in the current directory.
        SecretKey DesKey = secretkey(pass);

        // Create a cipher
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, DesKey);

        // Read and decrypt file.
        CipherInputStream in = new CipherInputStream(new BufferedInputStream(new FileInputStream(f1)), cipher);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f2));
        int i;
        do {
            i = in.read();
            if (i != -1) {
                out.write(i);
            }
        } while (i > 0);
        in.close();
        out.close();
    }

    public static String desDecrypt_File(String f1, Charset charset, String pass) throws Exception {
        // Open the key file -- It must be in the current directory.
        SecretKey DesKey = secretkey(pass);

        // Create a cipher
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, DesKey);

        // Read and decrypt file.
        CipherInputStream in = new CipherInputStream(new BufferedInputStream(new FileInputStream(f1)), cipher);

        try (Scanner scanner = new Scanner(in, charset.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    public static void main(String[] args) {
        File file = new File("");
        String help = "Su dung DES de ma hoa tap tin";
        System.out.println(help);
        try {
            desEncrypt(file.getAbsolutePath() + "/src/lib/SQL/CreateTables.txt", file.getAbsolutePath() + "/src/lib/SQL/CreateTablesEnCrypt.txt", "DoanThanhNhan");
            desDecrypt(file.getAbsolutePath() + "/src/lib/SQL/CreateTablesEnCrypt.txt", file.getAbsolutePath() + "/src/lib/SQL/plaintext1.txt", "DoanThanhNhan");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}

/*
Ví dụ về thuật toán Secret-key:
Yêu cầu lập trình  : 
- Dùng 1 mật mã (password) tùy thích để mã hóa nội dung trong tập tin plaintext.txt 
và nội dung mã hóa được lưu lại trong tập tin cipher.txt. 
- Cũng dùng mật mã trên để giải mã nội dung trong tập tin cipher.txt 
và lưu nội dung giải mã này trong tập tin plaintext1.txt

Thuật toán DES :
a. Tạo 1 DES Key dựa trên password :
Password chỉ là 1 chuỗi ký tự bình thường nên tính bảo mật không cao. 
Do đó, khi sử dụng một thuật toán mã hóa mào đó, password phải được mã hóa theo một dạng nhất định
được gọi là Key, trong trường hợp sử dụng thuật toán DES nên gọi là DES Key.
    byte[] passbyte = pass.getBytes();
    DESKeySpec keyspec = new DESKeySpec(passbyte);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey Deskey = keyFactory.generateSecret(keyspec);

Các lớp của gói JCE sẽ được trình bày chi tiết ở những phần sau, 
nên tạm thời chúng ta chỉ cần nói đại khái cách tạo ra 1 DesKey:
- Đầu tiên  pasword được chuyển thành mảng byte.
- Sử dụng class DESKeySpec tạo ra DES Key .
- Sử dụng  class SecretKeyFactory để chỉ ra thuật toán nào dùng cho mã hóa key.
- class SecretKey chứa kết quả của quá trình mã hóa key .  
Lưu ý : chuỗi password phải ít nhất 8 kí tự trở lên.

b. Mã hóa nội dung tập tin plaintext.txt: 
Lựa chọn thuật toán mã hóa DES, tiêu chuẩn ECB, padding là PKCS5Padding
Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
Chọn phương thức mã hóa
cipher.init(Cipher.ENCRYPT_MODE, DesKey);
Ghi nội dung mã hóa vào tập tin ciphertext.txt
CipherOutputStream out = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream("ciphertext.txt")),cipher);

c. Giải mã nội dung tập tin ciphertext.txt: 
Lựa chọn thuật toán mã hóa DES, tiêu chuẩn ECB, padding là PKCS5Padding
Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
Chọn phương thức mã hóa
cipher.init(Cipher.DECRYPT_MODE, DesKey);
Đọc tập tin được mã hóa ciphertext.txt
CipherInputStream in = new CipherInputStream(new BufferedInputStream(new FileInputStream("ciphertext.txt")),cipher);
 */
