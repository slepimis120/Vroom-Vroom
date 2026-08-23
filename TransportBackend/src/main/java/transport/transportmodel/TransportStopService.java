package transport.transportmodel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TransportStopService {

    private final TransportStopRepository repository;

    public TransportStopService(TransportStopRepository repository) {
        this.repository = repository;
    }

    public List<TransportStop> findAll() {
        return repository.findAll();
    }

    public Optional<TransportStop> findById(Long id) {
        return repository.findById(id);
    }

    public TransportStop save(TransportStop entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
	// <protected region name="custom">

    // </protected region>
}