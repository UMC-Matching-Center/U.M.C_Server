package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Branch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch , Long> {
    Optional<Branch> findByName(String name);

    List<Branch> findAllByGeneration(int Generation);
}
