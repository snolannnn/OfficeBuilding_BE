package com.example.officebuilding.service.order;

import com.example.officebuilding.dtos.HotelDTO;
import com.example.officebuilding.dtos.OrderDTO;
import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.entities.HotelEntity;
import com.example.officebuilding.entities.OrderEntity;
import com.example.officebuilding.entities.RoomEntity;
import com.example.officebuilding.entities.RoomTypeEntity;
import com.example.officebuilding.repository.IHotelRepository;
import com.example.officebuilding.repository.IOrderRepository;
import com.example.officebuilding.repository.IRoomRepository;
import com.example.officebuilding.repository.IRoomTypeRepository;
import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.security.repo.IUserRepository;
import com.example.officebuilding.service.room.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IHotelRepository hotelRepository;

    @Autowired
    private IRoomTypeRepository roomTypeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<OrderDTO> findAll(){
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDTO> orderDTOS = orderEntities.stream()
                .map(orderEntity -> modelMapper.map(orderEntity,OrderDTO.class))
                .collect(Collectors.toList());
        return orderDTOS;
    }
    @Override
    public Optional<OrderDTO> findById(Integer id){
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        Optional<OrderDTO> orderDTO = orderEntity
                .map(orderEntity1 -> modelMapper.map(orderEntity1,OrderDTO.class));
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Integer id){
        List<OrderEntity> orderEntities = orderRepository.findByUserId(id);
        return orderEntities.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByHotelId(Integer id){
        List<OrderEntity> orderEntities = orderRepository.findByHotelId(id);
        return orderEntities.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, OrderDTO.class))
                .collect(Collectors.toList());
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
    public OrderDTO save(OrderDTO orderDTO) {

        // Kiểm tra xem roomType tồn tại trong hotel hay không
        if (orderDTO.getRoomTypeId() != null) {
            boolean roomTypeExists = roomTypeRepository.existsByIdAndHotelId(orderDTO.getRoomTypeId(), orderDTO.getHotelId());
            if (!roomTypeExists) {
                throw new OrderService.NotFoundException("Room Type not found in the hotel");
            }
        }

        // chuyển từ DTO sang entity:
        OrderEntity orderEntity = modelMapper.map(orderDTO, OrderEntity.class);

        // Lấy đối tượng HotelEntity từ cơ sở dữ liệu
        HotelEntity hotelEntity = hotelRepository.findById(orderDTO.getHotelId())
                .orElseThrow(() -> new OrderService.NotFoundException("Hotel not found"));
        // Lấy đối tượng RoomTypeEntity từ cơ sở dữ liệu
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(orderDTO.getRoomTypeId())
                .orElseThrow(() -> new OrderService.NotFoundException("Room Type not found"));
        // Lấy đối tượng RoomTypeEntity từ cơ sở dữ liệu
        User userEntity = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new OrderService.NotFoundException("User not found"));



        // Thiết lập đối tượng HotelEntity vaf RoomTypeEntity cho RoomTypeEntity
        orderEntity.setHotel(hotelEntity);
        orderEntity.setRoomType(roomTypeEntity);
        orderEntity.setUser(userEntity);


        // save xuống db:
        OrderEntity updatedEntity = orderRepository.save(orderEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,OrderDTO.class);
    }


    @Override
    public void remove(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO){
        OrderEntity orderEntity = modelMapper.map(orderDTO,OrderEntity.class);
        OrderEntity updatedOrderEntity = orderRepository.save(orderEntity);
        return modelMapper.map(updatedOrderEntity,OrderDTO.class);
    }

}
