package com.findmyself.team.data.repository.convenient;

import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Shop;
import com.findmyself.team.data.domain.convenient.Sport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SportRepository {

    @PersistenceContext
    private EntityManager em;

    public Sport findOne(Long h_code) {
        return em.find(Sport.class, h_code);
    }

    public List<Sport> findAll() {
        return em.createQuery("select s from Sport s", Sport.class)
                .getResultList();
    }

    public int findMax(){
        return em.createQuery("select max(s.value) from Sport s", Integer.class)
                .getSingleResult();
    }

    public int findMin(){
        return em.createQuery("select min(s.value) from Sport s", Integer.class)
                .getSingleResult();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(s.no) from Sport s", Integer.class)
                .getSingleResult();
    }

    public List<Sport> findByNo(int no){
        return em.createQuery("select s from Sport s where s.no = :no", Sport.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<ConvenientCluster> findClusters(){

        List<ConvenientCluster> convenientClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<Sport> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(Sport tmp : list){
                codes.add(tmp.getH_code());
            }

            convenientClusters.add(new ConvenientCluster(i
                    , list.get(0).getMin()
                    , list.get(0).getMax()
                    , list.get(0).getAvg()
                    , codes));
        }

        return convenientClusters;
    }

    public ConvenientCluster findClusterByNo(int no){
        return findClusters().get(no);
    }
}
