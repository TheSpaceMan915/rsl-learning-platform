package app.components.mappers;

import app.dtos.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//    TODO: Write the controllers for modules, lessons and steps

@Component
public class ModuleMapper {

    private final LessonMapper mapper;

    public ModuleMapper(LessonMapper mapper) {
        this.mapper = mapper;
    }

    public GetModuleResponse toModule(GetModuleRequest moduleRequest) {
        long id = moduleRequest.data().id();
        GetModuleRequest.Attributes attributes = moduleRequest.data().attributes();
        String title = attributes.title();
        String description = attributes.description();
        String status = attributes.status().status();
        GetLessonsRequest lessonsRequest = attributes.lectures();
        List<GetLessonResponse> lessonsResponse = mapper.toLessons(lessonsRequest);
        return new GetModuleResponse(id, title, description, status, lessonsResponse);
    }

    public List<GetModuleResponse> toModules(GetModulesRequest modulesRequest) {
        List<GetModuleResponse> modulesResponse = new ArrayList<>();
        for (GetModulesRequest.Data data : modulesRequest.data()) {
            long id = data.id();
            GetModulesRequest.Attributes attributes = data.attributes();
            String title = attributes.title();
            String description = attributes.description();
            String status = attributes.status().status();
            modulesResponse.add(new GetModuleResponse(id, title, description, status, null));
        }
        return modulesResponse;
    }
}
