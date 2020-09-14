package br.com.tiagoamp.timetracker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private UserRepository userRepo;



    @Test
    @DisplayName("When user does not have categories registered should return empty list")
    void retrieveByUser1() {
        List<CategoryEntity> result = catRepo.retrieveByUser(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("When user has categories should return categories list")
    void retrieveByUser2() {
        // given
        UserEntity userEntity = userRepo.save( new UserEntity(1L, "email@email.com", "name", "pass") );
        CategoryEntity categoryEntity = catRepo.save(new CategoryEntity(10, "cat name", "cat desc", userEntity));
        // when
        List<CategoryEntity> result = catRepo.retrieveByUser(userEntity.getId());
        // then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(categoryEntity.getId(), result.get(0).getId());
    }

}