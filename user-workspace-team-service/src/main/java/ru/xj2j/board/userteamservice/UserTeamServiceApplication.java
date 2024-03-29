package ru.xj2j.board.userteamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserTeamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserTeamServiceApplication.class, args);
    }

}
