package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.ElectionCandidateResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ElectionCandidateResultRepository extends JpaRepository<ElectionCandidateResult, UUID> {

    List<ElectionCandidateResult> findByElectionId(UUID electionId);
}
