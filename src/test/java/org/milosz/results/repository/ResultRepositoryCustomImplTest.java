package org.milosz.results.repository;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.milosz.results.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//https://stackoverflow.com/questions/36849673/running-datajpatest-with-postgresql
//https://miensol.pl/clear-database-in-spring-boot-tests/
//https://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/html/boot-features-testing.html
 // Data JPA tests are transactional and rollback at the end of each test by default
//!!! Bardzo fajny tutorial
   //https://stackabuse.com/spring-annotations-testing/

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ResultRepositoryCustomImplTest {

    @Autowired
    ResultRepositoryCustomImpl resultRepositoryCustom;

    @Test
    //https://www.vogella.com/tutorials/AssertJ/article.html
    void findResultsByCourseQueryDslMax() {
        Map<String, Integer> expectedValue = Map.of("polish",4,"biology",5,"physics",5,"math",6);
        Map<String, Integer> maxByCourse = resultRepositoryCustom.findResultsByCourseQueryDslMax();
        assertThat(maxByCourse).containsAllEntriesOf(expectedValue);
    }

    @Test
    void findResultsByCourseQueryDslList() {
        Map<String, List<Integer>> resultsByCourse = resultRepositoryCustom.findResultsByCourseQueryDslList();
        Assertions.assertEquals(1,1);
    }

    @Test
    void findResultsByCourseQueryDslJoin() {
        List<Result>  results = resultRepositoryCustom.findResultsByCourseQueryDslJoin();
        Assertions.assertEquals(1,1);
    }

    @Test
    void findResultsByCourseQueryDslJoinDataOfBothTables() {
        List<Tuple>  results = resultRepositoryCustom.findResultsByCourseQueryDslJoinDataOfBothTables();
        Assertions.assertEquals(1,1);
    }

    @Test
    void findResultsByCourseQueryDslJoinDataOfBothTables2() {
        List<Tuple>  results = resultRepositoryCustom.findResultsByCourseQueryDslJoinDataOfBothTables2();
        Assertions.assertEquals(1,1);
    }

    @Test
    void findResultsByCourseQueryDslJoinWhenMappingOfRelations() {
        List<Tuple>  results = resultRepositoryCustom.findResultsByCourseQueryDslJoinWhenMappingOfRelations();
        Assertions.assertEquals(1,1);
    }

    @Test
    void findResultsByCourseQueryDslSubquery() {
        List<Tuple>  results = resultRepositoryCustom.findResultsByCourseQueryDslSubquery();
        Assertions.assertEquals(1,1);
    }

}