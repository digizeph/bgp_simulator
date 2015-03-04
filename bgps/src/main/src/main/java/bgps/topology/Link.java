package bgps.topology;

import bgps.components.BGPAS;
import bgps.relations.Relation;
import org.javatuples.Pair;

/**
 * Created by Mingwei Zhang on 3/2/15.
 *
 * Link between BGP ASes.
 */
public class Link {

    protected Pair<BGPAS,BGPAS> asPair;
    private Relation relation;

    public Pair<BGPAS,BGPAS> getAsPair(){
        return asPair;
    }

}
