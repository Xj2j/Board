package ru.xj2j.board.userteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xj2j.board.userteamservice.DTO.UserDTO;
import ru.xj2j.board.userteamservice.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDto = userService.getCurrentUser();
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping(value = "/onboarded", produces = "application/json")
    public ResponseEntity<String> updateUserOnBoarded(@RequestParam("is_onboarded") boolean isOnboarded) {
        try {
            userService.updateUserOnBoarded(isOnboarded);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Updated successfully\"}");
        } catch (Exception e) {
            // log error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Something went wrong please try again later\"}");
        }
    }

    /*@GetMapping("/activity")
    public ResponseEntity<Page<IssueActivityDto>> getUserActivity(Pageable pageable) {
        try {
            Page<IssueActivityDto> resultPage = userService.getUserActivity(pageable);
            return ResponseEntity.ok(resultPage);
        } catch (Exception e) {
            // log error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }*/
}
