package ssafy.ps.enjoytrip_be.service.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.AttractionDao;
import ssafy.ps.enjoytrip_be.dao.impl.AttractionDaoImpl;
import ssafy.ps.enjoytrip_be.dto.AttractionInfoDto;
import ssafy.ps.enjoytrip_be.dto.ContentTypeDto;
import ssafy.ps.enjoytrip_be.dto.GugunDto;
import ssafy.ps.enjoytrip_be.dto.SidoDto;
import ssafy.ps.enjoytrip_be.service.AttractionService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AttractionServiceImpl implements AttractionService {
    @Getter
    private static AttractionService instance = new AttractionServiceImpl();
    private final AttractionDao attractionDao = AttractionDaoImpl.getInstance();

    private AttractionServiceImpl() {
    }

    @Override
    public List<SidoDto> listSidos() throws Exception {
        return attractionDao.listSidos();

    }
    @Override
    public List<ContentTypeDto> listContentTypes() throws Exception {
        return attractionDao.listContentTypes();
    }


    @Override
    public List<GugunDto> listGuguns(int sidoCode) throws Exception {
        return attractionDao.listGuguns(sidoCode);
    }

    @Override
    public List<AttractionInfoDto> listAttractions(Map<String, String> params) throws SQLException {
        return attractionDao.listAttractions(params);
    }
}
