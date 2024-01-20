package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BlockInfo {
    private List<UUID> blockIds;
    private UUID blockDeviceId;

    public synchronized void updateBlockIds(List<UUID> blockIds) {
        this.blockIds.addAll(blockIds);
    }
}
