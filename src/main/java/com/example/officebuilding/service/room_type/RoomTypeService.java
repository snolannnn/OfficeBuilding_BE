package com.example.officebuilding.service.room_type;

import com.example.officebuilding.dtos.CompanyEmployeeDTO;
import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.entities.CompanyEmployeeEntity;
import com.example.officebuilding.entities.HotelEntity;
import com.example.officebuilding.entities.RoomTypeEntity;
import com.example.officebuilding.repository.IHotelRepository;
import com.example.officebuilding.repository.IRoomTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class RoomTypeService implements IRoomTypeService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IRoomTypeRepository roomTypeRepository;

    @Autowired
    private IHotelRepository hotelRepository;

    @Override
    public List<RoomTypeDTO> findAll(){
        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAll();
        List<RoomTypeDTO> roomTypeDTOS = roomTypeEntities.stream()
                .map(roomTypeEntity -> modelMapper.map(roomTypeEntity,RoomTypeDTO.class))
                .collect(Collectors.toList());
        return roomTypeDTOS;
    }

    @Override
    public Optional<RoomTypeDTO> findById(Integer id){
        Optional<RoomTypeEntity> roomTypeEntity = roomTypeRepository.findById(id);
        Optional<RoomTypeDTO> roomTypeDTO = roomTypeEntity
                .map(roomTypeEntity1 -> modelMapper.map(roomTypeEntity1,RoomTypeDTO.class));
        return roomTypeDTO;
    }


    public class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }

        public NotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    @Override
    public RoomTypeDTO save(RoomTypeDTO roomTypeDTO) {
        // chuyển từ DTO sang entity:
        RoomTypeEntity roomTypeEntity = modelMapper.map(roomTypeDTO, RoomTypeEntity.class);

        // Lấy đối tượng HotelEntity từ cơ sở dữ liệu
        HotelEntity hotelEntity = hotelRepository.findById(roomTypeDTO.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found"));

        // Thiết lập đối tượng HotelEntity cho RoomTypeEntity
        roomTypeEntity.setHotel(hotelEntity);


        // save xuống db:
        RoomTypeEntity updatedEntity = roomTypeRepository.save(roomTypeEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,RoomTypeDTO.class);
    }

    @Override
    public RoomTypeDTO update(RoomTypeDTO roomTypeDTO){
        RoomTypeEntity roomTypeEntity = modelMapper.map(roomTypeDTO,RoomTypeEntity.class);

        // Lấy đối tượng HotelEntity từ cơ sở dữ liệu
        HotelEntity hotelEntity = hotelRepository.findById(roomTypeDTO.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found"));

        // Thiết lập đối tượng HotelEntity cho RoomTypeEntity
        roomTypeEntity.setHotel(hotelEntity);

        RoomTypeEntity updateRoomTypeEntity = roomTypeRepository.save(roomTypeEntity);
        return modelMapper.map(updateRoomTypeEntity,RoomTypeDTO.class);
    }

    @Override
    public void remove(Integer id) {
        roomTypeRepository.deleteById(id);
    }

    @Override
    public List<RoomTypeDTO> findAllRoomTypeOfHotel(Integer id){
        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.getRoomTypeEntitiesByHotel_Id(id);
        List<RoomTypeDTO> roomTypeDTOs = roomTypeEntities.stream()
                .map(roomTypeEntity -> modelMapper.map(roomTypeEntity,RoomTypeDTO.class))
                .collect(Collectors.toList());
        return roomTypeDTOs;
    }
}
