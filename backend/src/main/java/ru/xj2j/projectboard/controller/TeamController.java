package ru.xj2j.projectboard.controller;

import org.springframework.web.bind.annotation.*;
import ru.xj2j.projectboard.UserNotFoundException;
import ru.xj2j.projectboard.model.Space;
import ru.xj2j.projectboard.model.Team;
import ru.xj2j.projectboard.repo.SpaceRepository;
import ru.xj2j.projectboard.repo.TeamRepository;

import java.util.List;

@RestController
public class TeamController {

    private final TeamRepository repository;

    TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/teams")
    List<Team> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/teams")
    Team newTeam(@RequestBody Team newTeam) {
        return repository.save(newTeam);
    }

    // Single item
    @GetMapping("/teams/{id}")
    Team one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));     ///!!!!!!!!
    }

    @PutMapping("/teams/{id}")
    Team replaceTeam(@RequestBody Team newTeam, @PathVariable Long id) {

        return repository.findById(id)
                .map(team -> {
                    team.setName(newTeam.getName());
                    team.setDescription(newTeam.getDescription());
                    team.setColor(newTeam.getColor());
                    team.setMembers(newTeam.getMembers());
                    team.setProjects(newTeam.getProjects());
                    return repository.save(team);
                })
                .orElseGet(() -> {
                    newTeam.setId(id);
                    return repository.save(newTeam);
                });
    }

    @DeleteMapping("/teams/{id}")
    void deleteTeam(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
