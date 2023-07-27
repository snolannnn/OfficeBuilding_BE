package com.example.officebuilding.service.customer;

import com.example.officebuilding.dtos.CustomerDTO;
import com.example.officebuilding.entities.CustomerEntity;
import com.example.officebuilding.repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> findAll(){
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customerEntities.stream().map(customerEntity -> modelMapper.map(customerEntity, CustomerDTO.class)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public Optional<CustomerDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return customerEntity.map(customerEntity1 -> modelMapper.map(customerEntity1, CustomerDTO.class));
    }
    @Override
    public CustomerDTO save(CustomerDTO customerDTO){
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        CustomerEntity updateCustomerEntity = customerRepository.save(customerEntity);
        return modelMapper.map(updateCustomerEntity,CustomerDTO.class);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO){
        CustomerEntity customerEntity = modelMapper.map(customerDTO,CustomerEntity.class);
        CustomerEntity updatedCustomerEntity = customerRepository.save(customerEntity);
        return modelMapper.map(updatedCustomerEntity,CustomerDTO.class);
    }
    @Override
    public void remove(Integer id){
        customerRepository.deleteById(id);
    }
}
