package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vote",
        uniqueConstraints = @UniqueConstraint(columnNames = {"election_id", "citizen_id"}))
public class Vote {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    private UUID candidateId;

    private LocalDateTime createdAt = LocalDateTime.now();
}
