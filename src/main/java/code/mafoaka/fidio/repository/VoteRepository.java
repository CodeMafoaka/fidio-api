package code.mafoaka.fidio.repository;

import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.VoteEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
  List<VoteEntity> findByElection(ElectionEntity election);

  long countByElection(ElectionEntity election);

  @Query(
      "select v.candidate.citizen.gid as gid, count(v) as count from VoteEntity v where v.election"
          + " = :election group by v.candidate.citizen.gid")
  List<Object[]> countVotesByCandidateForElection(@Param("election") ElectionEntity election);

  boolean existsByElectionAndMessage(ElectionEntity election, String message);
}
