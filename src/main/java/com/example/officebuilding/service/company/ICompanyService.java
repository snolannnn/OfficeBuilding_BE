package com.example.officebuilding.service.company;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface ICompanyService extends IGeneralService<CompanyDTO> {
    CompanyDTO update(CompanyDTO companyDTO);
    List<CompanyDTO> findAllByCStatus(Integer cusStatus);
}
