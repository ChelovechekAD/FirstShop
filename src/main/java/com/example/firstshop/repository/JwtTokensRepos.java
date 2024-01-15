package com.example.firstshop.repository;

import com.example.firstshop.database.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokensRepos extends CrudRepository<JwtToken, String> {

    JwtToken findByRefreshToken(String token);
}
