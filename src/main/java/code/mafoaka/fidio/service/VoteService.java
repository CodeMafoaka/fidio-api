package code.mafoaka.fidio.service;

import code.mafoaka.fidio.repository.CandidateRepository;
import code.mafoaka.fidio.repository.CitizenRepository;
import code.mafoaka.fidio.repository.ElectionRepository;
import code.mafoaka.fidio.repository.VoteRepository;
import code.mafoaka.fidio.repository.entity.CandidateEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.VoteEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
  private final VoteRepository voteRepository;
  private final ElectionRepository electionRepository;
  private final CandidateRepository candidateRepository;
  private final CitizenRepository citizenRepository;

  public void createVotes(List<VoteEntity> votes) {
    String gid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var voter = citizenRepository.findByGid(gid).orElseThrow();

    List<VoteEntity> toSave = new ArrayList<>();
    for (VoteEntity v : votes) {
      v.setVoter(voter);
      toSave.add(v);
    }
    voteRepository.saveAll(toSave);
  }

  public VoteEntity createVoteFromIds(UUID electionId, UUID candidateId) {
    ElectionEntity election = electionRepository.findById(electionId).orElseThrow();
    CandidateEntity candidate = candidateRepository.findById(candidateId).orElseThrow();
    String gid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var voter = citizenRepository.findByGid(gid).orElseThrow();

    return VoteEntity.builder().election(election).candidate(candidate).voter(voter).build();
  }
}
