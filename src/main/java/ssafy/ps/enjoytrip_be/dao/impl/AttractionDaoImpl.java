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
                    gugun.setSidoCode(sidoCode); // sidoCode는 이미 알고 있으니 그냥 넣어줌
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

    @Override
    public List<AttractionInfoDto> listAttractions(Map<String, String> params) {
        return List.of();
    }

}
