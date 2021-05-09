package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DandokRepository {

    private final EntityManager em;

    public DandokCharter findCharterOne(Long h_code){
        return em.createQuery("select dc from DandokCharter dc where dc.h_code = :h_code", DandokCharter.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public DandokMonthly findMonthlyOne(Long h_code){
        return em.createQuery("select dm from DandokMonthly dm where dm.h_code = :h_code", DandokMonthly.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    //군집 갯수
    public int findCharterMaxNo(){
        return em.createQuery("select max(dc.no) from DandokCharter dc", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMaxNo(){
        return em.createQuery("select max(dm.no) from DandokMonthly dm", Integer.class)
                .getSingleResult();
    }

    public List<DandokCharter> findChartersByNo(int no){
        return em.createQuery("select dc from DandokCharter dc where dc.no = :no", DandokCharter.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<DandokMonthly> findMonthlysByNo(int no){
        return em.createQuery("select dm from DandokMonthly dm where dm.no = :no", DandokMonthly.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<HomeCluster> findCharterClusters(){

        List<HomeCluster> charterClusters = new ArrayList<>();

        int cnt = findCharterMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<DandokCharter> list = findChartersByNo(i);
            List<Long> codes = new ArrayList<>();
            for(DandokCharter tmp : list){
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
            List<DandokMonthly> list = findMonthlysByNo(i);
            List<Long> codes = new ArrayList<>();
            for(DandokMonthly tmp : list){
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
