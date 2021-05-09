package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.SafetyCluster;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SafetyRepository {

    @PersistenceContext
    private EntityManager em;

    public Safety findOne(Long h_code) {
        return em.find(Safety.class, h_code);
    }

    public List<Safety> findAll() {
        return em.createQuery("select s from Safety s", Safety.class)
                .getResultList();
    }

    public int findMax(){
        return em.createQuery("select max(s.value) from Safety s", Integer.class)
                .getSingleResult();
    }

    public int findMin(){
        return em.createQuery("select min(s.value) from Safety s", Integer.class)
                .getSingleResult();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(s.no) from Safety s", Integer.class)
                .getSingleResult();
    }

    public List<Safety> findByNo(int no){
        return em.createQuery("select s from Safety s where s.no = :no", Safety.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<SafetyCluster> findClusters(){

        List<SafetyCluster> safetyClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<Safety> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(Safety tmp : list){
                codes.add(tmp.getH_code());
            }

            safetyClusters.add(new SafetyCluster(i
                    , list.get(0).getMin()
                    , list.get(0).getMax()
                    , list.get(0).getAvg()
                    , codes));
        }

        return safetyClusters;
    }

    public SafetyCluster findClusterByNo(int no){
        return findClusters().get(no);
    }

}
