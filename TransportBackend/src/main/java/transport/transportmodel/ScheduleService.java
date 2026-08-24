package transport.transportmodel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public List<Schedule> findAll() {
        return repository.findAll();
    }

    public Optional<Schedule> findById(Long id) {
        return repository.findById(id);
    }

    public Schedule save(Schedule entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
	// <protected region name="custom">

    // </protected region>
}