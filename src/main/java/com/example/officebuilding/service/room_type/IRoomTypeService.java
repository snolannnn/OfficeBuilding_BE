package com.example.officebuilding.service.room_type;

import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IRoomTypeService extends IGeneralService<RoomTypeDTO> {
    public List<RoomTypeDTO> findAllRoomTypeOfHotel(Integer id);

    RoomTypeDTO update(RoomTypeDTO roomTypeDTO);
}
