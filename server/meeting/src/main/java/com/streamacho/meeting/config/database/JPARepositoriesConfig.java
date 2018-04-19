package com.streamacho.meeting.config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.streamacho.meeting.room.repository")
public class JPARepositoriesConfig {
}
