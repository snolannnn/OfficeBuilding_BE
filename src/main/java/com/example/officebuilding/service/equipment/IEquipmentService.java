package com.example.officebuilding.service.equipment;


import com.example.officebuilding.dtos.EquipmentDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IEquipmentService extends IGeneralService<EquipmentDTO> {
    EquipmentDTO update(EquipmentDTO equipmentDTO);
}
