package com.example.officebuilding.service.room;

import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.entities.RoomEntity;
import com.example.officebuilding.repository.IRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRoomRepository roomRepository;

    @Override
    public List<RoomDTO> findAll(){
        List<RoomEntity> roomEntities = roomRepository.findAll();
        List<RoomDTO> roomDTOS = roomEntities.stream().map(roomEntity -> modelMapper.map(roomEntity, RoomDTO.class)).collect(Collectors.toList());
        return roomDTOS;
    }

    @Override
    public Optional<RoomDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return roomEntity.map(roomEntity1 -> modelMapper.map(roomEntity1, RoomDTO.class));
    }
    @Override
    public RoomDTO save(RoomDTO roomDTO){
        RoomEntity roomEntity = modelMapper.map(roomDTO, RoomEntity.class);
        RoomEntity updateRoomEntity = roomRepository.save(roomEntity);
        return modelMapper.map(updateRoomEntity,RoomDTO.class);
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO){
        RoomEntity roomEntity = modelMapper.map(roomDTO,RoomEntity.class);
        RoomEntity updatedRoomEntity = roomRepository.save(roomEntity);
        return modelMapper.map(updatedRoomEntity,RoomDTO.class);
    }
    @Override
    public void remove(Integer id){
        roomRepository.deleteById(id);
    }
}
