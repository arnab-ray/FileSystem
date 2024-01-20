package org.example;

import org.example.models.BlockInfo;
import org.example.models.FileSystem;
import org.example.models.Operation;

public class Main {
    public static void main(String[] args) {
        String data = "Hello World! I exist therefore I am. I am just putting up fillers to exhaust " +
                "a block so that we can demonstrate overflow.";

        FileSystem fileSystem = new FileSystem();

        String fileName = "test.txt";
        BlockInfo blockInfo = fileSystem.fopen(fileName, Operation.WRITE);
        fileSystem.fwrite(fileName, data, blockInfo);
        fileSystem.fclose(fileName);

        blockInfo = fileSystem.fopen(fileName, Operation.READ);
        System.out.println(fileSystem.fread(fileName, blockInfo));
        fileSystem.fclose(fileName);
    }
}