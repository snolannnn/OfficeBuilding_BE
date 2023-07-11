package com.example.officebuilding.service.hotel;

import com.example.officebuilding.dtos.HotelDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IHotelService extends IGeneralService<HotelDTO> {
    HotelDTO update(HotelDTO hotelDTO);
}
