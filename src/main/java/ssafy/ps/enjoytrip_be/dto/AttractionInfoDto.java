package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttractionInfoDto {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String firstImage1;
    private int areaCode; // sido_code
    private int sigunguCode; // gugun_code
    private double latitude;
    private double longitude;
}