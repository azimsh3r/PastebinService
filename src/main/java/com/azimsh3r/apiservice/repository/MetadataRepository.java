package com.azimsh3r.apiservice.repository;

import com.azimsh3r.apiservice.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
    Optional<Metadata> findByHash(String hash);
}
