package ua.lg.karazhanov.utils;

import java.io.File;

/**
 * @author karazhanov on 27.10.17.
 */
public class FileUtils {
    public static boolean deleteRecursive(File path) {
        if (!path.exists()) return false;
        boolean ret = true;
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File f : files) {
                    ret = ret && FileUtils.deleteRecursive(f);
                }
            }
        }
        return ret && path.delete();
    }
}
