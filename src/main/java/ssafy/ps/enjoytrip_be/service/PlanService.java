package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.PlanDto;

import java.util.List;

public interface PlanService {
    void savePlan(PlanDto planDto) throws Exception;
    List<PlanDto> listPlans(long userNo) throws Exception; // <-- 이 메소드 추가
}