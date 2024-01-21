package org.example.models;

import org.example.exceptions.FileUnavailableException;
import org.example.exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileSystem {
    private final Inode inode;
    private final Map<String, File> files;
    private final Map<UUID, BlockDevice> blockDevices;

    public FileSystem() {
        this.inode = new Inode();
        this.files = new HashMap<>();
        this.blockDevices = new HashMap<>();

        BlockDevice blockDevice = BlockDevice.getInstance();
        blockDevices.putIfAbsent(blockDevice.getId(), blockDevice);
    }

    public BlockInfo fopen(String fileName, Operation operation) {
        File existentFile = files.get(fileName);
        FileState fileState = operation == Operation.READ ? FileState.OPEN_READ : FileState.OPEN_WRITE;
        File file = existentFile == null ? new File(fileName) : existentFile;

        synchronized (file) {
            file.setFileState(fileState);

            if (existentFile == null) {
                Map.Entry<UUID, BlockDevice> entry = blockDevices.entrySet().iterator().next();
                UUID key = entry.getKey();
                BlockDevice blockDevice = blockDevices.get(key);

                files.putIfAbsent(fileName, file);
                inode.updateFileMetadata(fileName, new ArrayList<>(), blockDevice.getId());
            }

            return this.inode.getFileMetadata(fileName);
        }
    }

    public void fclose(String fileName) {
        File existentFile = files.get(fileName);
        if (existentFile == null) {
            throw new FileUnavailableException("File " + fileName + " doesn't exist");
        }

        synchronized (existentFile) {
            existentFile.setFileState(FileState.CLOSED);
        }
    }

    public String fread(String fileName, BlockInfo blockInfo) {
        File existentFile = files.get(fileName);
        if (existentFile.getFileState() != FileState.OPEN_READ) {
            throw new InvalidOperationException("File is not opened in appropriate mode");
        }

        synchronized (existentFile) {
            BlockDevice blockDevice = blockDevices.get(blockInfo.getBlockDeviceId());
            return blockDevice.getData(blockInfo.getBlockIds());
        }
    }

    public void fwrite(String fileName, String data, BlockInfo blockInfo) {
        File existentFile = files.get(fileName);
        if (existentFile.getFileState() != FileState.OPEN_WRITE) {
            throw new InvalidOperationException("File is not opened in appropriate mode");
        }

        synchronized (existentFile) {
            List<UUID> blockIds = new ArrayList<>();
            BlockDevice blockDevice;
            if (blockInfo == null) {
                Map.Entry<UUID, BlockDevice> entry = blockDevices.entrySet().iterator().next();
                UUID key = entry.getKey();
                blockDevice = blockDevices.get(key);
                List<UUID> newBlockIds = blockDevice.writeData(data);
                blockIds.addAll(newBlockIds);
            } else {
                blockDevice = blockDevices.get(blockInfo.getBlockDeviceId());
                List<UUID> newBlockIds = blockDevice.writeData(data);
                blockIds.addAll(newBlockIds);
                blockInfo.updateBlockIds(blockIds);
            }

            inode.updateFileMetadata(fileName, blockIds, blockDevice.getId());
        }
    }
}
