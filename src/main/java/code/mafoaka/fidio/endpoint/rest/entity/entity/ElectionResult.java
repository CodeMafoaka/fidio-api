package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "election_result")
public class ElectionResult {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "election_id")
    private Election election;

    private Integer totalVote;

    private LocalDateTime updatedAt = LocalDateTime.now();
}
