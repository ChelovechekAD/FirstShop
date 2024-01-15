package com.example.firstshop.repository;

import com.example.firstshop.database.Bricks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BricksRepos extends JpaRepository<Bricks, Long> {
}
