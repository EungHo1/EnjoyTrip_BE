package ssafy.ps.enjoytrip_be.dao;

import ssafy.ps.enjoytrip_be.dto.AttractionInfoDto;
import ssafy.ps.enjoytrip_be.dto.ContentTypeDto;
import ssafy.ps.enjoytrip_be.dto.GugunDto;
import ssafy.ps.enjoytrip_be.dto.SidoDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AttractionDao {
    List<SidoDto> listSidos() throws SQLException;
    List<GugunDto> listGuguns(int sidoCode) throws SQLException;
    // AttractionDao.java
    List<ContentTypeDto> listContentTypes() throws SQLException;
    List<AttractionInfoDto> listAttractions(Map<String, String> params);

}
