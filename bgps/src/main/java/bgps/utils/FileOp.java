package bgps.utils;


import java.io.*;

/**
 * Created by Mingwei Zhang on 2/17/14.
 * File operations.
 *
 * @author Mingwei Zhang
 */
public class FileOp {
    public static boolean folderExists(String location) {
        File f = new File(location);
        if (f.exists() && !f.isDirectory()) {
            return false;
        }
        return f.exists();
    }

    public static boolean createFolder(String location) {
        return (new File(location)).mkdirs();
    }

    public static boolean createFolderIfNotExists(String location) {
        if (folderExists(location)) {
            return true;
        }
        createFolder(location);
        return true;
    }

    public static boolean fileExists(String filepath) {
        File f = new File(filepath);
        return (f.exists() && !f.isDirectory());
    }

    public static File getFile(String folder, String filename, boolean createIfNotExists) {
        String filePath = folder + "/" + filename;
        File file = null;


        try {
            file = new File(filePath);
            // if file doesnt exists, then create it
            if (!file.exists() && createIfNotExists) {
                if (!FileOp.folderExists(folder)) {
                    FileOp.createFolder(folder);
                }
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static BufferedWriter getFileWriter(String folder, String filename) {

        BufferedWriter bw = null;

        try {
            String filepath = folder + "/" + filename;
            FileWriter fw = new FileWriter(filepath);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bw;
    }

    public static BufferedReader getBufferedReader(String folder, String filename) {

        BufferedReader br = null;

        try {
            String filepath = folder + "/" + filename;
            FileReader fr = new FileReader(filepath);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return br;
    }


}

