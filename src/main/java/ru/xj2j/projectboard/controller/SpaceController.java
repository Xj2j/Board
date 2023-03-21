package ru.xj2j.projectboard.controller;

import org.springframework.web.bind.annotation.*;
import ru.xj2j.projectboard.UserNotFoundException;
import ru.xj2j.projectboard.model.Space;
import ru.xj2j.projectboard.repo.SpaceRepository;

import java.util.List;

@RestController
public class SpaceController {

    private final SpaceRepository repository;

    SpaceController(SpaceRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/spaces")
    List<Space> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/spaces")
    Space newSpace(@RequestBody Space newSpace) {
        return repository.save(newSpace);
    }

    // Single item
    @GetMapping("/spaces/{id}")
    Space one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));     ///!!!!!!!!
    }

    @PutMapping("/spaces/{id}")
    Space replaceSpace(@RequestBody Space newSpace, @PathVariable Long id) {

        return repository.findById(id)
                .map(space -> {
                    space.setName(newSpace.getName());
                    space.setDescription(newSpace.getDescription());
                    space.setPrivacy(newSpace.getPrivacy());
                    return repository.save(space);
                })
                .orElseGet(() -> {
                    newSpace.setId(id);
                    return repository.save(newSpace);
                });
    }

    @DeleteMapping("/spaces/{id}")
    void deleteSpace(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

