package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.ContentTypeDto;
import ssafy.ps.enjoytrip_be.dto.GugunDto;
import ssafy.ps.enjoytrip_be.dto.SidoDto;

import java.util.List;

public interface AttractionService {
    List<SidoDto> listSidos() throws Exception;

    List<ContentTypeDto> listContentTypes() throws Exception;

    List<GugunDto> listGuguns(int sidoCode) throws Exception;

}