package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "election_candidate_result")
public class ElectionCandidateResult {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    private String candidateGid;

    private Integer voteAmount;
}
