package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "election")
public class Election {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private LocalDateTime createdAt = LocalDateTime.now();
}