package com.findmyself.team.data.repository.residence.age;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.SafetyCluster;
import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChildRepository {
    @PersistenceContext
    private EntityManager em;

    public Child findOne(Long h_code){
        return em.find(Child.class,h_code);
    }

    public Double findMax(){
        return em.createQuery("select max(s.value) from Child s", Double.class)
                .getSingleResult();
    }

    public List<Child> findAll(){
        return em.createQuery("select a from Child a",Child.class)
                .getResultList();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(s.no) from Child s", Integer.class)
                .getSingleResult();
    }

    public List<Child> findByNo(int no){
        return em.createQuery("select s from Child s where s.no = :no", Child.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<AgeCluster> findClusters(){

        List<AgeCluster> ageClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<Child> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(Child tmp : list){
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