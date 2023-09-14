package com.example.officebuilding.service.serviceContract;

import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.entities.RentalEntity;
import com.example.officebuilding.entities.ServiceContractEntity;
import com.example.officebuilding.repository.IServiceContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<ServiceContractDTO> findServiceContractsWithinDateRange(Integer month, Integer year) {

        List<ServiceContractEntity> serviceContractEntities = serviceContractRepository.findAll();

        List<ServiceContractDTO> filteredRentals = new ArrayList<>();
        for (ServiceContractEntity serviceContractEntity : serviceContractEntities){

            Integer yearBegin = Integer.parseInt(serviceContractEntity.getScDateBegin().substring(0, 4));
            Integer yearEnd = Integer.parseInt(serviceContractEntity.getScDateEnd().substring(0, 4));
            Integer monthBegin = Integer.parseInt(serviceContractEntity.getScDateBegin().substring(5, 7));
            Integer monthEnd = Integer.parseInt(serviceContractEntity.getScDateEnd().substring(5, 7));



            if (year - yearBegin ==0 && year - yearEnd == 0){

                if(month - monthBegin >=0 && monthEnd - month > 0){
                    ServiceContractDTO serviceContractDTO = modelMapper.map(serviceContractEntity, ServiceContractDTO.class);
                    filteredRentals.add(serviceContractDTO);
                }
            } else if (year - yearBegin == 0) {

                if(month - monthBegin >=0){
                    ServiceContractDTO serviceContractDTO = modelMapper.map(serviceContractEntity, ServiceContractDTO.class);
                    filteredRentals.add(serviceContractDTO);
                }
            } else if (year - yearEnd ==0) {

                if (monthEnd - month > 0){
                    ServiceContractDTO serviceContractDTO = modelMapper.map(serviceContractEntity, ServiceContractDTO.class);
                    filteredRentals.add(serviceContractDTO);
                }
            } else if(year - yearBegin >0 && yearEnd - year >0 ){
                ServiceContractDTO serviceContractDTO = modelMapper.map(serviceContractEntity, ServiceContractDTO.class);
                filteredRentals.add(serviceContractDTO);
            }
        }
        return filteredRentals;
    }
}
