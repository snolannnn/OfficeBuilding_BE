package com.example.officebuilding.service.hotel;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.dtos.HotelDTO;
import com.example.officebuilding.entities.CompanyEntity;
import com.example.officebuilding.entities.HotelEntity;
import com.example.officebuilding.repository.ICompanyRepository;
import com.example.officebuilding.repository.IHotelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IHotelRepository hotelRepository;

    @Override
    public List<HotelDTO> findAll(){
        List<HotelEntity> hotelEntities = hotelRepository.findAll();
        List<HotelDTO> hotelDTOS = hotelEntities.stream().map(hotelEntity -> modelMapper.map(hotelEntity, HotelDTO.class)).collect(Collectors.toList());
        return hotelDTOS;
    }

    @Override
    public Optional<HotelDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<HotelEntity> hotelEntity = hotelRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return hotelEntity.map(hotelEntity1 -> modelMapper.map(hotelEntity1, HotelDTO.class));
    }

    @Override
    public HotelDTO save(HotelDTO hotelDTO){
        HotelEntity hotelEntity = modelMapper.map(hotelDTO, HotelEntity.class);
        HotelEntity updateHotelEntity = hotelRepository.save(hotelEntity);
        return modelMapper.map(updateHotelEntity,HotelDTO.class);
    }

    @Override
    public HotelDTO update(HotelDTO hotelDTO){
        HotelEntity hotelEntity = modelMapper.map(hotelDTO,HotelEntity.class);
        HotelEntity updatedHotelEntity = hotelRepository.save(hotelEntity);
        return modelMapper.map(updatedHotelEntity,HotelDTO.class);
    }
    @Override
    public void remove(Integer id){
        hotelRepository.deleteById(id);
    }

}
