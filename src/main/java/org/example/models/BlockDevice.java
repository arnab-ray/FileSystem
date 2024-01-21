package org.example.models;

import lombok.Getter;
import org.example.exceptions.UnavailableSpaceException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class BlockDevice {

    private final UUID id;
    private final List<Block> blocks;
    private final List<Block> usedBlocks;
    private final int numberOfBlocks = 64;

    private BlockDevice() {
        this.id = UUID.randomUUID();
        this.blocks = new LinkedList<>();
        for (int i = 0; i < numberOfBlocks; i++) {
            blocks.add(new Block());
        }

        this.usedBlocks = new LinkedList<>();
    }

    private static class SingletonHelper {
        private static final BlockDevice INSTANCE = new BlockDevice();
    }

    public static BlockDevice getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public synchronized List<UUID> writeData(String data) {
        int size = blocks.get(0).getStorageLength();
        int numOfBlocks = 1 + (data.length() / size);

        if (blocks.size() <  numOfBlocks) {
            throw new UnavailableSpaceException("No space available");
        }

        // split up data and consume blocks
        List<UUID> blockIds = new ArrayList<>();
        int offSet = 0;
        for (int i = 0; i < numOfBlocks; i++) {
            Block block = blocks.remove(0);
            int length = Math.min(size, data.length());
            block.writeToBlock(data.substring(offSet, Math.min(offSet + length, data.length())));
            offSet += length;
            usedBlocks.add(block);
            blockIds.add(block.getId());
        }

        return blockIds;
    }

    public String getData(List<UUID> blockIds) {
        StringBuilder sb = new StringBuilder();
        for (Block block : usedBlocks) {
            if (blockIds.contains(block.getId())) {
                sb.append(block.getData());
            }
        }

        return sb.toString();
    }
}
