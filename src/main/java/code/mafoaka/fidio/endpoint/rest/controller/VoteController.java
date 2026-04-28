package code.mafoaka.fidio.endpoint.rest.controller;

import code.mafoaka.fidio.endpoint.rest.api.VotesApi;
import code.mafoaka.fidio.endpoint.rest.model.CreateVote;
import code.mafoaka.fidio.repository.entity.VoteEntity;
import code.mafoaka.fidio.security.BlindAuthenticationToken;
import code.mafoaka.fidio.service.VoteService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController implements VotesApi {
  private final VoteService service;

  @Override
  public ResponseEntity<Void> createVotes(List<CreateVote> createVote) {
    BlindAuthenticationToken auth =
        (BlindAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

    for (CreateVote v : createVote) {
      if (!v.getElectionId().equals(auth.getElectionId())) {
        throw new IllegalArgumentException(
            "Vote electionId does not match the electionId in the Blind Authorization token");
      }
    }

    List<VoteEntity> entities =
        createVote.stream()
            .map(
                v ->
                    service.createVoteFromIds(
                        UUID.fromString(v.getElectionId()),
                        UUID.fromString(v.getCandidateId()),
                        auth.getMessage(),
                        auth.getSignature()))
            .collect(Collectors.toList());
    service.createVotes(entities);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
