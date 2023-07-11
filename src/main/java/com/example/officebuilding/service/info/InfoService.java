package com.example.officebuilding.service.info;

import com.example.officebuilding.dtos.HotelDTO;
import com.example.officebuilding.dtos.InfoDTO;
import com.example.officebuilding.dtos.OrderDTO;
import com.example.officebuilding.entities.HotelEntity;
import com.example.officebuilding.entities.InfoEntity;
import com.example.officebuilding.entities.OrderEntity;
import com.example.officebuilding.repository.IHotelRepository;
import com.example.officebuilding.repository.IInfoRepository;
import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.security.repo.IUserRepository;
import com.example.officebuilding.service.order.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InfoService implements IInfoService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IInfoRepository infoRepository;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<InfoDTO> findAll(){
        List<InfoEntity> infoEntities = infoRepository.findAll();
        List<InfoDTO> infoDTOS = infoEntities.stream().map(infoEntity -> modelMapper.map(infoEntity, InfoDTO.class)).collect(Collectors.toList());
        return infoDTOS;
    }

    @Override
    public Optional<InfoDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<InfoEntity> infoEntity = infoRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return infoEntity.map(infoEntity1 -> modelMapper.map(infoEntity1, InfoDTO.class));
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
    public List<InfoDTO> getInfosByUserId(Integer id){
        List<InfoEntity> infoEntities = infoRepository.findByUserId(id);
        return infoEntities.stream()
                .map(infoEntity -> modelMapper.map(infoEntity, InfoDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public InfoDTO save(InfoDTO infoDTO){
        InfoEntity infoEntity = modelMapper.map(infoDTO, InfoEntity.class);

        // Lấy đối tượng RoomTypeEntity từ cơ sở dữ liệu
        User userEntity = userRepository.findById(infoDTO.getUserId())
                .orElseThrow(() -> new InfoService.NotFoundException("User not found"));
        infoEntity.setUser(userEntity);


        InfoEntity updateInfoEntity = infoRepository.save(infoEntity);
        return modelMapper.map(updateInfoEntity,InfoDTO.class);
    }
    @Override
    public void remove(Integer id){
        infoRepository.deleteById(id);
    }


}
