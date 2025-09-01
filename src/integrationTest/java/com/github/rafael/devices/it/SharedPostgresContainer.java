package com.github.rafael.devices.it;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class SharedPostgresContainer {

    @Container
    public static final PostgreSQLContainer<?> INSTANCE =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("devices")
                    .withUsername("devices")
                    .withPassword("devices")
                    .waitingFor(Wait.forListeningPort());

    static {
        INSTANCE.start();
    }
}

