package org.milosz.results.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResultStatisticResource {

    public ResultStatisticResource() {
    }

    @QueryProjection
    public ResultStatisticResource(String course, long count) {
        this.course = course;
        this.count = count;
    }

    private String course;
    private long count;
}
