package br.com.tiagoamp.timetracker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeEntryRepository extends PagingAndSortingRepository<TimeEntryEntity,Long> {

    @Query("SELECT t FROM TimeEntryEntity t WHERE t.categoryEntity.id = :categoryId")
    List<TimeEntryEntity> retrieveByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT t FROM TimeEntryEntity t WHERE t.userEntity.id = :userId AND t.id = :timeId")
    List<TimeEntryEntity> retrieveByUserAndId(@Param("userId") Long userId, @Param("timeId") Long timeId);

}
