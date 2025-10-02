package ssafy.ps.enjoytrip_be.dao.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.AttractionDao;
import ssafy.ps.enjoytrip_be.dto.AttractionInfoDto;
import ssafy.ps.enjoytrip_be.dto.ContentTypeDto;
import ssafy.ps.enjoytrip_be.dto.GugunDto;
import ssafy.ps.enjoytrip_be.dto.SidoDto;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttractionDaoImpl implements AttractionDao {
    DBUtil dbUtil = DBUtil.getInstance();
    @Getter
    private final static AttractionDaoImpl instance = new AttractionDaoImpl();
    private AttractionDaoImpl() {
    }

    @Override
    public List<AttractionInfoDto> listAttractions(Map<String, String> params) throws SQLException {
        List<AttractionInfoDto> list = new ArrayList<>();

        // 동적 쿼리를 만들기 위해 StringBuilder 사용
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT content_id, title, addr1, first_image1, latitude, longitude \n");
        sql.append("FROM attractions \n");
        sql.append("WHERE 1=1 \n"); // 어떤 조건이 들어올지 모르니 1=1로 시작하는 트릭

        String sidoCode = params.get("sidoCode");
        String gugunCode = params.get("gugunCode");
        String contentTypeId = params.get("contentTypeId");

        if (sidoCode != null && !sidoCode.isBlank() && !sidoCode.equals("0")) {
            sql.append("AND area_code = ? \n");
        }
        if (gugunCode != null && !gugunCode.isBlank() && !gugunCode.equals("0")) {
            sql.append("AND si_gun_gu_code = ? \n");
        }
        if (contentTypeId != null && !contentTypeId.isBlank() && !contentTypeId.equals("0")) {
            sql.append("AND content_type_id = ? \n");
        }


        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1; // ?의 순서를 추적할 변수
            if (sidoCode != null && !sidoCode.equals("0")) {
                pstmt.setInt(paramIndex++, Integer.parseInt(sidoCode));
            }
            if (gugunCode != null && !gugunCode.equals("0")) {
                pstmt.setInt(paramIndex++, Integer.parseInt(gugunCode));
            }
            if (contentTypeId != null && !contentTypeId.equals("0")) {
                pstmt.setInt(paramIndex++, Integer.parseInt(contentTypeId));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AttractionInfoDto dto = new AttractionInfoDto();
                    dto.setContentId(rs.getInt("content_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setAddr1(rs.getString("addr1"));
                    dto.setFirstImage1(rs.getString("first_image1"));
                    dto.setLatitude(rs.getDouble("latitude"));
                    dto.setLongitude(rs.getDouble("longitude"));
                    list.add(dto);
                }
            }
        }
        return list;
    }

    @Override
    public AttractionInfoDto getAttraction(int contentId) throws SQLException {
        AttractionInfoDto attractionInfo = null;
        String sql = "SELECT content_id, title, addr1, first_image1, latitude, longitude, content_type_id, area_code, si_gun_gu_code " +
                "FROM attractions " +
                "WHERE content_id = ?";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, contentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                // 결과가 있다면
                if (rs.next()) {
                    attractionInfo = new AttractionInfoDto();
                    attractionInfo.setContentId(rs.getInt("content_id"));
                    attractionInfo.setTitle(rs.getString("title"));
                    attractionInfo.setAddr1(rs.getString("addr1"));
                    attractionInfo.setFirstImage1(rs.getString("first_image1"));
                    attractionInfo.setLatitude(rs.getDouble("latitude"));
                    attractionInfo.setLongitude(rs.getDouble("longitude"));
                    attractionInfo.setContentTypeId(rs.getInt("content_type_id"));
                    attractionInfo.setAreaCode(rs.getInt("area_code"));
                    attractionInfo.setSigunguCode(rs.getInt("si_gun_gu_code"));
                }
            }
        }
        // 결과가 없으면 null이 반환됨
        return attractionInfo;
    }


    @Override
    public List<SidoDto> listSidos() throws SQLException {
        List<SidoDto> list = new ArrayList<>();
        String sql = "SELECT sido_code, sido_name FROM sidos ORDER BY sido_code";

        // try-with-resources 구문으로 자원 자동 해제
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                SidoDto sido = new SidoDto();
                sido.setSidoCode(rs.getInt("sido_code"));
                sido.setSidoName(rs.getString("sido_name"));
                list.add(sido);
            }
        }
        return list;
    }

    @Override
    public List<GugunDto> listGuguns(int sidoCode) throws SQLException {
        List<GugunDto> list = new ArrayList<>();
        // SQL의 WHERE 절을 사용해서 특정 sido_code에 해당하는 구/군만 가져온다.
        String sql = "SELECT gugun_code, gugun_name FROM guguns WHERE sido_code = ? ORDER BY gugun_name";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sidoCode); // SQL의 '?' 부분에 파라미터로 받은 sidoCode 값을 채운다.

            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    GugunDto gugun = new GugunDto();
                    gugun.setGugunCode(rs.getInt("gugun_code"));
                    gugun.setGugunName(rs.getString("gugun_name"));
                    gugun.setSidoCode(sidoCode);
                    list.add(gugun);
                }
            }
        }
        return list;
    }
    @Override
    public List<ContentTypeDto> listContentTypes() throws SQLException {
        List<ContentTypeDto> list = new ArrayList<>();
        String sql = "SELECT content_type_id, content_type_name FROM contenttypes";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()) {
                ContentTypeDto type = new ContentTypeDto();
                type.setContentTypeId(rs.getInt("content_type_id"));
                type.setContentTypeName(rs.getString("content_type_name"));
                list.add(type);
            }
        }
        return list;
    }
}
