package bgps.components;

import bgps.relations.Relation;
import bgps.topology.Link;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mingwei Zhang on 3/2/15.
 *
 * BGP Autonomous System
 */
public class BGPAS {

    private Set<BGPAS> customers;
    private Set<BGPAS> providers;
    private Set<BGPAS> peers;

    private int asn;

    public BGPAS(int asn){
        this.asn = asn;
        customers = new HashSet<>();
        providers = new HashSet<>();
        peers = new HashSet<>();
    }

    public int getAsn(){
        return asn;
    }

    public void addCustomer(BGPAS as){
        customers.add(as);
    }

    public void addProvider(BGPAS as){
        providers.add(as);
    }

    public void addPeer(BGPAS as){
        peers.add(as);
    }

    /**
     * Override hashCode to make BGPAS hashable for hashMap.
     * No two ASes should have the same AS number.
     *
     * @return the asn as hash value
     */
    @Override
    public int hashCode(){
        return asn;
    }

    /**
     * Override equals to make BGPAS hashable for hashMap.
     * If two ASes have the same AS number, they are the same AS.
     *
     * @return if the two ASes are the same
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof BGPAS)){
            return false;
        }

        if(obj == this){
            return true;
        }

        BGPAS as = (BGPAS)obj;
        return as.getAsn() == asn;
    }
}
