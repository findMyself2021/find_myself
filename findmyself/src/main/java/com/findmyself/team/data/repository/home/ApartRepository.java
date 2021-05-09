package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApartRepository {

    private final EntityManager em;

    public ApartCharter findCharterOne(Long h_code){
        return em.createQuery("select ac from ApartCharter ac where ac.h_code = :h_code", ApartCharter.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public ApartMonthly findMonthlyOne(Long h_code){
        return em.createQuery("select am from ApartMonthly am where am.h_code = :h_code", ApartMonthly.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    //군집 갯수
    public int findCharterMaxNo(){
        return em.createQuery("select max(ac.no) from ApartCharter ac", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMaxNo(){
        return em.createQuery("select max(am.no) from ApartMonthly am", Integer.class)
                .getSingleResult();
    }

    public List<ApartCharter> findChartersByNo(int no){
        return em.createQuery("select ac from ApartCharter ac where ac.no = :no", ApartCharter.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<ApartMonthly> findMonthlysByNo(int no){
        return em.createQuery("select am from ApartMonthly am where am.no = :no", ApartMonthly.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<HomeCluster> findCharterClusters(){

        List<HomeCluster> charterClusters = new ArrayList<>();

        int cnt = findCharterMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<ApartCharter> list = findChartersByNo(i);
            List<Long> codes = new ArrayList<>();
            for(ApartCharter tmp : list){
                codes.add(tmp.getH_code());
            }

            charterClusters.add(new HomeCluster(i
                    , list.get(0).getDeposit_min()
                    , list.get(0).getDeposit_max()
                    , list.get(0).getDeposit_avg()
                    , codes));
        }

        return charterClusters;
    }

    public List<HomeCluster> findMonthlyClusters(){

        List<HomeCluster> monthlyClusters = new ArrayList<>();

        int cnt = findMonthlyMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<ApartMonthly> list = findMonthlysByNo(i);
            List<Long> codes = new ArrayList<>();
            for(ApartMonthly tmp : list){
                codes.add(tmp.getH_code());
            }

            monthlyClusters.add(new HomeCluster(i
                    , list.get(0).getDeposit_min()
                    , list.get(0).getDeposit_max()
                    , list.get(0).getDeposit_avg()
                    , list.get(0).getMonthly_min()
                    , list.get(0).getMonthly_max()
                    , list.get(0).getMonthly_avg()
                    , codes));
        }

        return monthlyClusters;
    }

    public HomeCluster findCharterClusterByNo(int no){
        return findCharterClusters().get(no);
    }

    public HomeCluster findMonthlyClusterByNo(int no){
        return findMonthlyClusters().get(no);
    }
}
