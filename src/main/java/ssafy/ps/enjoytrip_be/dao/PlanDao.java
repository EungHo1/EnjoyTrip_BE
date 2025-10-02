package ssafy.ps.enjoytrip_be.dao;


import ssafy.ps.enjoytrip_be.dto.PlanDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PlanDao {
    // Service로부터 Connection 객체를 직접 받아서 사용하도록 설계
    void savePlan(Connection conn, PlanDto planDto) throws SQLException;
    List<PlanDto> listPlans(long userNo) throws SQLException;
}