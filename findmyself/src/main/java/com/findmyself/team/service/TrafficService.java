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

    String subResult = "";
    String subResult2 = "";

    public String searchSubwayInfo(String subwayStations) {

        String result = "";

        //subwayStations 문자열 리스트로 나누고 검색하고 리턴
        String[] stations = subwayStations.split("/");

        for(int i=0; i<stations.length; i++) {
            // 시간 구분 : "," , 역 구분 : "/"
            result += stations[i] + "역 일 평균 0~1시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_0h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 0~1시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_0h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 1~2시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_1h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 1~2시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_1h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 4~5시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_4h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 4~5시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_4h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 5~6시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_5h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 5~6시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_5h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 6~7시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_6h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 6~7시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_6h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 7~8시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_7h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 7~8시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_7h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 8~9시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_8h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 8~9시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_8h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 9~10시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_9h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 9~10시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_9h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 10~11시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_10h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 10~11시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_10h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 11~12시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_11h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 11~12시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_11h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 12~13시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_12h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 12~13시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_12h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 13~14시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_13h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 13~14시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_13h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 14~15시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_14h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 14~15시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_14h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 15~16시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_15h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 15~16시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_15h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 16~17시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_16h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 16~17시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_16h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 17~18시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_17h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 17~18시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_17h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 18~19시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_18h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 18~19시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_18h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 19~20시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_19h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 19~20시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_19h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 20~21시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_20h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 20~21시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_20h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 21~22시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_21h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 21~22시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_21h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 22~23시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_22h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 22~23시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_22h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 23~24시 승차인원 : " + (int)(subwayService.findOne(stations[i]).getIn_23h()/365 + 1) + "명,";
            result += stations[i] + "역 일 평균 23~24시 하차인원 : " + (int)(subwayService.findOne(stations[i]).getOut_23h()/365 + 1) + "명/";

            summarizeResult(stations[i], 1);
        }
        result += "|" + subResult2;
        return result;
    }

    public String searchCarRootInfo(String carRouteInfo) {

        int longitudeIdx;
        int latitudeIdx = 0;
        int dupCheck = 0;
        String result = "";
        //String subResult = "";
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

        for(String point : trafficPoints) {
            volumeService.findOne(point);

            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 0~1시 교통량 : " + (int)(volumeService.findOne(point).get_0h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 1~2시 교통량 : " + (int)(volumeService.findOne(point).get_1h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 2~3시 교통량 : " + (int)(volumeService.findOne(point).get_2h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 3~4시 교통량 : " + (int)(volumeService.findOne(point).get_3h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 4~5시 교통량 : " + (int)(volumeService.findOne(point).get_4h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 5~6시 교통량 : " + (int)(volumeService.findOne(point).get_5h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 6~7시 교통량 : " + (int)(volumeService.findOne(point).get_6h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 7~8시 교통량 : " + (int)(volumeService.findOne(point).get_7h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 8~9시 교통량 : " + (int)(volumeService.findOne(point).get_8h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 9~10시 교통량 : " + (int)(volumeService.findOne(point).get_9h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 10~11시 교통량 : " + (int)(volumeService.findOne(point).get_10h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 11~12시 교통량 : " + (int)(volumeService.findOne(point).get_11h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 12~13시 교통량 : " + (int)(volumeService.findOne(point).get_12h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 13~14시 교통량 : " + (int)(volumeService.findOne(point).get_13h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 14~15시 교통량 : " + (int)(volumeService.findOne(point).get_14h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 15~16시 교통량 : " + (int)(volumeService.findOne(point).get_15h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 16~17시 교통량 : " + (int)(volumeService.findOne(point).get_16h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 17~18시 교통량 : " + (int)(volumeService.findOne(point).get_17h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 18~19시 교통량 : " + (int)(volumeService.findOne(point).get_18h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 19~20시 교통량 : " + (int)(volumeService.findOne(point).get_19h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 20~21시 교통량 : " + (int)(volumeService.findOne(point).get_20h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 21~22시 교통량 : " + (int)(volumeService.findOne(point).get_21h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 22~23시 교통량 : " + (int)(volumeService.findOne(point).get_22h()/30) + ",";
            result += volumeService.findOne(point).getName() + "부근에서의 일 평균 23~24시 교통량 : " + (int)(volumeService.findOne(point).get_23h()/30) + "/";

            summarizeResult(point, 0);

            System.out.println("경유 지점 확인 :" + volumeService.findOne(point).getName());
        }

        result += "|" + subResult;
        return result;
    }

    private String summarizeResult (String point, int select) {
        
        if(select == 0) {

            // 자동차
            if(volumeService.findOne(point).getName().charAt(0) == 'A') {
                if(volumeService.findOne(point).get_6h() < 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_6h() < 60000 && volumeService.findOne(point).get_6h() >= 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_7h() < 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_7h() < 60000 && volumeService.findOne(point).get_7h() >= 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_8h() < 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_8h() < 60000 && volumeService.findOne(point).get_8h() >= 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_17h() < 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_17h() < 60000 && volumeService.findOne(point).get_17h() >= 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_18h() < 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_18h() < 60000 && volumeService.findOne(point).get_18h() >= 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_19h() < 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_19h() < 60000 && volumeService.findOne(point).get_19h() >= 30000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 정체될 것으로 예상됩니다,";
                }
            } else if(volumeService.findOne(point).getName().charAt(0) == 'B') {
                if(volumeService.findOne(point).get_6h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_6h() < 150000 && volumeService.findOne(point).get_6h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_7h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_7h() < 150000 && volumeService.findOne(point).get_7h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_8h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_8h() < 150000 && volumeService.findOne(point).get_8h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_17h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_17h() < 150000 && volumeService.findOne(point).get_17h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_18h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_18h() < 150000 && volumeService.findOne(point).get_18h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_19h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_19h() < 150000 && volumeService.findOne(point).get_19h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 정체될 것으로 예상됩니다,";
                }
            } else if(volumeService.findOne(point).getName().charAt(0) == 'C') {
                if(volumeService.findOne(point).get_6h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_6h() < 150000 && volumeService.findOne(point).get_6h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_7h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_7h() < 150000 && volumeService.findOne(point).get_7h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_8h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_8h() < 150000 && volumeService.findOne(point).get_8h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_17h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_17h() < 150000 && volumeService.findOne(point).get_17h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_18h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_18h() < 150000 && volumeService.findOne(point).get_18h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_19h() < 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_19h() < 150000 && volumeService.findOne(point).get_19h() >= 60000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 정체될 것으로 예상됩니다,";
                }
            } else if(volumeService.findOne(point).getName().charAt(0) == 'D') {
                if(volumeService.findOne(point).get_6h() < 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_6h() < 80000 && volumeService.findOne(point).get_6h() >= 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_7h() < 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_7h() < 80000 && volumeService.findOne(point).get_7h() >= 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_8h() < 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_8h() < 80000 && volumeService.findOne(point).get_8h() >= 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_17h() < 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_17h() < 80000 && volumeService.findOne(point).get_17h() >= 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_18h() < 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_18h() < 80000 && volumeService.findOne(point).get_18h() >= 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_19h() < 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_19h() < 80000 && volumeService.findOne(point).get_19h() >= 50000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 정체될 것으로 예상됩니다,";
                }
            } else {
                if(volumeService.findOne(point).get_6h() < 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_6h() < 200000 && volumeService.findOne(point).get_6h() >= 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_7h() < 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_7h() < 200000 && volumeService.findOne(point).get_7h() >= 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 7~8시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_8h() < 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_8h() < 200000 && volumeService.findOne(point).get_8h() >= 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오전 8~9시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_17h() < 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_17h() < 200000 && volumeService.findOne(point).get_17h() >= 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 5~6시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_18h() < 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_18h() < 200000 && volumeService.findOne(point).get_18h() >= 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 6~7시경에는 정체될 것으로 예상됩니다,";
                }

                if(volumeService.findOne(point).get_19h() < 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 원활할 것으로 예상됩니다,";
                } else if(volumeService.findOne(point).get_19h() < 200000 && volumeService.findOne(point).get_19h() >= 100000) {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 서행할 것으로 예상됩니다,";
                } else {
                    subResult += volumeService.findOne(point).getName() + "부근에서 오후 7~8시경에는 정체될 것으로 예상됩니다,";
                }
            }
        } else {

            // 대중교통
            if((subwayService.findOne(point).getIn_6h() + subwayService.findOne(point).getOut_6h()) < 800000) {
                subResult2 += point + "역에서 오전 6~7시에는 사람이 붐비지 않을 것으로 예상됩니다,";
            } else if((subwayService.findOne(point).getIn_6h() + subwayService.findOne(point).getOut_6h()) < 1600000 && (subwayService.findOne(point).getIn_6h() + subwayService.findOne(point).getOut_6h()) >= 800000 ) {
                subResult2 += point + "역에서 오전 6~7시에는 다소 혼잡할 것으로 예상됩니다,";
            } else {
                subResult2 += point + "역에서 오전 6~7시에는 매우 혼잡할 것으로 예상됩니다,";
            }

            if((subwayService.findOne(point).getIn_7h() + subwayService.findOne(point).getOut_7h()) < 800000) {
                subResult2 += point + "역에서 오전 7~8시에는 사람이 붐비지 않을 것으로 예상됩니다,";
            } else if((subwayService.findOne(point).getIn_7h() + subwayService.findOne(point).getOut_7h()) < 1600000 && (subwayService.findOne(point).getIn_7h() + subwayService.findOne(point).getOut_7h()) >= 800000 ) {
                subResult2 += point + "역에서 오전 7~8시에는 다소 혼잡할 것으로 예상됩니다,";
            } else {
                subResult2 += point + "역에서 오전 7~8시에는 매우 혼잡할 것으로 예상됩니다,";
            }

            if((subwayService.findOne(point).getIn_8h() + subwayService.findOne(point).getOut_8h()) < 800000) {
                subResult2 += point + "역에서 오전 8~9시에는 사람이 붐비지 않을 것으로 예상됩니다,";
            } else if((subwayService.findOne(point).getIn_8h() + subwayService.findOne(point).getOut_8h()) < 1600000 && (subwayService.findOne(point).getIn_8h() + subwayService.findOne(point).getOut_8h()) >= 800000 ) {
                subResult2 += point + "역에서 오전 8~9시에는 다소 혼잡할 것으로 예상됩니다,";
            } else {
                subResult2 += point + "역에서 오전 8~9시에는 매우 혼잡할 것으로 예상됩니다,";
            }

            if((subwayService.findOne(point).getIn_17h() + subwayService.findOne(point).getOut_17h()) < 800000) {
                subResult2 += point + "역에서 오후 5~6시에는 사람이 붐비지 않을 것으로 예상됩니다,";
            } else if((subwayService.findOne(point).getIn_17h() + subwayService.findOne(point).getOut_17h()) < 1600000 && (subwayService.findOne(point).getIn_17h() + subwayService.findOne(point).getOut_17h()) >= 800000 ) {
                subResult2 += point + "역에서 오후 5~6시에는 다소 혼잡할 것으로 예상됩니다,";
            } else {
                subResult2 += point + "역에서 오후 5~6시에는 매우 혼잡할 것으로 예상됩니다,";
            }
            if((subwayService.findOne(point).getIn_18h() + subwayService.findOne(point).getOut_18h()) < 800000) {
                subResult2 += point + "역에서 오후 6~7시에는 사람이 붐비지 않을 것으로 예상됩니다,";
            } else if((subwayService.findOne(point).getIn_18h() + subwayService.findOne(point).getOut_18h()) < 1600000 && (subwayService.findOne(point).getIn_18h() + subwayService.findOne(point).getOut_18h()) >= 800000 ) {
                subResult2 += point + "역에서 오후 6~7시에는 다소 혼잡할 것으로 예상됩니다,";
            } else {
                subResult2 += point + "역에서 오후 6~7시에는 매우 혼잡할 것으로 예상됩니다,";
            }
            if((subwayService.findOne(point).getIn_19h() + subwayService.findOne(point).getOut_19h()) < 800000) {
                subResult2 += point + "역에서 오후 7~8시에는 사람이 붐비지 않을 것으로 예상됩니다,";
            } else if((subwayService.findOne(point).getIn_19h() + subwayService.findOne(point).getOut_19h()) < 1600000 && (subwayService.findOne(point).getIn_19h() + subwayService.findOne(point).getOut_19h()) >= 800000 ) {
                subResult2 += point + "역에서 오후 7~8시에는 다소 혼잡할 것으로 예상됩니다,";
            } else {
                subResult2 += point + "역에서 오후 7~8시에는 매우 혼잡할 것으로 예상됩니다,";
            }
        }

        return null;
    }
}
