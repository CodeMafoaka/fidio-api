package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "biometric_data")
public class BiometricData {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    private String type;

    @ElementCollection
    private List<Float> embedding;

    private LocalDateTime createdAt = LocalDateTime.now();
}
