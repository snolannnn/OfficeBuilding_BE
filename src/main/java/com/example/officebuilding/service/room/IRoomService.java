package com.example.officebuilding.service.room;

import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IRoomService extends IGeneralService<RoomDTO> {
    RoomDTO update(RoomDTO roomDTO);
    List<RoomDTO> findAllRoomsByFloorId(Integer floorId);
    void updateRoomStatus(Integer roomId, Integer newStatus);
}
