package com.file;

import java.io.File;

public class Context {

    EncryptAndDecrypt strategy;

    public void setStrategy(EncryptAndDecrypt strategy) {
        this.strategy = strategy;
    }

    public void encryptFile (File file) {
        strategy.encryptFile(file);
    }

    public void decryptFile (File file) {
        strategy.decryptFile(file);
    }

}
