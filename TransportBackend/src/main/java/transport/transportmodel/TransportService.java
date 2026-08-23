package transport.transportmodel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TransportService {

    private final TransportRepository repository;

    public TransportService(TransportRepository repository) {
        this.repository = repository;
    }

    public List<Transport> findAll() {
        return repository.findAll();
    }

    public Optional<Transport> findById(Long id) {
        return repository.findById(id);
    }

    public Transport save(Transport entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
	// <protected region name="custom">

    // </protected region>
}