package app.components.mappers;

import app.dtos.GetStepRequest;
import app.dtos.GetStepResponse;
import app.dtos.GetStepsRequest;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class StepMapper {

    public GetStepResponse toStep(GetStepRequest stepRequest) {
        long id = stepRequest.data().id();
        GetStepRequest.Attributes attributes = stepRequest.data().attributes();
        String title = attributes.title();
        String description = attributes.description();
        String status = attributes.status().status();
        String type = attributes.type().type();
        String content = attributes.content().get(0).text();
        return new GetStepResponse(id, title, description, status, type, content);
    }

    public List<GetStepResponse> toSteps(GetStepsRequest stepsRequest) {
        List<GetStepResponse> stepsResponse = new ArrayList<>();
        for (GetStepsRequest.Data data : stepsRequest.data()) {
            long id = data.id();
            GetStepsRequest.Attributes attributes = data.attributes();
            String title = attributes.title();
            String description = attributes.description();
            String status = attributes.status().status();
            String type = attributes.type().type();
            stepsResponse.add(new GetStepResponse(id, title, description, status, type, null));
        }
        return stepsResponse;
    }
}
