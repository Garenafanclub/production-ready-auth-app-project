package com.example.auth_app_practice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    private boolean enabled = true;

    @Column(updatable = false) // Protect creation time from changing
    private Instant createdAt;

    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    Provider provider = Provider.LOCAL;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    Set<Roles> set = new HashSet<>();

                             // Runs before the object is inserted into the database for the first time
    @PrePersist
    protected void createdAt()
    {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();  // Set update time too on creation
    }
                             // Runs before any update is saved to the database
    @PreUpdate
    protected void updatedAt()
    {
        this.updatedAt = Instant.now();
    }
}
