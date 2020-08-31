package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.TimeEntryRequestDTO;
import br.com.tiagoamp.timetracker.dto.TimeEntryResponseDTO;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("time")
public class TimeEntryController {

    @PostMapping
    public ResponseEntity<?> createTimeEntry(@RequestBody TimeEntryRequestDTO timeEntryReqDTO) {
        return ResponseEntity.ok(new TimeEntryResponseDTO());
    }

    @PutMapping("{timeId}")
    public ResponseEntity<?> updateTimeEntry(@PathVariable("timeId") String timeId, @RequestBody TimeEntry timeEntryReqDTO) {
        return ResponseEntity.ok(new TimeEntryResponseDTO());
    }

    @DeleteMapping("{timeId}")
    public ResponseEntity<?> removeTimeEntry(@PathVariable("timeId") String timeId) {
        return ResponseEntity.ok(new TimeEntryResponseDTO());
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<TimeEntryResponseDTO>> getTimeEntriesByUsers(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("{timeId}")
    public ResponseEntity<?> getUserById(@PathVariable("timeId") String timeId) {
        return ResponseEntity.ok(new TimeEntryResponseDTO());
    }

}
