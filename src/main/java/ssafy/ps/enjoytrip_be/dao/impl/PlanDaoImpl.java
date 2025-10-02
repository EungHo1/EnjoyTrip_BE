package ssafy.ps.enjoytrip_be.dao.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.PlanDao;
import ssafy.ps.enjoytrip_be.dto.AttractionInfoDto;
import ssafy.ps.enjoytrip_be.dto.PlanDto;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PlanDaoImpl implements PlanDao {
    @Getter
    private static PlanDao instance = new PlanDaoImpl();
    private final DBUtil dbUtil = DBUtil.getInstance();

    private PlanDaoImpl() {
    }

    public static PlanDao getInstance(PlanDao instance) {
        return instance;
    }

    @Override
    public void savePlan(Connection conn, PlanDto planDto) throws SQLException {
        // 1. plan 테이블에 먼저 INSERT
        String planSql = "INSERT INTO plan (user_no, title) VALUES (?, ?)";
        // Statement.RETURN_GENERATED_KEYS 옵션으로 방금 INSERT된 plan_id를 받아올 수 있다!
        try (PreparedStatement pstmtPlan = conn.prepareStatement(planSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmtPlan.setLong(1, planDto.getUserNo());
            pstmtPlan.setString(2, planDto.getTitle());
            pstmtPlan.executeUpdate();

            // 방금 생성된 plan_id 가져오기
            try (ResultSet rs = pstmtPlan.getGeneratedKeys()) {
                if (rs.next()) {
                    int planId = rs.getInt(1);
                    planDto.setPlanId(planId); // DTO에도 ID 설정
                }
            }
        }

        // 2. plan_attraction 테이블에 Batch Insert
        String detailSql = "INSERT INTO plan_attraction (plan_id, content_id, `order`) VALUES (?, ?, ?)";
        try (PreparedStatement pstmtDetail = conn.prepareStatement(detailSql)) {
            List<AttractionInfoDto> attractions = planDto.getAttractions();
            for (int i = 0; i < attractions.size(); i++) {
                AttractionInfoDto spot = attractions.get(i);
                pstmtDetail.setInt(1, planDto.getPlanId());
                pstmtDetail.setInt(2, spot.getContentId());
                pstmtDetail.setInt(3, i + 1); // 순서 저장
                pstmtDetail.addBatch();
            }
            pstmtDetail.executeBatch();
        }
    }

    @Override
    public List<PlanDto> listPlans(long userNo) throws SQLException {
        // Key: plan_id, Value: PlanDto
        // plan_id를 기준으로 관광지들을 그룹핑하기 위해 Map을 사용한다.
        Map<Integer, PlanDto> planMap = new LinkedHashMap<>();

        String sql = "SELECT p.plan_id, p.title AS plan_title, p.create_date, " +
                "       a.content_id, a.title AS attraction_title, pa.`order` " +
                "FROM plan p " +
                "JOIN plan_attraction pa ON p.plan_id = pa.plan_id " +
                "JOIN attractions a ON pa.content_id = a.content_id " +
                "WHERE p.user_no = ? " +
                "ORDER BY p.plan_id, pa.`order`"; // plan_id와 순서로 정렬

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int planId = rs.getInt("plan_id");

                    // 이 planId를 처음 만났다면, 새로운 PlanDto를 생성해서 Map에 넣는다.
                    PlanDto planDto = planMap.computeIfAbsent(planId, id -> {
                        PlanDto newPlan = new PlanDto();
                        newPlan.setPlanId(id);
                        try {
                            newPlan.setTitle(rs.getString("plan_title"));
                            newPlan.setCreateDate(rs.getString("create_date"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        newPlan.setAttractions(new ArrayList<>());
                        return newPlan;
                    });

                    // 현재 행의 관광지 정보를 만들어서 해당 PlanDto의 attractions 리스트에 추가한다.
                    AttractionInfoDto attraction = new AttractionInfoDto();
                    attraction.setContentId(rs.getInt("content_id"));
                    attraction.setTitle(rs.getString("attraction_title"));
                    planDto.getAttractions().add(attraction);
                }
            }
        }
        // Map에 저장된 모든 PlanDto 객체들을 List로 변환해서 반환
        return new ArrayList<>(planMap.values());
    }
}