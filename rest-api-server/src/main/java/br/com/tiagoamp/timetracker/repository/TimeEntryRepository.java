package br.com.tiagoamp.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntryEntity,Long> {

    @Query("SELECT t FROM TimeEntryEntity t WHERE t.category.id = :categoryId")
    List<TimeEntryEntity> retrieveByCategory(@Param("categoryId") Long categoryId);

}
