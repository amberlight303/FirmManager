package com.amberlight.firmmanager.util;

import java.io.File;

/**
 * A util class for searching and analyzing files in a specific directory.
 */
public class FilesAnalyzer {

    /**
     * Recursive inspection of a directory and subdirectories for an existing file with specific filename
     * @param targetFileName the filename to search for
     * @param directory the directory to analyze
     * @return true if directory or subdirectories have file, false if not.
     */
    public boolean doDirectoryHaveFile(String targetFileName, File directory)
    {
        File[] list = directory.listFiles();
        if (list!=null)

            for (File file : list)
            {
                if (file.isDirectory())
                {
                    if (doDirectoryHaveFile(targetFileName,file)) return true; else return false;
                }
                else if (targetFileName.equals(file.getName()))
                {
                    return true;
                }
            }
        return false;
    }

    @Override
    public String toString() {
        return "FilesAnalyzer{}";
    }
}
