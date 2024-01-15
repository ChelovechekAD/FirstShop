package com.example.firstshop.repository;

import com.example.firstshop.database.Timber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimberRepository extends JpaRepository<Timber, Long> {
}
