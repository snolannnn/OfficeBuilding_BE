package com.example.officebuilding.service.room;

import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IRoomService extends IGeneralService<RoomDTO> {

    List<RoomDTO> getRoomsByHotelId(Integer id);

    List<RoomDTO> getAllRoomsByHotelAndRoomType(Integer hotelId, Integer roomTypeId);
    RoomDTO update(RoomDTO roomDTO);

}
