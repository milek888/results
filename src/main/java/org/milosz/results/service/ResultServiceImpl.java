package org.milosz.results.service;

import lombok.AllArgsConstructor;
import org.milosz.results.model.Result;
import org.milosz.results.model.ResultStatisticResource;
import org.milosz.results.repository.ResultRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Override
    public List<Result> getResults() {
        return resultRepository.findAll();
    }

    @Override
    public List<Result> getResultsByForename(String forename) {
        return resultRepository.findByForename(forename);
    }

    @Override
    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public List<Result> findResultsByCustomPredicates(String forename, String course) {
        return resultRepository.findResultsByCustomPredicates(forename, course);
    }

    @Override
    public List<Result> findResultsByCustomPredicatesQueryDsl(String forename, String course) {
        return resultRepository.findResultsByCustomPredicates(forename, course);
    }

    @Override
    public List<Result> findByExample(Result result) {
        return resultRepository.findAll(Example.of(result));
    }

    @Override
    public List<ResultStatisticResource> findResultsByCourseQueryDsl1() {
        return resultRepository.findResultsByCourseQueryDsl1();
    }
    @Override
    public Map<String, Long> findResultsByCourseQueryDsl2() {
        return resultRepository.findResultsByCourseQueryDsl2();
    }

    @Override
    public Map<String, Integer> findResultsByCourseQueryDslMax() {
        return resultRepository.findResultsByCourseQueryDslMax();
    }
}
