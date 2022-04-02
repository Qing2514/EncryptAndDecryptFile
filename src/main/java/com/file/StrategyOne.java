package com.file;

import java.io.*;
import java.io.File;

// 使用Java的二进制IO，对文件每个字节的数据进行修改，从而达到加密的目的。默认的加密方法是每个字节的数据+5，解密方法则是-5。
// 不用担心数字的值超过255而溢出，超过之后的值会减256从而回到0~255。
public class StrategyOne implements EncryptAndDecrypt {

    private int OFFSET = 5;
    private String ENCRYPTION = ".encryptedByASCLL";

    public StrategyOne() {
    }

    @Override
    public boolean encryptFile (File file){
        return changeFile(file, OFFSET, ENCRYPTION);
    }

    @Override
    public boolean decryptFile (File file){
        return changeFile(file, -OFFSET, ENCRYPTION);
    }

    public static boolean changeFile(File inFile, int offset, String prefix) {
        //check input file
        if (!inFile.exists()) {
            System.out.println("File does not exist");
            return false;
        }

        File outFile;
        String fileName = inFile.getName();

        // 如果是已经加密的文件，删除文件后缀
        if(prefix.equals(fileName.substring(fileName.lastIndexOf(".")))) {
            outFile = new File(inFile.getName().substring(0, inFile.getName().lastIndexOf(".")));
            System.out.println(fileName + " was encrypted by ASCLL");
        }
        // 否则就是需要对该文件加密，加上后缀
        else {
            outFile = new File(inFile.getName() + prefix);
            System.out.println(fileName + " isn't been encrypted");
        }

        if (outFile.exists()) {
            outFile.delete();
            System.out.println("file already exists");
            // return false;
        }

        try {
            if (!outFile.createNewFile()) {
                System.out.println("createNewFile failed");
                return false;
            }
        } catch (IOException e) {
            return false;
        }

        //IO
        try (
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(inFile));
                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outFile));
        ) {
            int value;
            for (; ; ) {
                value = input.read();
                if (value == -1) {
                    break;
                }
                // 对每个值加上设定的 ASCLL 值
                output.write(value + offset);
            }
            input.close();
            inFile.delete();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
