package br.com.tiagoamp.timetracker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Test
    @DisplayName("When registered e-mail should return empty result")
    void findByEmail01() {
        Optional<UserEntity> result = repo.findByEmail("non-existing-email");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("When registered e-mail should return entity")
    void findByEmail02() {
        // given
        UserEntity userEntity = repo.save( new UserEntity(1L, "email@email.com", "name", "pass") );
        // when
        Optional<UserEntity> result = repo.findByEmail(userEntity.getEmail());
        // then
        assertFalse(result.isEmpty());
        assertEquals(userEntity.getEmail(), result.get().getEmail());
    }

}