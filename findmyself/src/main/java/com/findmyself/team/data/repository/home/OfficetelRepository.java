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

public class OfficetelRepository {

    private final EntityManager em;

    public OfficetelCharter findCharterOne(Long h_code){
        return em.createQuery("select oc from OfficetelCharter oc where oc.h_code = :h_code", OfficetelCharter.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public OfficetelMonthly findMonthlyOne(Long h_code){
        return em.createQuery("select om from OfficetelMonthly om where om.h_code = :h_code", OfficetelMonthly.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    //군집 갯수
    public int findCharterMaxNo(){
        return em.createQuery("select max(oc.no) from OfficetelCharter oc", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMaxNo(){
        return em.createQuery("select max(om.no) from OfficetelMonthly om", Integer.class)
                .getSingleResult();
    }

    public List<OfficetelCharter> findChartersByNo(int no){
        return em.createQuery("select oc from OfficetelCharter oc where oc.no = :no", OfficetelCharter.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<OfficetelMonthly> findMonthlysByNo(int no){
        return em.createQuery("select om from OfficetelMonthly om where om.no = :no", OfficetelMonthly.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<HomeCluster> findCharterClusters(){

        List<HomeCluster> charterClusters = new ArrayList<>();

        int cnt = findCharterMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<OfficetelCharter> list = findChartersByNo(i);
            List<Long> codes = new ArrayList<>();
            for(OfficetelCharter tmp : list){
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
            List<OfficetelMonthly> list = findMonthlysByNo(i);
            List<Long> codes = new ArrayList<>();
            for(OfficetelMonthly tmp : list){
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
