package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {

    List<Vote> findByElectionId(UUID electionId);

    boolean existsByElectionIdAndCitizenId(UUID electionId, UUID citizenId);
}
