package bgps.utils;

import org.javatuples.Triplet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mingwei Zhang on 3/3/15.
 *
 * Input related functionalities.
 */
public class Input {


    /**
     * Read file and get all columns.
     *
     * @param folder   the folder of the file
     * @param filename the name of the file
     * @return a Map with column title as key and column values as a list
     */
    public static List<Triplet<Integer,Integer,Integer>> readRelFile(String folder, String filename) {

        List<Triplet<Integer,Integer,Integer>> res = new ArrayList<>();

        try {

        String line;

        BufferedReader br = FileOp.getBufferedReader(folder, filename);
        try {
            while ((line = br.readLine()) != null) {
                if(line.startsWith("#")){
                    continue;
                }
                // process the line.
                String[] valueStrs = line.trim().split("\\|");
                int as1 = Integer.parseInt(valueStrs[0]);
                int as2 = Integer.parseInt(valueStrs[1]);
                int rel = Integer.parseInt(valueStrs[2]);
                res.add(new Triplet<>(as1,as2,rel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
