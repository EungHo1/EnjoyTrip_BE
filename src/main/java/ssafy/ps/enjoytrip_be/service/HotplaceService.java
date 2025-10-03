package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.HotplaceDto;

import java.util.List;

public interface HotplaceService {
    void create(HotplaceDto hotplaceDto) throws Exception;
    List<HotplaceDto> findAll() throws Exception;
}
