package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> map = new HashMap<>();
    private AtomicLong sequence = new AtomicLong();

    public TimeEntry create(TimeEntry timeEntry) {
        Long id = sequence.incrementAndGet();
        timeEntry.setId(id);
        map.put(id, timeEntry);
        return timeEntry;
    }

    public TimeEntry find(Long id) {
        return map.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(map.values());
    }

    public TimeEntry update(Long id, TimeEntry newTimeEntry) {
        if (map.containsKey(id)) {
            newTimeEntry.setId(id);
            map.put(id, newTimeEntry);

            return newTimeEntry;
        }
        else {
            return null;
        }
    }

    public void delete(Long id) {
        map.remove(id);
    }
}
