package org.milosz.results.repository;

import org.milosz.results.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>, ResultRepositoryCustom {
    List<Result> findByForename(String forename);
}
