package com.example.waterpipelinesystemproject.util;

import lombok.Getter;

import java.io.File;

public class LoadUtil {
    public static String getPath(Folder folder) {
        return new File("").getAbsoluteFile() + "/" + folder.getFolder();
    }

    @Getter
    public enum Folder {

        CSV("csv");

        Folder(String folder) {
            this.folder = folder;
        }

        private final String folder;
    }
}
