package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.error.ResourceNotFoundException;
import br.com.tiagoamp.timetracker.error.TimeTrackerOperationException;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    private TimeEntryRepository timeEntryRepo;
    private UserRepository userRepo;
    private CategoryRepository categoryRepo;
    private TimeEntryMapper timeMapper;

    @Autowired
    public TimeService(TimeEntryRepository timeEntryRepo, UserRepository userRepo, CategoryRepository categoryRepo, TimeEntryMapper timeMapper) {
        this.timeEntryRepo = timeEntryRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.timeMapper = timeMapper;
    }


    public TimeEntry create(TimeEntry timeEntry) {
        Long userId = timeEntry.getUser().getId();
        userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userId));
        categoryRepo.retrieveByUser(userId).stream()
                .filter(cat -> cat.getId().longValue() == timeEntry.getCategory().getId().longValue())
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Category id: " + timeEntry.getCategory().getId()));
        TimeEntryEntity timeEntryEntity = timeMapper.toEntity(timeEntry);
        timeEntryEntity = timeEntryRepo.save(timeEntryEntity);
        return timeMapper.toModel(timeEntryEntity);
    }

    public void delete(Long userId, Long timeId) {
        // usar novo metodo no DAO
    }

}
