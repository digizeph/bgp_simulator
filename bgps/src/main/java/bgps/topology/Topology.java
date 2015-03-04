package bgps.topology;

import bgps.components.BGPAS;
import bgps.utils.Input;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Mingwei Zhang on 3/2/15.
 *
 * Topology related class.
 */
public class Topology {

    HashSet<BGPAS> asSet;
    HashMap<Integer,BGPAS> asMap;

    public Topology(){
        asSet = new HashSet<>();
        asMap = new HashMap<>();
    }

    public void loadTopology(String folder, String filename){
        List<Triplet<Integer, Integer, Integer>> relList = Input.readRelFile(folder, filename);

        for(Triplet<Integer,Integer,Integer> triplet: relList){
            int asn1=triplet.getValue0(), asn2=triplet.getValue1(), rel = triplet.getValue2();
            if(!asMap.containsKey(asn1)){
                asMap.put(asn1, new BGPAS(asn1));
            }
            if(!asMap.containsKey(asn2)){
                asMap.put(asn2, new BGPAS(asn2));
            }
            BGPAS as1=asMap.get(asn1),as2=asMap.get(asn2);

            if(rel==0){
                as1.addPeer(as2);
                as2.addPeer(as1);
            } else {
                as1.addCustomer(as2);
                as2.addProvider(as1);
            }
        }
    }

    public static void main(String[] args) {
        Topology topo = new Topology();
        topo.loadTopology("/Users/mingwei/netsec/drawbridge/data/topology","20150101.as-rel.txt");
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        long heapSize = Runtime.getRuntime().totalMemory();
        System.out.printf("%d MB.\n",heapSize/1024/1024);
    }
}
