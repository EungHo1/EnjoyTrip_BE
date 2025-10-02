package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.AttractionInfoDto;
import ssafy.ps.enjoytrip_be.dto.ContentTypeDto;
import ssafy.ps.enjoytrip_be.dto.GugunDto;
import ssafy.ps.enjoytrip_be.dto.SidoDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AttractionService {
    List<SidoDto> listSidos() throws Exception;

    List<ContentTypeDto> listContentTypes() throws Exception;

    List<GugunDto> listGuguns(int sidoCode) throws Exception;

    List<AttractionInfoDto> listAttractions(Map<String, String> params) throws SQLException;

    AttractionInfoDto getAttraction(int contentId) throws SQLException;
}