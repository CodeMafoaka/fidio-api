package code.mafoaka.fidio.service;

import code.mafoaka.fidio.repository.CandidateRepository;
import code.mafoaka.fidio.repository.ElectionRepository;
import code.mafoaka.fidio.repository.VoteRepository;
import code.mafoaka.fidio.repository.entity.CandidateEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.VoteEntity;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {
  private final VoteRepository voteRepository;
  private final ElectionRepository electionRepository;
  private final CandidateRepository candidateRepository;
  private final BlindSignatureService blindSignatureService;

  public void createVotes(List<VoteEntity> votes) {
    for (VoteEntity v : votes) {
      if (!blindSignatureService.verifySignature(
          v.getElection(), v.getMessage(), v.getSignature())) {
        throw new IllegalArgumentException("Invalid blind signature for vote");
      }
      if (voteRepository.existsByElectionAndMessage(v.getElection(), v.getMessage())) {
        throw new IllegalArgumentException("Vote already exists for this message");
      }
    }
    voteRepository.saveAll(votes);
  }

  public VoteEntity createVoteFromIds(
      UUID electionId, UUID candidateId, String message, String signature) {
    ElectionEntity election = electionRepository.findById(electionId).orElseThrow();
    CandidateEntity candidate = candidateRepository.findById(candidateId).orElseThrow();

    return VoteEntity.builder()
        .election(election)
        .candidate(candidate)
        .message(message)
        .signature(signature)
        .build();
  }
}
