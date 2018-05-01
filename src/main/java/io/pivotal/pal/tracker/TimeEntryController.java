package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdEntry = repository.create(timeEntryToCreate);

        URI uri = UriComponentsBuilder.newInstance().path("/time-entries/{id}").buildAndExpand(createdEntry.getId()).toUri();
        return ResponseEntity.created(uri).body(createdEntry);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long id) {
        TimeEntry entry = repository.find(id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entry);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries = repository.list();
        return ResponseEntity.ok(timeEntries);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TimeEntry entry) {
        TimeEntry updatedEntry = repository.update(id, entry);
        if (updatedEntry == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
