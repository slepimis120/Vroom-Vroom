package ${class.typePackage};

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ${class.name}Service {

    private final ${class.name}Repository repository;

    public ${class.name}Service(${class.name}Repository repository) {
        this.repository = repository;
    }

    public List<${class.name}> findAll() {
        return repository.findAll();
    }

    public Optional<${class.name}> findById(Long id) {
        return repository.findById(id);
    }

    public ${class.name} save(${class.name} entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}