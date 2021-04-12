package org.milosz.results.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultResource {
    private Integer id;
    private String forename;
    private String course;
    private Integer result;
    private String description;
}
