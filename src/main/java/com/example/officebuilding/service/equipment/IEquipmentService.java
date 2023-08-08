package com.example.officebuilding.service.equipment;


import com.example.officebuilding.dtos.EquipmentDTO;
import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IEquipmentService extends IGeneralService<EquipmentDTO> {
    EquipmentDTO update(EquipmentDTO equipmentDTO);
    List<EquipmentDTO> findAllEquipmentsByFloorId(Integer floorId);
}
