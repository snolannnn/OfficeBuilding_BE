package com.example.officebuilding.service.service;

import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.entities.ServiceEntity;
import com.example.officebuilding.repository.IServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceService implements IServiceService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public List<ServiceDTO> findAll(){
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        List<ServiceDTO> serviceDTOS = serviceEntities.stream().map(serviceEntity -> modelMapper.map(serviceEntity, ServiceDTO.class)).collect(Collectors.toList());
        return serviceDTOS;
    }

    @Override
    public Optional<ServiceDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return serviceEntity.map(serviceEntity1 -> modelMapper.map(serviceEntity1, ServiceDTO.class));
    }
    @Override
    public ServiceDTO save(ServiceDTO serviceDTO){
        ServiceEntity serviceEntity = modelMapper.map(serviceDTO, ServiceEntity.class);
        ServiceEntity updateServiceEntity = serviceRepository.save(serviceEntity);
        return modelMapper.map(updateServiceEntity,ServiceDTO.class);
    }

    @Override
    public ServiceDTO update(ServiceDTO serviceDTO){
        ServiceEntity serviceEntity = modelMapper.map(serviceDTO,ServiceEntity.class);
        ServiceEntity updatedServiceEntity = serviceRepository.save(serviceEntity);
        return modelMapper.map(updatedServiceEntity,ServiceDTO.class);
    }
    @Override
    public void remove(Integer id){
        serviceRepository.deleteById(id);
    }
}
