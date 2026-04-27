package code.mafoaka.fidio.endpoint.rest.entity.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "citizen")
public class Citizen {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String gid;

    private String password;
}
