package com.github.sdan20.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Run(
        @Id
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer miles,
        Location location,
        @Version
        Integer version
    ) {

    public Run {
        if (!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Completed On must be after Started On");
        }
    }
}
