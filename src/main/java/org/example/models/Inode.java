package org.example.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class Inode {

    private final Map<String, BlockInfo> inodeEntries;

    public Inode() {
        this.inodeEntries = new HashMap<>();
    }

    public synchronized void updateFileMetadata(String fileName, List<UUID> blockIds, UUID blockDeviceId) {
        BlockInfo blockInfo = new BlockInfo(blockIds, blockDeviceId);
        this.inodeEntries.put(fileName, blockInfo);
    }

    public BlockInfo getFileMetadata(String fileName) {
        return this.inodeEntries.get(fileName);
    }
}
