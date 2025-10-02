package ssafy.ps.enjoytrip_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlanDto {
    private int planId;
    private long userNo;
    private String title;
    private String createDate;
    private List<AttractionInfoDto> attractions; // 계획에 포함된 관광지 목록
}