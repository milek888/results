package org.milosz.results.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.milosz.results.model.Result;
import org.milosz.results.model.ResultResource;
import org.milosz.results.model.ResultStatisticResource;
import org.milosz.results.service.ResultService;
import org.milosz.results.util.ResultMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/results")
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final ResultMapper resultMapper;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<ResultResource>> getResults() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultMapper.toResultResources(resultService.getResults()));
    }

    @GetMapping("/byFriend/{forename}")
    public ResponseEntity<List<ResultResource>> getResultsByFriend(@PathVariable String forename) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultMapper.toResultResources(resultService.getResultsByForename(forename)));
    }

    @GetMapping("/byCustomPredicates")
    public ResponseEntity<List<ResultResource>> getResultsByCustomPredicates(@RequestParam String forename, @RequestParam String course) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultMapper.toResultResources(resultService.findResultsByCustomPredicates(forename, course)));
    }

    //https://lankydan.dev/2017/03/11/passing-data-transfer-objects-with-get-in-spring-boot <-------------- rozne sposoby przekazywania objektow w parametrach geta
    @GetMapping("/byExample")//TODO test it
       public ResponseEntity<List<ResultResource>> getResultsByExample(ResultResource resultResource) {
        Result result = resultMapper.toResult(resultResource);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultMapper.toResultResources(resultService.findByExample(result)));
    }

    //https://lankydan.dev/2017/03/11/passing-data-transfer-objects-with-get-in-spring-boot <-------------- rozne sposoby przekazywania objektow w parametrach geta
    @GetMapping("/byExampleJson")//TODO test it
        public ResponseEntity<List<ResultResource>> getResultsByExampleJson(@RequestParam String resultResourceParam) throws JsonProcessingException {

        ResultResource resultResource = objectMapper.readValue(resultResourceParam, ResultResource.class);
        Result result = resultMapper.toResult(resultResource);

        return ResponseEntity.status(HttpStatus.OK)
                .body(resultMapper.toResultResources(resultService.findByExample(result)));
    }

    //------------------------------------------------------------QueryDsl-----------------------------------------------

    //https://www.baeldung.com/querydsl-with-jpa-tutorial
    //https://www.baeldung.com/intro-to-querydsl
    @GetMapping("/byCustomPredicatesQueryDsl")
    public ResponseEntity<List<ResultResource>> getResultsByCustomPredicatesQueryDsl(@RequestParam String forename, @RequestParam String course) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultMapper.toResultResources(resultService.findResultsByCustomPredicatesQueryDsl(forename, course)));
    }

    @GetMapping("/findResultsByCourseQueryDsl1")
    public ResponseEntity<List<ResultStatisticResource>> getResultsByCourseQueryDsl1() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultService.findResultsByCourseQueryDsl1());
    }

    //https://www.baeldung.com/querydsl-with-jpa-tutorial
    //https://www.baeldung.com/intro-to-querydsl
    @GetMapping("/findResultsByCourseQueryDsl2")
    public ResponseEntity<Map<String, Long>> getResultsByCourseQueryDsl2() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultService.findResultsByCourseQueryDsl2());
    }

    //https://www.baeldung.com/querydsl-with-jpa-tutorial
    //https://www.baeldung.com/intro-to-querydsl
    @GetMapping("/findResultsByCourseQueryDslMax")
    public ResponseEntity<Map<String, Integer>> getResultsByCourseQueryDslMax() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultService.findResultsByCourseQueryDslMax());
    }

    //-----------------------------------------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<ResultResource> saveResult(@RequestBody ResultResource resultResource) {
        Result result = resultMapper.toResult(resultResource);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resultMapper.toResultResource(resultService.saveResult(result)));
    }

}
