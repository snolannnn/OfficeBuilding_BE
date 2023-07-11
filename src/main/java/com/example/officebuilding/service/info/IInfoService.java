package com.example.officebuilding.service.info;

import com.example.officebuilding.dtos.InfoDTO;
import com.example.officebuilding.dtos.OrderDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IInfoService extends IGeneralService<InfoDTO> {
    List<InfoDTO> getInfosByUserId(Integer id);
}
