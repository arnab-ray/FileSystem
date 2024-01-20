package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {
    private FileState fileState;
    private String fileName;

    public File(String fileName) {
        this.fileName = fileName;
        this.fileState = FileState.CLOSED;
    }
}
