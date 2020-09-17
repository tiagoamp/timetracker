package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.TimeEntryRequestDTO;
import br.com.tiagoamp.timetracker.dto.TimeEntryResponseDTO;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("time")
public class TimeEntryController {

    private TimeService timeService;
    private TimeEntryMapper timeMapper;

    @Autowired
    public TimeEntryController(TimeService timeService, TimeEntryMapper timeMapper) {
        this.timeService = timeService;
        this.timeMapper = timeMapper;
    }


    @PostMapping
    public ResponseEntity<?> createTimeEntry(@Valid @RequestBody TimeEntryRequestDTO timeEntryReqDTO) {
        var timeEntry = timeMapper.toModel(timeEntryReqDTO);
        timeEntry = timeService.create(timeEntry);
        var timeDTO = timeMapper.toResponseDTO(timeEntry);
        return ResponseEntity.created(URI.create(timeDTO.getId().toString())).body(timeDTO);
    }

    @PutMapping("{timeId}")
    public ResponseEntity<?> updateTimeEntry(@PathVariable("timeId") String timeId, @RequestBody TimeEntry timeEntryReqDTO) {
        return ResponseEntity.ok(new TimeEntryResponseDTO());
    }

    @DeleteMapping("{timeId}")
    public ResponseEntity removeTimeEntry(@PathVariable("timeId") String timeId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<TimeEntryResponseDTO>> getTimeEntriesByUsers(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("{timeId}")
    public ResponseEntity<?> getTimeEntryById(@PathVariable("timeId") Long timeId) {
        return ResponseEntity.ok(new TimeEntryResponseDTO());
    }

}
