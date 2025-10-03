package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.dto.HotplaceDto;

import java.sql.SQLException;
import java.util.List;

public interface HotplaceDao {
    void create(HotplaceDto hotplaceDto) throws SQLException;
    List<HotplaceDto> findAll() throws SQLException;
}