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
public class DasedeRepository {

    private final EntityManager em;

    public DasedeCharter findCharterOne(Long h_code){
        return em.createQuery("select dc from DasedeCharter dc where dc.h_code = :h_code", DasedeCharter.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public DasedeMonthly findMonthlyOne(Long h_code){
        return em.createQuery("select dm from DasedeMonthly dm where dm.h_code = :h_code", DasedeMonthly.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    //군집 갯수
    public int findCharterMaxNo(){
        return em.createQuery("select max(dc.no) from DasedeCharter dc", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMaxNo(){
        return em.createQuery("select max(dm.no) from DasedeMonthly dm", Integer.class)
                .getSingleResult();
    }

    public List<DasedeCharter> findChartersByNo(int no){
        return em.createQuery("select dc from DasedeCharter dc where dc.no = :no", DasedeCharter.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<DasedeMonthly> findMonthlysByNo(int no){
        return em.createQuery("select dm from DasedeMonthly dm where dm.no = :no", DasedeMonthly.class)
                .setParameter("no",no)
                .getResultList();
    }

    public List<HomeCluster> findCharterClusters(){

        List<HomeCluster> charterClusters = new ArrayList<>();

        int cnt = findCharterMaxNo();
        for(int i=0; i<cnt+1; i++){
            List<DasedeCharter> list = findChartersByNo(i);
            List<Long> codes = new ArrayList<>();
            for(DasedeCharter tmp : list){
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
            List<DasedeMonthly> list = findMonthlysByNo(i);
            List<Long> codes = new ArrayList<>();
            for(DasedeMonthly tmp : list){
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
