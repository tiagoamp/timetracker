package br.com.tiagoamp.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.user.id = :userId")
    List<CategoryEntity> retrieveByUser(@Param("userId") String userId);

}
