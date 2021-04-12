package org.milosz.results.repository;

import org.milosz.results.model.Result;
import org.milosz.results.model.ResultStatisticResource;

import java.util.List;
import java.util.Map;

public interface ResultRepositoryCustom {
    List<Result> findResultsByCustomPredicates(String forename, String course);
    List<Result> findResultsByCustomPredicatesQueryDsl(String forename, String course);
    List<ResultStatisticResource> findResultsByCourseQueryDsl1();
    Map<String, Long> findResultsByCourseQueryDsl2();
    Map<String, Integer> findResultsByCourseQueryDslMax();

}
