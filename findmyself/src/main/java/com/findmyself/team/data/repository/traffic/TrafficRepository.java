package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.Traffic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrafficRepository {

    @PersistenceContext
    private EntityManager em;

    public Traffic findOne(Long h_code){
        return em.find(Traffic.class, h_code);
    }

    public List<Traffic> findAll() {
        return em.createQuery("select t from Traffic t", Traffic.class)
                .getResultList();
    }

    public int findMax(){
        return em.createQuery("select max(t.value) from Traffic t", Integer.class)
                .getSingleResult();
    }

    public int findMin(){
        return em.createQuery("select min(t.value) from Traffic t", Integer.class)
                .getSingleResult();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(t.no) from Traffic t", Integer.class)
                .getSingleResult();
    }

    public List<Traffic> findByNo(int no){
        return em.createQuery("select t from Traffic t where t.no = :no", Traffic.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<TrafficCluster> findClusters(){

        List<TrafficCluster> trafficClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<Traffic> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(Traffic tmp : list){
                codes.add(tmp.getH_code());
            }

            trafficClusters.add(new TrafficCluster(i
                    , list.get(0).getMin()
                    , list.get(0).getMax()
                    , list.get(0).getAvg()
                    , codes));
        }

        return trafficClusters;
    }

    public TrafficCluster findClusterByNo(int no){
        return findClusters().get(no);
    }

}