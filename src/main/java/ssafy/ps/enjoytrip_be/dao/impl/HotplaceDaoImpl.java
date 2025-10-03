package ssafy.ps.enjoytrip_be.dao.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.HotplaceDao;
import ssafy.ps.enjoytrip_be.dto.HotplaceDto;
import ssafy.ps.enjoytrip_be.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotplaceDaoImpl implements HotplaceDao {
    private final DBUtil dbUtil = DBUtil.getInstance();
    @Getter
    private static final HotplaceDao instance = new HotplaceDaoImpl();

    private HotplaceDaoImpl() {}


    @Override
    public void create(HotplaceDto hotplaceDto) throws SQLException {
        String sql = "INSERT INTO hotplace (user_no, title, description, latitude, longitude, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, hotplaceDto.getUserNo());
            pstmt.setString(2, hotplaceDto.getTitle());
            pstmt.setString(3, hotplaceDto.getDescription());
            pstmt.setDouble(4, hotplaceDto.getLatitude());
            pstmt.setDouble(5, hotplaceDto.getLongitude());
            pstmt.setString(6, hotplaceDto.getImageUrl());

            pstmt.executeUpdate();
        }
    }

    @Override
    public List<HotplaceDto> findAll() throws SQLException {
        List<HotplaceDto> list = new ArrayList<>();
        String sql = "SELECT h.hotplace_id, h.user_no, u.user_name, h.title, h.description, " +
                "       h.latitude, h.longitude, h.image_url, h.created_date " +
                "FROM hotplace h JOIN user u ON h.user_no = u.user_no " +
                "ORDER BY h.created_date DESC";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                HotplaceDto dto = new HotplaceDto();
                dto.setHotplaceId(rs.getInt("hotplace_id"));
                dto.setUserNo(rs.getLong("user_no"));
                dto.setUserName(rs.getString("user_name")); // JOIN된 사용자 이름
                dto.setTitle(rs.getString("title"));
                dto.setDescription(rs.getString("description"));
                dto.setLatitude(rs.getDouble("latitude"));
                dto.setLongitude(rs.getDouble("longitude"));
                dto.setImageUrl(rs.getString("image_url"));
                dto.setCreatedDate(rs.getString("created_date"));
                list.add(dto);
            }
        }
        return list;
    }
}
