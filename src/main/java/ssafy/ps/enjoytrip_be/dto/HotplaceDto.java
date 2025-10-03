package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotplaceDto {
    private int hotplaceId;
    private long userNo;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private String createdDate;
    private String userName; // JOIN해서 가져올 작성자 이름
}