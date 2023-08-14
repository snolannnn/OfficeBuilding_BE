package com.example.officebuilding.service.serviceContract;

import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.entities.ServiceContractEntity;
import com.example.officebuilding.repository.IServiceContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceContractService implements IServiceContractService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IServiceContractRepository serviceContractRepository;

    @Override
    public List<ServiceContractDTO> findAll(){
        List<ServiceContractEntity> serviceContractEntities = serviceContractRepository.findAll();
        List<ServiceContractDTO> serviceContractDTOS = serviceContractEntities.stream().map(serviceContractEntity -> modelMapper.map(serviceContractEntity, ServiceContractDTO.class)).collect(Collectors.toList());
        return serviceContractDTOS;
    }

    @Override
    public Optional<ServiceContractDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<ServiceContractEntity> serviceContractEntity = serviceContractRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return serviceContractEntity.map(serviceContractEntity1 -> modelMapper.map(serviceContractEntity1, ServiceContractDTO.class));
    }
    @Override
    public ServiceContractDTO save(ServiceContractDTO serviceContractDTO){
        ServiceContractEntity serviceContractEntity = modelMapper.map(serviceContractDTO, ServiceContractEntity.class);
        ServiceContractEntity updateServiceContractEntity = serviceContractRepository.save(serviceContractEntity);
        return modelMapper.map(updateServiceContractEntity,ServiceContractDTO.class);
    }

    @Override
    public ServiceContractDTO update(ServiceContractDTO serviceContractDTO){
        ServiceContractEntity serviceContractEntity = modelMapper.map(serviceContractDTO,ServiceContractEntity.class);
        ServiceContractEntity updatedServiceContractEntity = serviceContractRepository.save(serviceContractEntity);
        return modelMapper.map(updatedServiceContractEntity,ServiceContractDTO.class);
    }
    @Override
    public void remove(Integer id){
        serviceContractRepository.deleteById(id);
    }

    @Override
    public List<ServiceContractDTO> findAllByStatus(Integer scStatus){
        List<ServiceContractEntity> serviceContractEntities = serviceContractRepository.findByScStatus(scStatus);
        return serviceContractEntities.stream()
                .map((serviceContractEntity -> modelMapper.map(serviceContractEntity, ServiceContractDTO.class)))
                .collect(Collectors.toList());
    }
}
