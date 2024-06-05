package app.components.mappers;

import app.domain.progress.StepProgress;
import app.dtos.GetStepProgressResponse;

import org.springframework.stereotype.Component;

@Component
public class StepProgressMapper {

    public GetStepProgressResponse toDto(StepProgress stepProgress) {
        Long id = stepProgress.getId().getStepId();
        String status = stepProgress.getStatus().getName();
        return new GetStepProgressResponse(id, status);
    }
}
