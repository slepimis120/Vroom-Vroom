package ${class.typePackage};

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${class.name?uncap_first}")
public class ${class.name}Controller {

    private final ${class.name}Service service;

    public ${class.name}Controller(${class.name}Service service) {
        this.service = service;
    }

    @GetMapping
    public List<${class.name}> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<${class.name}> findById(@PathVariable Long id) {
        Optional<${class.name}> entity = service.findById(id);

        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ${class.name} save(@RequestBody ${class.name} entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    package ${class.typePackage};

import org.springframework.data.jpa.repository.JpaRepository;

public interface ${class.name}Repository extends JpaRepository<${class.name}, Long> {

    // <protected region name="custom">

    // </protected region>
}
}