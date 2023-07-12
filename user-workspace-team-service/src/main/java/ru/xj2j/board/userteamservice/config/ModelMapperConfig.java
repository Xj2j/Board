package ru.xj2j.board.userteamservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Настройка сопоставления полей с разными именами
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Стратегия сопоставления по умолчанию
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Настройка маппинга для типов User и LocalDateTime, если требуется

        return modelMapper;
    }
}