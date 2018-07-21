package com.jek.parkinglot.repository;

import com.jek.parkinglot.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllByOccupiedTrue();

    Vehicle findFirstByOccupiedFalse();

    Vehicle findBySlotNo(int slotNo);

    @Modifying
    @Query("update com.jek.parkinglot.model.Vehicle ci set ci.occupied=:occupied where ci.slotNo = :slotNo")
    int updateOccupancyBySlotNo(@Param("occupied") boolean occupied, @Param("slotNo") int slotNo);

}
