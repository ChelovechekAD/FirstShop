package com.example.firstshop.repository;

import com.example.firstshop.database.User;
import com.example.firstshop.exception.ListExceptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);

    Boolean deleteUserByEmail(String email);

    default java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getUserRolesByEmail(String email){
        if (!this.existsUserByEmail(email)){
            throw new ListExceptions.UserNotFoundException();
        }
        User user = findByEmail(email);
        return user.getAuthorities();

    }
    Optional<User> findUserByEmail(String email);
    User findByEmail(String email);
}
