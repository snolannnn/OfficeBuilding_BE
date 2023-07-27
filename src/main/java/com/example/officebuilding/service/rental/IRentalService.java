package com.example.officebuilding.service.rental;

import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IRentalService extends IGeneralService<RentalDTO> {
    RentalDTO update(RentalDTO rentalDTO);
}
