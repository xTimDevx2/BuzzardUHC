package me.xtimdevx.buzzarduhc.utils;

import java.io.File;

/**
 * Created by xTimDevx on 14/06/2017.
 */
public class FileUtils {

    public static boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
