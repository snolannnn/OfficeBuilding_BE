package com.example.officebuilding.service.floor;

import com.example.officebuilding.dtos.FloorDTO;
import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.repository.IFloorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FloorService implements IFloorService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IFloorRepository floorRepository;

    @Override
    public List<FloorDTO> findAll(){
        List<FloorEntity> floorEntities = floorRepository.findAll();
        List<FloorDTO> floorDTOS = floorEntities.stream().map(floorEntity -> modelMapper.map(floorEntity, FloorDTO.class)).collect(Collectors.toList());
        return floorDTOS;
    }

    @Override
    public Optional<FloorDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<FloorEntity> floorEntity = floorRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return floorEntity.map(floorEntity1 -> modelMapper.map(floorEntity1, FloorDTO.class));
    }
    @Override
    public FloorDTO save(FloorDTO floorDTO){
        FloorEntity floorEntity = modelMapper.map(floorDTO, FloorEntity.class);
        FloorEntity updateFloorEntity = floorRepository.save(floorEntity);
        return modelMapper.map(updateFloorEntity,FloorDTO.class);
    }

    @Override
    public FloorDTO update(FloorDTO floorDTO){
        FloorEntity floorEntity = modelMapper.map(floorDTO,FloorEntity.class);
        FloorEntity updatedFloorEntity = floorRepository.save(floorEntity);
        return modelMapper.map(updatedFloorEntity,FloorDTO.class);
    }
    @Override
    public void remove(Integer id){
        floorRepository.deleteById(id);
    }
}
