package com.example.firstshop.database;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor (staticName = "of")
@AllArgsConstructor
@Builder
@Entity
@Table (name="role")
public class Role implements GrantedAuthority {
    @Id
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }
}
