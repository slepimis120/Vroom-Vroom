package transport.transportmodel;

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
@RequestMapping("/transportStop")
public class TransportStopController {

    private final TransportStopService service;

    public TransportStopController(TransportStopService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransportStop> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportStop> findById(@PathVariable Long id) {
        Optional<TransportStop> entity = service.findById(id);

        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public TransportStop save(@RequestBody TransportStop entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // <protected region name="custom">
    // </protected region>
}