package com.findmyself.team.data.repository.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import com.findmyself.team.data.domain.residence.age.S4050;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class S4050Repository {
    @PersistenceContext
    private EntityManager em;

    public S4050 findOne(Long h_code){
        return em.find(S4050.class,h_code);
    }

    public Double findMax(){
        return em.createQuery("select max(s.value) from S4050 s", Double.class)
                .getSingleResult();
    }

    public List<S4050> findAll(){
        return em.createQuery("select a from S4050 a",S4050.class)
                .getResultList();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(s.no) from S4050 s", Integer.class)
                .getSingleResult();
    }

    public List<S4050> findByNo(int no){
        return em.createQuery("select s from S4050 s where s.no = :no", S4050.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<AgeCluster> findClusters(){

        List<AgeCluster> ageClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<S4050> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(S4050 tmp : list){
                codes.add(tmp.getH_code());
            }

            ageClusters.add(new AgeCluster(i
                    , list.get(0).getMin()
                    , list.get(0).getMax()
                    , list.get(0).getAvg()
                    , codes));
        }

        return ageClusters;
    }

    public AgeCluster findClusterByNo(int no){
        return findClusters().get(no);
    }

}