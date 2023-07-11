package com.example.officebuilding.service.order;

import com.example.officebuilding.dtos.OrderDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IOrderService extends IGeneralService<OrderDTO> {
    List<OrderDTO> getOrdersByUserId(Integer id);
    List<OrderDTO> getOrdersByHotelId(Integer id);
    OrderDTO update(OrderDTO orderDTO);
}
