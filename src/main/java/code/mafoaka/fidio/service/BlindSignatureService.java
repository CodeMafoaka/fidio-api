package code.mafoaka.fidio.service;

import code.mafoaka.fidio.repository.BlindSignatureRequestRepository;
import code.mafoaka.fidio.repository.RsaKeyPairRepository;
import code.mafoaka.fidio.repository.entity.BlindSignatureRequestEntity;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.RsaKeyPairEntity;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlindSignatureService {
  private final RsaKeyPairRepository rsaKeyPairRepository;
  private final BlindSignatureRequestRepository blindSignatureRequestRepository;

  @Transactional
  public RsaKeyPairEntity generateKeyPairForElection(ElectionEntity election) {
    try {
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
      keyGen.initialize(2048);
      KeyPair pair = keyGen.generateKeyPair();
      RSAPublicKey pub = (RSAPublicKey) pair.getPublic();
      RSAPrivateKey priv = (RSAPrivateKey) pair.getPrivate();

      RsaKeyPairEntity keyPairEntity =
          RsaKeyPairEntity.builder()
              .election(election)
              .modulus(pub.getModulus().toString())
              .publicExponent(pub.getPublicExponent().toString())
              .privateExponent(priv.getPrivateExponent().toString())
              .build();

      return rsaKeyPairRepository.save(keyPairEntity);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("RSA algorithm not found", e);
    }
  }

  public RsaKeyPairEntity getKeyPairForElection(ElectionEntity election) {
    return rsaKeyPairRepository
        .findByElection(election)
        .orElseThrow(() -> new RuntimeException("RSA Key pair not found for election"));
  }

  @Transactional
  public String signBlindedMessage(
      ElectionEntity election, CitizenEntity citizen, String blindedMessage) {
    if (blindSignatureRequestRepository.findByElectionAndCitizen(election, citizen).isPresent()) {
      throw new IllegalArgumentException("Citizen already requested a signature for this election");
    }

    RsaKeyPairEntity keyPair = getKeyPairForElection(election);
    BigInteger mPrime = new BigInteger(blindedMessage);
    BigInteger d = new BigInteger(keyPair.getPrivateExponent());
    BigInteger n = new BigInteger(keyPair.getModulus());

    BigInteger sPrime = mPrime.modPow(d, n);

    blindSignatureRequestRepository.save(
        BlindSignatureRequestEntity.builder().election(election).citizen(citizen).build());

    return sPrime.toString();
  }

  public boolean verifySignature(ElectionEntity election, String message, String signature) {
    RsaKeyPairEntity keyPair = getKeyPairForElection(election);
    BigInteger m = new BigInteger(message);
    BigInteger s = new BigInteger(signature);
    BigInteger e = new BigInteger(keyPair.getPublicExponent());
    BigInteger n = new BigInteger(keyPair.getModulus());

    return s.modPow(e, n).equals(m);
  }
}
