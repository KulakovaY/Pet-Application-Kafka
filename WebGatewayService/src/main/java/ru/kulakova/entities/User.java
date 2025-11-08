package ru.kulakova.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.kulakova.entities.enums.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(name = "Password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserRoles", joinColumns = @JoinColumn(name = "UserId"))
    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
}
