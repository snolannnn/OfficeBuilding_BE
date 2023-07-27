package com.example.officebuilding.service.customer;

import com.example.officebuilding.dtos.CustomerDTO;
import com.example.officebuilding.service.IGeneralService;

public interface ICustomerService extends IGeneralService<CustomerDTO> {
    CustomerDTO update(CustomerDTO customerDTO);
}
