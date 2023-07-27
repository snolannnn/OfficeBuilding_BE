package com.example.officebuilding.service.serviceContract;

import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IServiceContractService extends IGeneralService<ServiceContractDTO> {
    ServiceContractDTO update(ServiceContractDTO serviceContractDTO);
}
