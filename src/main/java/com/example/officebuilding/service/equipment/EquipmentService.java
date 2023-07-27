package com.example.officebuilding.service.equipment;

import com.example.officebuilding.dtos.EquipmentDTO;
import com.example.officebuilding.entities.EquipEntity;
import com.example.officebuilding.repository.IEquipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService implements IEquipmentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEquipmentRepository equipmentRepository;

    @Override
    public List<EquipmentDTO> findAll(){
        List<EquipEntity> equipmentEntities = equipmentRepository.findAll();
        List<EquipmentDTO> equipmentDTOS = equipmentEntities.stream().map(equipmentEntity -> modelMapper.map(equipmentEntity, EquipmentDTO.class)).collect(Collectors.toList());
        return equipmentDTOS;
    }

    @Override
    public Optional<EquipmentDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<EquipEntity> equipmentEntity = equipmentRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return equipmentEntity.map(equipmentEntity1 -> modelMapper.map(equipmentEntity1, EquipmentDTO.class));
    }
    @Override
    public EquipmentDTO save(EquipmentDTO equipmentDTO){
        EquipEntity equipmentEntity = modelMapper.map(equipmentDTO, EquipEntity.class);
        EquipEntity updateEquipEntity = equipmentRepository.save(equipmentEntity);
        return modelMapper.map(updateEquipEntity,EquipmentDTO.class);
    }

    @Override
    public EquipmentDTO update(EquipmentDTO equipmentDTO){
        EquipEntity equipmentEntity = modelMapper.map(equipmentDTO,EquipEntity.class);
        EquipEntity updatedEquipEntity = equipmentRepository.save(equipmentEntity);
        return modelMapper.map(updatedEquipEntity,EquipmentDTO.class);
    }
    @Override
    public void remove(Integer id){
        equipmentRepository.deleteById(id);
    }
}
