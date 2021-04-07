package com.findmyself.team.service;

import com.findmyself.team.TrafficData;
import com.findmyself.team.data.service.traffic.AddressService;
import com.findmyself.team.data.service.traffic.SubwayService;
import com.findmyself.team.data.service.traffic.VolumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrafficService {

    @Autowired
    SubwayService subwayService;

    @Autowired
    AddressService addressService;

    @Autowired
    VolumeService volumeService;

    TrafficData td = new TrafficData();

    String result = "";

    public String searchSubwayInfo(String subwayStations) {

        //subwayStations 문자열 리스트로 나누고 검색하고 리턴
        String[] stations = subwayStations.split("/");

        for(int i=0; i<stations.length; i++) {
            td.setIn_0h(subwayService.findOne(stations[i]).getIn_0h());
            td.setIn_1h(subwayService.findOne(stations[i]).getIn_1h());
            td.setIn_4h(subwayService.findOne(stations[i]).getIn_4h());
            td.setIn_5h(subwayService.findOne(stations[i]).getIn_5h());
            td.setIn_6h(subwayService.findOne(stations[i]).getIn_7h());
            td.setIn_7h(subwayService.findOne(stations[i]).getIn_7h());
            td.setIn_8h(subwayService.findOne(stations[i]).getIn_8h());
            td.setIn_9h(subwayService.findOne(stations[i]).getIn_9h());
            td.setIn_10h(subwayService.findOne(stations[i]).getIn_10h());
            td.setIn_11h(subwayService.findOne(stations[i]).getIn_11h());
            td.setIn_12h(subwayService.findOne(stations[i]).getIn_12h());
            td.setIn_13h(subwayService.findOne(stations[i]).getIn_13h());
            td.setIn_14h(subwayService.findOne(stations[i]).getIn_14h());
            td.setIn_15h(subwayService.findOne(stations[i]).getIn_15h());
            td.setIn_16h(subwayService.findOne(stations[i]).getIn_16h());
            td.setIn_17h(subwayService.findOne(stations[i]).getIn_17h());
            td.setIn_18h(subwayService.findOne(stations[i]).getIn_18h());
            td.setIn_19h(subwayService.findOne(stations[i]).getIn_19h());
            td.setIn_20h(subwayService.findOne(stations[i]).getIn_20h());
            td.setIn_21h(subwayService.findOne(stations[i]).getIn_21h());
            td.setIn_22h(subwayService.findOne(stations[i]).getIn_22h());
            td.setIn_23h(subwayService.findOne(stations[i]).getIn_23h());

            System.out.println(stations[i] + "역 1년간 23~24시 승차인원 : " +subwayService.findOne(stations[i]).getIn_23h());
        }

        result = stations[0] + "역 1년간 23~24시 승차인원 : " + subwayService.findOne(stations[0]).getIn_23h();

        return result;
    }

    public String searchCarRootInfo(String carRouteInfo) {

        int longitudeIdx;
        int latitudeIdx = 0;
        int dupCheck = 0;
        String result = "";
        List<String> trafficPoints = new ArrayList<String>();

        //subwayStations 문자열 리스트로 나누고 검색하고 리턴
        String[] carRoute = carRouteInfo.split(",");
        List<Float> carRouteLocation = new ArrayList<Float>();

        for(int i = 0; i < carRoute.length; i++) {
            carRoute[i] = carRoute[i].substring(0, 10);
            carRouteLocation.add((float)(Math.round(Float.parseFloat(carRoute[i])*10000)/10000.0));
        }

        //교통량 지점 DB에서 지점을 지나는 경로가 있는지 확인
        for(Float idx : carRouteLocation) {
            for(double i = -0.001; i <= 0.001; i = (i+0.0001)) {
                if(idx < 100 && null != addressService.findOne((float) (idx + i))) {
                    longitudeIdx = 0;
                    for (Float idx2 : carRouteLocation) {
                        if (Math.abs(addressService.findOne((float) (idx + i)).getLongitude() - idx2) <= 0.003 && latitudeIdx == longitudeIdx + 1) {
                            //중복 지점 확인
                            for(String idx3 : trafficPoints) {
                                if(idx3 == addressService.findOne((float) (idx + i)).getNum())
                                    dupCheck = 1;
                            }

                            if(dupCheck == 0)
                                trafficPoints.add(addressService.findOne((float) (idx + i)).getNum());

                            dupCheck = 0;
                        }
                        longitudeIdx++;
                    }
                }
            }
            latitudeIdx++;
        }

        int check = 0;

        for(String point : trafficPoints) {
            System.out.println("경유 지점 확인 :" + point);
            if(check == 0) {
                result = result + "경유지 지점 확인 : " + point;
                check++;
            } else {
                result = result + ", 경유지 지점 확인 : " + point;
            }
        }

        return result;
    }
}
