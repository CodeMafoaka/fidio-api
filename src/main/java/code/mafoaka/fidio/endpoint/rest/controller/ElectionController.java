package code.mafoaka.fidio.endpoint.rest.controller;

import code.mafoaka.fidio.endpoint.rest.api.ElectionsApi;
import code.mafoaka.fidio.endpoint.rest.model.BlindSignaturePublicKey;
import code.mafoaka.fidio.endpoint.rest.model.BlindSignatureRequest;
import code.mafoaka.fidio.endpoint.rest.model.BlindSignatureResponse;
import code.mafoaka.fidio.endpoint.rest.model.CreateElection;
import code.mafoaka.fidio.endpoint.rest.model.Election;
import code.mafoaka.fidio.endpoint.rest.model.ElectionCandidate;
import code.mafoaka.fidio.endpoint.rest.model.ElectionCandidateResult;
import code.mafoaka.fidio.endpoint.rest.model.ElectionResult;
import code.mafoaka.fidio.repository.entity.CandidateEntity;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.RsaKeyPairEntity;
import code.mafoaka.fidio.service.BlindSignatureService;
import code.mafoaka.fidio.service.CitizenService;
import code.mafoaka.fidio.service.ElectionService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
public class ElectionController implements ElectionsApi {
  private final ElectionService service;
  private final BlindSignatureService blindSignatureService;
  private final CitizenService citizenService;

  @Override
  public ResponseEntity<List<Election>> createElections(List<CreateElection> createElection) {
    List<ElectionEntity> entities =
        createElection.stream().map(this::toEntity).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            service.createElections(entities).stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<List<Election>> getElections() {
    return ResponseEntity.ok(
        service.getAllElections().stream().map(this::toDto).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<ElectionResult> getElectionResult(String electionId) {
    UUID uuid = UUID.fromString(electionId);
    var results = service.getResults(uuid);
    var total = service.getTotalVotes(uuid);

    ElectionResult dto = new ElectionResult();
    dto.setElectionId(electionId);
    dto.setTotalVote((int) total);
    dto.setCandidateResults(
        results.entrySet().stream()
            .map(
                e -> {
                  ElectionCandidateResult r = new ElectionCandidateResult();
                  r.setCandidateGid(e.getKey());
                  r.setVoteAmount(e.getValue().intValue());
                  return r;
                })
            .collect(Collectors.toList()));

    return ResponseEntity.ok(dto);
  }

  @Override
  public ResponseEntity<BlindSignaturePublicKey> getElectionBlindSignaturePublicKey(
      String electionId) {
    ElectionEntity election = service.getElectionById(UUID.fromString(electionId));
    RsaKeyPairEntity keyPair = blindSignatureService.getKeyPairForElection(election);

    BlindSignaturePublicKey dto = new BlindSignaturePublicKey();
    dto.setModulus(keyPair.getModulus());
    dto.setPublicExponent(keyPair.getPublicExponent());

    return ResponseEntity.ok(dto);
  }

  @Override
  public ResponseEntity<BlindSignatureResponse> signBlindedMessage(
      String electionId, BlindSignatureRequest blindSignatureRequest) {
    String gid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CitizenEntity citizen = citizenService.getByGid(gid);
    ElectionEntity election = service.getElectionById(UUID.fromString(electionId));

    String blindSignature =
        blindSignatureService.signBlindedMessage(
            election, citizen, blindSignatureRequest.getBlindedMessage());

    BlindSignatureResponse response = new BlindSignatureResponse();
    response.setBlindedSignature(blindSignature);

    return ResponseEntity.ok(response);
  }

  private ElectionEntity toEntity(CreateElection dto) {
    ElectionEntity entity =
        ElectionEntity.builder()
            .title(dto.getTitle())
            .startAt(dto.getStartAt().toInstant())
            .endAt(dto.getEndAt().toInstant())
            .build();
    if (dto.getCandidates() != null) {
      entity.setCandidates(
          dto.getCandidates().stream()
              .map(
                  c ->
                      CandidateEntity.builder()
                          .citizen(CitizenEntity.builder().gid(c.getGid()).build())
                          .description(c.getDescription())
                          .election(entity)
                          .build())
              .collect(Collectors.toList()));
    }
    return entity;
  }

  private Election toDto(ElectionEntity entity) {
    Election dto = new Election();
    dto.setId(entity.getId().toString());
    dto.setTitle(entity.getTitle());
    dto.setStartAt(OffsetDateTime.ofInstant(entity.getStartAt(), ZoneOffset.UTC));
    dto.setEndAt(OffsetDateTime.ofInstant(entity.getEndAt(), ZoneOffset.UTC));
    dto.setCreatedAt(OffsetDateTime.ofInstant(entity.getCreatedAt(), ZoneOffset.UTC));
    if (entity.getCandidates() != null) {
      dto.setCandidates(
          entity.getCandidates().stream()
              .map(
                  c -> {
                    ElectionCandidate ec = new ElectionCandidate();
                    ec.setGid(c.getCitizen().getGid());
                    ec.setDescription(c.getDescription());
                    return ec;
                  })
              .collect(Collectors.toList()));
    }
    return dto;
  }
}
