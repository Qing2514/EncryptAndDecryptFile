package com.file;

import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/***
 * Des文件加密解密
 */
public class StrategyTwo implements EncryptAndDecrypt {

    private String KEYSTR = "TWO";
    private String ENCRYPTION = ".encryptedByDES";

    public StrategyTwo() {
        genKey(KEYSTR);
        initCipher();
    }

    private Key key;
    // 解密密码
    private Cipher cipherDecrypt;
    // 加密密码
    private Cipher cipherEncrypt;


    @Override
    public boolean encryptFile(File file) {
        //check input file
        if (!file.exists()) {
            System.out.println("File does not exist");
            return false;
        }

        String srcFileName = file.getName();
        System.out.println(srcFileName + " isn't been encrypted");
        try {
            InputStream is = new FileInputStream(srcFileName);
            OutputStream out = new FileOutputStream(srcFileName + ENCRYPTION);

            CipherInputStream cis = new CipherInputStream(is, cipherEncrypt);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
            file.delete();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean decryptFile(File file) {
        //check input file
        if (!file.exists()) {
            System.out.println("File does not exist");
            return false;
        }

        String fileName = file.getName();
        System.out.println(fileName + " was encrypted by DES");
        File outFile = new File(fileName.substring(0, fileName.lastIndexOf(".")));
        try {
            InputStream is = new FileInputStream(fileName);
            CipherInputStream cis = new CipherInputStream(is, cipherDecrypt);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cis));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile.getName()));
            String line;
            while ((line = reader.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            reader.close();
            cis.close();
            is.close();
            bw.close();
            file.delete();
            //LOG.info("文件{}解密完成", fileName);
        }
        catch (Exception e) {
            //LOG.error("解密文件{}出现异常", fileName, e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    private void initCipher() {
        try {
            // 加密的cipher
            cipherEncrypt = Cipher.getInstance("DES");
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, key);
            // 解密的cipher
            cipherDecrypt = Cipher.getInstance("DES");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //可以使用此类来根据一个字节数组构造一个 SecretKey
    public void genKey(String keyRule) {
        // Key key = null;
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
    }

}