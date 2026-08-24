package transport.transportmodel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class StationService {

    private final StationRepository repository;

    public StationService(StationRepository repository) {
        this.repository = repository;
    }

    public List<Station> findAll() {
        return repository.findAll();
    }

    public Optional<Station> findById(Long id) {
        return repository.findById(id);
    }

    public Station save(Station entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
	// <protected region name="custom">

    // </protected region>
}