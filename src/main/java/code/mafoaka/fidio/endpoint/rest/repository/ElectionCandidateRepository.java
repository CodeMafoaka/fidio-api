package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate, UUID> {

    List<ElectionCandidate> findByElectionId(UUID electionId);
}
