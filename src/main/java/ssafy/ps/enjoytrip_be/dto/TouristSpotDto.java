package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristSpotDto {
    private int spotId;
    private String title;
    private String addr;
    private double latitude;
    private double longitude;
    private int contentTypeId; // 관광지(12), 문화시설(14), 숙박(32), 음식점(39) 등
}