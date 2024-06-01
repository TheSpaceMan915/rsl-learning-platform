package app.components.mappers;

import app.domain.progress.ModuleProgress;
import app.dtos.GetLessonProgressResponse;
import app.dtos.GetModuleProgressResponse;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleProgressMapper {

    private final LessonProgressMapper lessonProgressMapper;

    public ModuleProgressMapper(LessonProgressMapper lessonProgressMapper) {
        this.lessonProgressMapper = lessonProgressMapper;
    }

    public GetModuleProgressResponse toDto(ModuleProgress moduleProgress) {
        Long moduleId = moduleProgress.getId().getModuleId();
        String status = moduleProgress.getStatus().getName();
        List<GetLessonProgressResponse> lessonProgresses = moduleProgress
                .getModule()
                .getLessons()
                .stream()
                .map(lessonProgressMapper::toDto)
                .toList();
        return new GetModuleProgressResponse(moduleId, status, lessonProgresses);
    }
}
