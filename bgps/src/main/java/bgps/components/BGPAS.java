package bgps.components;


import java.util.ArrayList;
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

    private List<BGPAS> path;
    private List<BGPAS> receivedPath;

    private int asn;

    public BGPAS(int asn){
        this.asn = asn;
        customers = new HashSet<>();
        providers = new HashSet<>();
        peers = new HashSet<>();
        path = new ArrayList<>();
        receivedPath = new ArrayList<>();
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

    public List<BGPAS> getToSendTargets(){

        List<BGPAS> toSend = new ArrayList<>();

        // received a path that it send out before.
        if(receivedPath.contains(this)){
            return toSend;
        }

        // origin of the propagation.
        if(receivedPath.size()==0){
            // first hop, send to all
            path.add(this);
            toSend.addAll(customers);
            toSend.addAll(providers);
            toSend.addAll(peers);
            return toSend;
        }

        BGPAS receivedFrom = receivedPath.get(receivedPath.size()-1);

        // if the current path is shorter.
        if(path.size()!=0 && path.size() < receivedPath.size()){
            return toSend;
        }

        path.clear();
        path.addAll(receivedPath);


        // add all potential send-to targets.
        toSend.addAll(customers);
        toSend.addAll(peers);
        if(!providers.contains(receivedFrom)){
            // do not propagate to providers unless it is not from a provider
            toSend.addAll(providers);
        }

        if(toSend.contains(receivedFrom))
            toSend.remove(receivedFrom);

        // remove targets that will cause circle.
        List<BGPAS> toRemove = new ArrayList<>();
        for(BGPAS as: toSend){
            if(receivedPath.contains(as))
                toRemove.add(as);
        }

        toSend.removeAll(toRemove);

        return toSend;
    }

    public void receivePath(List<BGPAS> list){
        receivedPath.clear();
        for(BGPAS as: list){
            receivedPath.add(as);
        }
    }

    public void propagatePath(List<BGPAS> path){

        System.out.println(asn);
        receivePath(path);
        List<BGPAS> targets = getToSendTargets();
        List<BGPAS> newpath = new ArrayList<>();
        newpath.addAll(path);
        newpath.add(this);

        for(BGPAS as: targets){
            as.propagatePath(newpath);
        }
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
