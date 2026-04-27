package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "election_candidate")
public class ElectionCandidate {

    @Id
    @GeneratedValue
    private UUID id;

    private String gid;

    private String description;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;
}
