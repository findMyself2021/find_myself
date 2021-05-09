package com.findmyself.team.data.repository.residence;

import com.findmyself.team.data.domain.home.ApartCharter;
import com.findmyself.team.data.domain.home.HomeCluster;
import com.findmyself.team.data.domain.residence.Gender;
import com.findmyself.team.data.domain.residence.GenderCluster;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenderRepository {
    @PersistenceContext
    private EntityManager em;

    public Gender findOne(Long h_code){
        return em.find(Gender.class,h_code);
    }

    public List<Gender> findAll(){
        return em.createQuery("select rg from Gender rg",Gender.class)
                .getResultList();
    }

    public double findMax(){
        return em.createQuery("select max(rg.ratio) from Gender rg", Double.class)
                .getSingleResult();
    }

    public double findMin(){
        return em.createQuery("select min(rg.ratio) from Gender rg", Double.class)
                .getSingleResult();
    }

    //군집 갯수
    public int findMaxNo(){
        return em.createQuery("select max(g.no) from Gender g", Integer.class)
                .getSingleResult();
    }

    public List<Gender> findByNo(int no){
        return em.createQuery("select g from Gender g where g.no = :no", Gender.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<GenderCluster> findClusters(){

        List<GenderCluster> genderClusters = new ArrayList<>();

        int cnt = findMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<Gender> list = findByNo(i);
            List<Long> codes = new ArrayList<>();
            for(Gender tmp : list){
                codes.add(tmp.getH_code());
            }

            genderClusters.add(new GenderCluster(i
                    , list.get(0).getRatio_min()
                    , list.get(0).getRatio_max()
                    , list.get(0).getRatio_avg()
                    , codes));
        }

        return genderClusters;
    }

    public GenderCluster findClusterByNo(int no){
        return findClusters().get(no);
    }
}
