package com.example.officebuilding.service.room;

import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IRoomService extends IGeneralService<RoomDTO> {
    RoomDTO update(RoomDTO roomDTO);
}
