package org.milosz.results.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.SubqueryExpression;
import org.milosz.results.model.QResult;
import org.milosz.results.model.QRevard;
import org.milosz.results.model.Result;
import org.milosz.results.model.ResultStatisticResource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.milosz.results.model.QRevard.revard;

//Query by example https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example
@Repository
@AllArgsConstructor
public class ResultRepositoryCustomImpl implements ResultRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<Result> findResultsByCustomPredicates(String forename, String course) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Result> criteriaQuery = criteriaBuilder.createQuery(Result.class);
        Root<Result> from = criteriaQuery.from(Result.class);

        List<Predicate> predicates = new ArrayList<>();
        if (forename != null) {
            Predicate predicateName = criteriaBuilder.equal(from.get("forename"), forename);
            predicates.add(predicateName);
        }
        if (course != null) {
            Predicate predicateJob = criteriaBuilder.equal(from.get("course"), course);
            predicates.add(predicateJob);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

//----------------------------------------------------------QueryDsl aggregations----------------------------------------------------

    public List<Result> findResultsByCustomPredicatesQueryDsl(String forename, String course) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QResult result = QResult.result1;

        return queryFactory.selectFrom(result)
                .where(result.forename.eq(forename), result.course.eq(course))
                .fetch();
    }

    /*
        Projekcje w QueryDsl
        https://stackoverflow.com/questions/12427665/multiple-columns-in-querydsl
        http://www.querydsl.com/static/querydsl/4.1.4/reference/html_single/#d0e2187
        https://www.programmersought.com/article/6859681415/
    */
    //Pierwszy sposob agregacji
    public List<ResultStatisticResource> findResultsByCourseQueryDsl1() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QResult result = QResult.result1;

        List<ResultStatisticResource> results = queryFactory.select(result.course, result.count())
                .from(result)
                .groupBy(result.course)
                .orderBy(result.course.asc())
                .fetch()
                .stream()
                .map(tuple -> new ResultStatisticResource(tuple.get(result.course), tuple.get(1, Long.class)))
                .collect(Collectors.toList());

        return results;
    }

    //Drugi sposob agregacji
    public Map<String, Long> findResultsByCourseQueryDsl2() {

        QResult result = QResult.result1;

        Map<String, Long> countByCourse = new JPAQueryFactory(entityManager)
                .from(result)
                .groupBy(result.course)
                .transform(GroupBy.groupBy(result.course).as(result.id.count()));

        return countByCourse;
    }

    //Max
    public Map<String, Integer> findResultsByCourseQueryDslMax() {

        QResult result = QResult.result1;

        Map<String, Integer> maxByCourse = new JPAQueryFactory(entityManager)
                .from(result)
                .transform(GroupBy.groupBy(result.course).as(GroupBy.max(result.result)));
                /*.transform(GroupBy.groupBy(result.course).as(GroupBy.sum(result.result)));<----- działa*/
                //TODO problem with avg

        return maxByCourse;
    }

    //List
    public Map<String, List<Integer>> findResultsByCourseQueryDslList() {
        QResult result = QResult.result1;

        Map<String, List<Integer>> resultsByCourse = new JPAQueryFactory(entityManager)
                .from(result)
                .transform(GroupBy.groupBy(result.course).as(GroupBy.list(result.result)));

        return resultsByCourse;
    }

    //----------------------------------------------------------QueryDsl joins----------------------------------------------------

    /*
       Na koncu przyklad zlaczenia jak nie mamy pomapowanych relacji
      https://stackoverflow.com/questions/47701172/how-to-join-multiple-querydsl-tables/47702568
    JPAQueryFactory(entityManager).from(qSampleBO)
    .innerJoin(qEntity1).on(qEntity1.id.eq(qSampleBO.address.id))
            .innerJoin(qEntity2).on(qEntity2.id.eq(qSampleBO.secondary_address.id))*/

    public  List<Result> findResultsByCourseQueryDslJoin() {
        QResult result = QResult.result1;
        QRevard revard = QRevard.revard;

        List<Result> results = new JPAQueryFactory(entityManager)
                .selectFrom(result)//musi byc selectFrom select nie przechodzi
                .innerJoin(revard).on(revard.result_id.eq(result.id))
                .fetch();

        return results;
    }

/*  Mozna stosowac jak mamy pomapowane relacjie
     https://stackoverflow.com/questions/47701172/how-to-join-multiple-querydsl-tables/47702568*/
    public  List<Tuple> findResultsByCourseQueryDslJoinWhenMappingOfRelations() {
        QResult result = QResult.result1;
        QRevard revard = QRevard.revard;

        List<Tuple> results = new JPAQueryFactory(entityManager)
                .select(result.id, result.course,result.forename, revard.id, revard.name, revard.amount, revard.result_id)
                .from(result)
                .innerJoin(result.revard, revard)
                .fetch();

        return results;
    }

    public  List<Tuple> findResultsByCourseQueryDslJoinDataOfBothTables() {
        QResult result = QResult.result1;
        QRevard revard = QRevard.revard;

        List<Tuple> results = new JPAQueryFactory(entityManager)
                .select(result.id, result.course,result.forename, revard.id, revard.name, revard.amount, revard.result_id)
                .from(result)
                .innerJoin(revard).on(revard.result_id.eq(result.id))
                .fetch();

        return results;
    }

    public  List<Tuple> findResultsByCourseQueryDslJoinDataOfBothTables2() {
        QResult result = QResult.result1;
        QRevard revard = QRevard.revard;

        List<Tuple> results = new JPAQueryFactory(entityManager)
                .select(result.result, revard.amount)
                .from(result)
                .innerJoin(revard).on(revard.result_id.eq(result.id))
                .fetch();

        return results;
    }


//----------------------------------------------------------QueryDsl subqueries----------------------------------------------------

    public  List<Tuple> findResultsByCourseQueryDslSubquery() {
        QResult result = QResult.result1;
        QRevard revard = QRevard.revard;

        List<Tuple> results = new JPAQueryFactory(entityManager)
                .select(result.id, result.course,result.forename, revard.id, revard.name, revard.amount, revard.result_id)
                .from(result)
                .where(result.id.in(JPAExpressions
                        .select(revard.result_id)
                        .from(revard))
                ).fetch();

        return results;
    }

//----------------------------------------------------------------------------------------------------------------------



}
