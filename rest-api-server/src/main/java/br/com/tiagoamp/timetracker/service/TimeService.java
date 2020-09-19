package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.repository.CategoryRepository;
import br.com.tiagoamp.timetracker.repository.TimeEntryEntity;
import br.com.tiagoamp.timetracker.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService {

    private TimeEntryRepository timeEntryRepo;
    private UserService userService;
    private CategoryService categoryService;
    private TimeEntryMapper timeMapper;

    @Autowired
    public TimeService(TimeEntryRepository timeEntryRepo, UserService userService, CategoryService categoryService, TimeEntryMapper timeMapper) {
        this.timeEntryRepo = timeEntryRepo;
        this.userService = userService;
        this.categoryService = categoryService;
        this.timeMapper = timeMapper;
    }


    public TimeEntry create(Long userId, TimeEntry timeEntry) {
        userService.findUserById(userId);
        categoryService.findCategories(userId);
        TimeEntryEntity timeEntryEntity = timeMapper.toEntity(timeEntry);
        timeEntryEntity = timeEntryRepo.save(timeEntryEntity);
        return timeMapper.toModel(timeEntryEntity);
    }

    public void delete(Long userId, Long timeId) {
        // usar novo metodo no DAO
    }

    public List<TimeEntry> findByCategory(Long categoryId) {
        return null;
    }
}
