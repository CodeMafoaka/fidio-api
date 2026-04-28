package code.mafoaka.fidio.service;

import code.mafoaka.fidio.repository.CandidateRepository;
import code.mafoaka.fidio.repository.CitizenRepository;
import code.mafoaka.fidio.repository.ElectionRepository;
import code.mafoaka.fidio.repository.VoteRepository;
import code.mafoaka.fidio.repository.entity.CandidateEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ElectionService {
  private final ElectionRepository electionRepository;
  private final CandidateRepository candidateRepository;
  private final VoteRepository voteRepository;
  private final CitizenRepository citizenRepository;
  private final BlindSignatureService blindSignatureService;

  @Transactional
  public List<ElectionEntity> createElections(List<ElectionEntity> elections) {
    for (ElectionEntity election : elections) {
      List<CandidateEntity> candidates = election.getCandidates();
      election.setCandidates(null);
      ElectionEntity savedElection = electionRepository.save(election);
      blindSignatureService.generateKeyPairForElection(savedElection);
      if (candidates != null) {
        candidates.forEach(
            c -> {
              c.setElection(savedElection);
              c.setCitizen(
                  citizenRepository
                      .findByGid(c.getCitizen().getGid())
                      .orElseThrow(
                          () ->
                              new IllegalArgumentException(
                                  "Citizen with gid " + c.getCitizen().getGid() + " not found")));
            });
        candidateRepository.saveAll(candidates);
        savedElection.setCandidates(candidates);
      }
    }
    return elections;
  }

  public ElectionEntity getElectionById(UUID id) {
    return electionRepository.findById(id).orElseThrow();
  }

  public Map<String, Long> getResults(UUID electionId) {
    ElectionEntity election = getElectionById(electionId);
    List<Object[]> counts = voteRepository.countVotesByCandidateForElection(election);
    return counts.stream().collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));
  }

  public long getTotalVotes(UUID electionId) {
    ElectionEntity election = getElectionById(electionId);
    return voteRepository.countByElection(election);
  }
}
