package app.components.mappers;

import app.domain.Lesson;
import app.domain.Module;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;
import app.dtos.GetLessonProgressResponse;
import app.dtos.GetModuleProgressResponse;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ModuleProgressMapper {

    private final LessonProgressMapper lessonProgressMapper;

    public ModuleProgressMapper(LessonProgressMapper lessonProgressMapper) {
        this.lessonProgressMapper = lessonProgressMapper;
    }

/*    public GetModuleProgressResponse toDto(ModuleProgress moduleProgress) {
        Long moduleId = moduleProgress.getId().getModuleId();
//        String status = moduleProgress.getStatus().getName();
        List<GetLessonProgressResponse> lessonProgresses = moduleProgress
                .getModule()
                .getLessons()
                .stream()
                .map(lessonProgressMapper::toDto)
                .toList();
        return new GetModuleProgressResponse(moduleId,lessonProgresses);
    }*/

    public GetModuleProgressResponse toDto(Module module,
                                           Map<Lesson, List<StepProgress>> stepProgressMap) {
        Long id = module.getId();
        List<GetLessonProgressResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : stepProgressMap.keySet()) {
            GetLessonProgressResponse lessonProgress =
                    lessonProgressMapper.toDto(lesson, stepProgressMap.get(lesson));
            lessonResponses.add(lessonProgress);
        }
        return new GetModuleProgressResponse(id, lessonResponses);
    }
}
