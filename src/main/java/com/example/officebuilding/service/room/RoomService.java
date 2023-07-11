package com.example.officebuilding.service.room;

import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.entities.HotelEntity;
import com.example.officebuilding.entities.RoomEntity;
import com.example.officebuilding.entities.RoomTypeEntity;
import com.example.officebuilding.repository.IHotelRepository;
import com.example.officebuilding.repository.IRoomRepository;
import com.example.officebuilding.repository.IRoomTypeRepository;
import com.example.officebuilding.service.room_type.RoomTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IHotelRepository hotelRepository;

    @Autowired
    private IRoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomDTO> findAll(){
        List<RoomEntity> roomEntities = roomRepository.findAll();
        List<RoomDTO> roomDTOS = roomEntities.stream()
                .map(roomEntity -> modelMapper.map(roomEntity,RoomDTO.class))
                .collect(Collectors.toList());
        return roomDTOS;
    }

    @Override
    public Optional<RoomDTO> findById(Integer id){
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);
        Optional<RoomDTO> roomDTO = roomEntity
                .map(roomEntity1 -> modelMapper.map(roomEntity1,RoomDTO.class));
        return roomDTO;
    }

    public class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }

        public NotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class RoomNumberExistsException extends RuntimeException {
        public RoomNumberExistsException(String message) {
            super(message);
        }
    }
    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        // Kiểm tra xem roomNumber đã tồn tại trong hotel chưa
        boolean roomNumberExists = roomRepository.existsByHotelIdAndRmNumber(roomDTO.getHotelId(), roomDTO.getRmNumber());
        if (roomNumberExists) {
            throw new RoomService.RoomNumberExistsException("Room number already exists in the hotel");
        }

        // Kiểm tra xem roomType tồn tại trong hotel hay không
        if (roomDTO.getRoomTypeId() != null) {
            boolean roomTypeExists = roomTypeRepository.existsByIdAndHotelId(roomDTO.getRoomTypeId(), roomDTO.getHotelId());
            if (!roomTypeExists) {
                throw new RoomService.NotFoundException("Room Type not found in the hotel");
            }
        }

        // chuyển từ DTO sang entity:
        RoomEntity roomEntity = modelMapper.map(roomDTO, RoomEntity.class);

        // Lấy đối tượng HotelEntity từ cơ sở dữ liệu
        HotelEntity hotelEntity = hotelRepository.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RoomService.NotFoundException("Hotel not found"));
        // Lấy đối tượng RoomTypeEntity từ cơ sở dữ liệu
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(roomDTO.getRoomTypeId())
                .orElseThrow(() -> new RoomService.NotFoundException("Room Type not found"));

        // Thiết lập đối tượng HotelEntity vaf RoomTypeEntity cho RoomTypeEntity
        roomEntity.setHotel(hotelEntity);
        roomEntity.setRoomType(roomTypeEntity);


        // save xuống db:
        RoomEntity updatedEntity = roomRepository.save(roomEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,RoomDTO.class);
    }

    @Override
    public void remove(Integer id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDTO> getRoomsByHotelId(Integer id) {
        List<RoomEntity> roomEntities = roomRepository.findByHotelId(id);
        return roomEntities.stream()
                .map(roomEntity -> modelMapper.map(roomEntity, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getAllRoomsByHotelAndRoomType(Integer hotelId, Integer roomTypeId) {
        // Lấy danh sách RoomEntity từ cơ sở dữ liệu dựa trên hotelId và roomTypeId
        List<RoomEntity> roomEntities = roomRepository.findAllByHotelIdAndRoomTypeId(hotelId, roomTypeId);

        // Chuyển đổi danh sách RoomEntity sang danh sách RoomDTO
        List<RoomDTO> roomDTOs = roomEntities.stream()
                .map(roomEntity -> modelMapper.map(roomEntity, RoomDTO.class))
                .collect(Collectors.toList());

        return roomDTOs;
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO){
        // Kiểm tra xem phòng có tồn tại không
        RoomEntity existingRoom = roomRepository.findById(roomDTO.getId())
                .orElseThrow(() -> new RoomService.NotFoundException("Room not found"));
        // Kiểm tra xem roomNumber đã tồn tại trong hotel chưa
        if (!existingRoom.getRmNumber().equals(roomDTO.getRmNumber())){
            boolean roomNumberExists = roomRepository.existsByHotelIdAndRmNumber(roomDTO.getHotelId(), roomDTO.getRmNumber());
            if (roomNumberExists) {
                throw new RoomService.RoomNumberExistsException("Room number already exists in the hotel");
            }
        }



        // Kiểm tra xem roomType tồn tại trong hotel hay không
        if (roomDTO.getRoomTypeId() != null) {
            boolean roomTypeExists = roomTypeRepository.existsByIdAndHotelId(roomDTO.getRoomTypeId(), roomDTO.getHotelId());
            if (!roomTypeExists) {
                throw new RoomService.NotFoundException("Room Type not found in the hotel");
            }
        }

        // chuyển từ DTO sang entity:
        RoomEntity roomEntity = modelMapper.map(roomDTO, RoomEntity.class);

        // Lấy đối tượng HotelEntity từ cơ sở dữ liệu
        HotelEntity hotelEntity = hotelRepository.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RoomService.NotFoundException("Hotel not found"));
        // Lấy đối tượng RoomTypeEntity từ cơ sở dữ liệu
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(roomDTO.getRoomTypeId())
                .orElseThrow(() -> new RoomService.NotFoundException("Room Type not found"));

        // Thiết lập đối tượng HotelEntity vaf RoomTypeEntity cho RoomTypeEntity
        roomEntity.setHotel(hotelEntity);
        roomEntity.setRoomType(roomTypeEntity);
        // save xuống db:
        RoomEntity updatedEntity = roomRepository.save(roomEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,RoomDTO.class);

    }
}
