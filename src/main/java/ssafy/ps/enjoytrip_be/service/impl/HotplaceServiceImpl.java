package ssafy.ps.enjoytrip_be.service.impl;

import lombok.Getter;
import ssafy.ps.enjoytrip_be.dao.HotplaceDao;
import ssafy.ps.enjoytrip_be.dao.impl.HotplaceDaoImpl;
import ssafy.ps.enjoytrip_be.dto.HotplaceDto;
import ssafy.ps.enjoytrip_be.service.HotplaceService;

import java.util.List;

public class HotplaceServiceImpl implements HotplaceService {
    @Getter
    private static final HotplaceService instance = new HotplaceServiceImpl();
    private final HotplaceDao hotplaceDao = HotplaceDaoImpl.getInstance();

    private HotplaceServiceImpl() {
    }

    public static HotplaceService getInstance() {
        return instance;
    }
    @Override
    public void create(HotplaceDto hotplaceDto) throws Exception {
        hotplaceDao.create(hotplaceDto);
    }

    @Override
    public List<HotplaceDto> findAll() throws Exception {
        return hotplaceDao.findAll();
    }




}
