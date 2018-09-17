package com.ashu.parkinglot.repository;

import com.ashu.parkinglot.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSlot, Integer> {

    List<ParkingSlot> findAllByOccupiedTrue();

    List<ParkingSlot> findAllByOccupiedTrueAndColor(String colour);

    ParkingSlot findByOccupiedTrueAndRegistrationNo(String colour);

    ParkingSlot findFirstByOccupiedFalse();

    ParkingSlot findBySlotNo(int slotNo);

    @Modifying
    @Query("update com.jek.parkinglot.model.ParkingSlot ci set ci.occupied=:occupied where ci.slotNo = :slotNo")
    int updateOccupancyBySlotNo(@Param("occupied") boolean occupied, @Param("slotNo") int slotNo);

}
