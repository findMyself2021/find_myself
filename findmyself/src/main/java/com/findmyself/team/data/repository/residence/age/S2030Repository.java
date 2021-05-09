package com.findmyself.team.data.repository.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import com.findmyself.team.data.domain.residence.age.S2030;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class S2030Repository {
    @PersistenceContext
    private EntityManager em;

    public S2030 findOne(Long h_code){
        return em.find(S2030.class,h_code);
    }

    public Double findMax(){
        return em.createQuery("select max(s.value) from S2030 s", Double.class)
                .getSingleResult();
    }

    public List<S2030> findAll(){
        return em.createQuery("select a from S2030 a",S2030.class)
                .getResultList();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(s.no) from S2030 s", Integer.class)
                .getSingleResult();
    }

    public List<S2030> findByNo(int no){
        return em.createQuery("select s from S2030 s where s.no = :no", S2030.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<AgeCluster> findClusters(){

        List<AgeCluster> ageClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<S2030> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(S2030 tmp : list){
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