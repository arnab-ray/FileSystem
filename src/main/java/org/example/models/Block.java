package org.example.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Block {

    private final UUID id;
    private final char[] storage;
    private final int storageLength = 64;

    public Block() {
        this.id = UUID.randomUUID();
        this.storage = new char[storageLength];
    }

    public void writeToBlock(String data) {
        System.out.println("Writing substring : " + data);
        for (int i = 0; i < Math.min(data.length(), storageLength); i++) {
            storage[i] = data.charAt(i);
        }

        for (int i = Math.min(data.length(), storageLength); i < storageLength; i++) {
            storage[i] = ' ';
        }
    }

    public String getData() {
        return new String(storage);
    }
}
