package org.milosz.results.service;

import org.milosz.results.model.Result;
import org.milosz.results.model.ResultStatisticResource;

import java.util.List;
import java.util.Map;

public interface ResultService {
    List<Result> getResults();

    List<Result> getResultsByForename(String forename);

    Result saveResult(Result result);

    List<Result> findResultsByCustomPredicates(String forename, String course);

    List<Result> findResultsByCustomPredicatesQueryDsl(String forename, String course);

    List<Result> findByExample(Result result);

    List<ResultStatisticResource> findResultsByCourseQueryDsl1();

    Map<String, Long> findResultsByCourseQueryDsl2();

    Map<String, Integer> findResultsByCourseQueryDslMax();
}
