package ssafy.ps.enjoytrip_be.service.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.PlanDao;
import ssafy.ps.enjoytrip_be.dao.impl.PlanDaoImpl;
import ssafy.ps.enjoytrip_be.dto.PlanDto;
import ssafy.ps.enjoytrip_be.service.PlanService;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.Connection;
import java.util.List;

public class PlanServiceImpl implements PlanService {
    @Getter
    private static PlanService instance = new PlanServiceImpl();
    private final PlanDao planDao = PlanDaoImpl.getInstance();
    private final DBUtil dbUtil = DBUtil.getInstance();

    public static void setInstance(PlanService instance) {
        PlanServiceImpl.instance = instance;
    }

    @Override
    public void savePlan(PlanDto planDto) throws Exception {
        Connection conn = null;
        try {
            // 1. 커넥션을 얻어온다.
            conn = dbUtil.getConnection();

            // 2. 트랜잭션 시작! (자동 커밋 기능 비활성화)
            conn.setAutoCommit(false);

            // 3. DAO에게 커넥션을 넘겨주며 작전(DB 작업)을 지시한다.
            planDao.savePlan(conn, planDto);

            // 4. 모든 작전이 예외 없이 성공했으면, 최종 승인(커밋)
            conn.commit();

        } catch (Exception e) {
            // 5. 작전 중 문제 발생 시, 모든 것을 되돌린다(롤백)
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            throw e; // 예외를 다시 던져서 컨트롤러에게 실패를 알린다.
        } finally {
            // 6. 커넥션의 상태를 원래대로 되돌리고 닫아준다.
            if (conn != null) {
                conn.setAutoCommit(true);
                dbUtil.close(conn);
            }
        }
    }

    @Override
    public List<PlanDto> listPlans(long userNo) throws Exception {
        return planDao.listPlans(userNo);
    }
}