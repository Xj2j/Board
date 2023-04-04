package ru.xj2j.projectboard.controller;

import org.springframework.web.bind.annotation.*;
import ru.xj2j.projectboard.UserNotFoundException;
import ru.xj2j.projectboard.model.User;
import ru.xj2j.projectboard.repo.UserRepository;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newEmployee(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User replaceEmployee(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    user.setSurname(newUser.getSurname());
                    user.setRole(newUser.getRole());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setPositions(newUser.getPositions());
                    user.setTeams(newUser.getTeams());
                    user.setProjects(newUser.getProjects());
                    user.setTasks(newUser.getTasks());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}