package com.example.officebuilding.service.rental;

import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IRentalService extends IGeneralService<RentalDTO> {
    RentalDTO update(RentalDTO rentalDTO);
    RentalDTO createRentalWithRoomStatusChange(RentalDTO rentalDTO, Integer newRoomStatus);
    void cancelRental(Integer rentalId, String dateEnd);

    List<RentalDTO> findAllByStatus(Integer reStatus);
    List<RentalDTO> findAllByRoomIdAndStatus(Integer roomId, Integer reStatus);

    List<RentalDTO> findAllByRoomId(Integer roomId);

    List<RentalDTO> findRentalsWithinDateRange(Integer month,Integer year);

}
