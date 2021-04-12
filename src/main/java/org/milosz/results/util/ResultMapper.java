package org.milosz.results.util;

import org.mapstruct.Mapper;
import org.milosz.results.model.Result;
import org.milosz.results.model.ResultResource;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResultMapper {
    ResultResource toResultResource(Result result);
    Result toResult(ResultResource resultResource);
    List<ResultResource> toResultResources(List<Result> result);
    List<Result> toResults(List<ResultResource> resultResources);
}
