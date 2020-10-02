package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.repository.TimeEntryEntity;
import br.com.tiagoamp.timetracker.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public TimeEntry update(Long userId, TimeEntry timeEntry) {
        var entityFromDB = findTimeEntryEntityIfExists(userId, timeEntry.getId());
        TimeEntryEntity timeEntryEntity = timeMapper.toEntity(timeEntry);
        timeEntryEntity.setCategoryEntity(entityFromDB.getCategory());
        timeEntryEntity = timeEntryRepo.save(timeEntryEntity);
        return timeMapper.toModel(timeEntryEntity);
    }

    public void delete(Long userId, Long timeId) {
        var timeEntity = findTimeEntryEntityIfExists(userId, timeId);
        timeEntryRepo.delete(timeEntity);
    }

    public List<TimeEntry> findTimeEntriesOfUser(Long userId) {
        var categories = categoryService.findCategories(userId);
        return categories.stream()
                .map(cat -> timeEntryRepo.retrieveByCategory(cat.getId()))
                .flatMap(l -> l.stream())
                .map(timeMapper::toModel)
                .collect(toList());
    }

    public TimeEntry findTimeEntryById(Long userId, Long timeId) {
        var timeEntity = findTimeEntryEntityIfExists(userId, timeId);
        return timeMapper.toModel(timeEntity);
    }


    private TimeEntryEntity findTimeEntryEntityIfExists(Long userId, Long timeId) {
        userService.findUserById(userId);
        var timeEntity = timeEntryRepo.findById(timeId)
                .orElseThrow(() -> new ResourceNotFoundException("Time entry id: " + timeId));
        boolean isEntryOfThisUser = timeEntity.getCategory().getUser().getId().longValue() == userId;
        if (!isEntryOfThisUser) throw new ResourceNotFoundException("Time entry id: " + timeId);
        return timeEntity;
    }

}
