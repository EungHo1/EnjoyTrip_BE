package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttractionDto {
    private int no;
    private int contentId;
    private String title;
    private int contentTypeId;
    private int areaCode; // sido_code
    private int siGunGuCode; // gugun_code
    private String firstImage1;
    private double latitude;
    private double longitude;
    private String tel;
    private String addr1;
    private String addr2;
}