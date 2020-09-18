package br.com.tiagoamp.timetracker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TimeEntryRepositoryTest {

    @Autowired
    private TimeEntryRepository timeRepo;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private UserRepository userRepo;


    @Test
    @DisplayName("When category does not have time entries should return empty list")
    void retrieveByCategory1() {
        List<TimeEntryEntity> result = timeRepo.retrieveByCategory(10L);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("When user has time entries for category should return categories list")
    void retrieveByCategory2() {
        // given
        UserEntity userEntity = userRepo.save( new UserEntity(null, "email@email.com", "name", "pass") );
        CategoryEntity categoryEntity = catRepo.save( new CategoryEntity(null, "cat name", "cat desc", userEntity) );
        TimeEntryEntity timeEntity = timeRepo.save( new TimeEntryEntity(null, LocalDateTime.now(), LocalDateTime.now(), null, categoryEntity, userEntity) );
        // when
        List<TimeEntryEntity> result = timeRepo.retrieveByCategory(categoryEntity.getId());
        // then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(timeEntity.getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("When does not exist time entries should return empty list")
    void retrieveByUserAndId1() {
        List<TimeEntryEntity> result = timeRepo.retrieveByUserAndId(1L, 10L);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("When user has time entries should return categories list")
    void retrieveByUserAndId2() {
        // given
        UserEntity userEntity = userRepo.save( new UserEntity(null, "email@email.com", "name", "pass") );
        CategoryEntity categoryEntity = catRepo.save( new CategoryEntity(null, "cat name", "cat desc", userEntity) );
        TimeEntryEntity timeEntity = timeRepo.save( new TimeEntryEntity(null, LocalDateTime.now(), LocalDateTime.now(), null, categoryEntity, userEntity) );
        // when
        List<TimeEntryEntity> result = timeRepo.retrieveByUserAndId(userEntity.getId(), timeEntity.getId());
        // then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(timeEntity.getId(), result.get(0).getId());
    }

}