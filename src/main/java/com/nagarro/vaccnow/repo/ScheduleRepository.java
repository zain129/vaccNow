package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("select max(s.scheduleId) from Schedule s")
    Integer getMaxId();

}
