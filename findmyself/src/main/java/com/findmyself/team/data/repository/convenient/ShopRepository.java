package com.findmyself.team.data.repository.convenient;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.SafetyCluster;
import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Shop;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopRepository {

    @PersistenceContext
    private EntityManager em;

    public Shop findOne(Long h_code) {
        return em.find(Shop.class, h_code);
    }

    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }

    public int findMax(){
        return em.createQuery("select max(s.value) from Shop s", Integer.class)
                .getSingleResult();
    }

    public int findMin(){
        return em.createQuery("select min(s.value) from Shop s", Integer.class)
                .getSingleResult();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(s.no) from Shop s", Integer.class)
                .getSingleResult();
    }

    public List<Shop> findByNo(int no){
        return em.createQuery("select s from Shop s where s.no = :no", Shop.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<ConvenientCluster> findClusters(){

        List<ConvenientCluster> convenientClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<Shop> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(Shop tmp : list){
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
