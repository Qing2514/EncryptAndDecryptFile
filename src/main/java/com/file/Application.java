package com.file;


import java.io.File;

public class Application {

    public static void main(String[] args) throws Exception {

        Context context = new Context();

        // 策略一：每个字节数据 ASCLL 码加 5
        context.setStrategy(new StrategyOne());
        context.encryptFile(new File( "file1.txt"));
        // context.decryptFile(new File( "file1.txt.encryptedByASCLL"));

        // 策略二：DES对称加密
        context.setStrategy(new StrategyTwo());
        context.encryptFile(new File( "file2.txt"));
        // context.decryptFile(new File( "file2.txt.encryptedByDES"));
    }

}
