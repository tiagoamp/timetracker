package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.TimeEntryRequestDTO;
import br.com.tiagoamp.timetracker.dto.TimeEntryResponseDTO;
import br.com.tiagoamp.timetracker.mapper.TimeEntryMapper;
import br.com.tiagoamp.timetracker.security.AuthorizationRules;
import br.com.tiagoamp.timetracker.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("user/{userId}/time")
public class TimeEntryController {

    private TimeService timeService;
    private TimeEntryMapper timeMapper;
    private AuthorizationRules auth;

    @Autowired
    public TimeEntryController(TimeService timeService, TimeEntryMapper timeMapper, AuthorizationRules authorizationRules) {
        this.timeService = timeService;
        this.timeMapper = timeMapper;
        this.auth = authorizationRules;
    }


    @PostMapping
    public ResponseEntity<?> createTimeEntry(@PathVariable("userId") Long userId, @Valid @RequestBody TimeEntryRequestDTO timeEntryReqDTO) {
        auth.authorizeOnlyIfRequesterUserIsTheAuthenticatedUser(userId);
        var timeEntry = timeMapper.toModel(timeEntryReqDTO);
        timeEntry = timeService.create(userId, timeEntry);
        var timeDTO = timeMapper.toResponseDTO(timeEntry);
        timeDTO.setUserId(userId);
        return ResponseEntity.created(URI.create(timeDTO.getId().toString())).body(timeDTO);
    }

    @PutMapping("{timeId}")
    public ResponseEntity<?> updateTimeEntry(@PathVariable("userId") Long userId, @PathVariable("timeId") Long timeId, @RequestBody TimeEntryRequestDTO timeEntryReqDTO) {
        auth.authorizeOnlyIfRequesterUserIsTheAuthenticatedUser(userId);
        var timeEntry = timeMapper.toModel(timeEntryReqDTO);
        timeEntry.setId(timeId);
        timeEntry = timeService.update(userId, timeEntry);
        var timeEntryRespDTO = timeMapper.toResponseDTO(timeEntry);
        timeEntryRespDTO.setUserId(userId);
        return ResponseEntity.ok(timeEntryRespDTO);
    }

    @DeleteMapping("{timeId}")
    public ResponseEntity removeTimeEntry(@PathVariable("userId") Long userId, @PathVariable("timeId") Long timeId) {
        auth.authorizeOnlyIfRequesterUserIsTheAuthenticatedUser(userId);
        timeService.delete(userId, timeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntryResponseDTO>> getTimeEntriesByUsers(@PathVariable("userId") Long userId) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        var timeEntries = timeService.findTimeEntriesOfUser(userId);
        var dtos = timeEntries.stream()
                .map(timeMapper::toResponseDTO)
                .map(dto -> {
                    dto.setUserId(userId);
                    return dto;
                }).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{timeId}")
    public ResponseEntity<TimeEntryResponseDTO> getTimeEntryById(@PathVariable("userId") Long userId, @PathVariable("timeId") Long timeId) {
        auth.authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(userId);
        var timeEntry = timeService.findTimeEntryById(userId, timeId);
        var responseDTO = timeMapper.toResponseDTO(timeEntry);
        responseDTO.setUserId(userId);
        return ResponseEntity.ok(responseDTO);
    }

}
