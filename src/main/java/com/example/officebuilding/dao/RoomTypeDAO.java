package com.example.officebuilding.dao;

import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RoomTypeDAO {
    public void insertRoomTypeByHotelId(Integer hotelId, RoomTypeDTO roomTypeDTO){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            NativeQuery query = session.createSQLQuery("INSERT INTO bookinghotel.room_type(rt_bed_num,rt_desc,rt_name,rt_price,rt_size,hotel_id) VALUES(:rtBedNum,:rtDesc,:rtName,:rtPrice,:rtSize,:hotelId)");
            query.setParameter("rtBedNum",roomTypeDTO.getRtBedNum());
            query.setParameter("rtDesc",roomTypeDTO.getRtDesc());
            query.setParameter("rtName",roomTypeDTO.getRtName());
            query.setParameter("rtPrice",roomTypeDTO.getRtPrice());
            query.setParameter("rtSize",roomTypeDTO.getRtSize());
            query.setParameter("hotelId",hotelId);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            if (session.getTransaction() != null) session.getTransaction().rollback();
        }
    }
}
