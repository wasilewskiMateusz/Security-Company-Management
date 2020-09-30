package pl.lodz.p.it.thesis.scm.dto.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AppResponse {
    Boolean success;
    private List<String> fullMessages;

    public AppResponse(boolean success) {
        this.success = success;
        fullMessages = new ArrayList<>();
    }

    public void addFullMessage(String message) {
        if (message == null)
            return;
        if (fullMessages == null)
            fullMessages = new ArrayList<>();

        fullMessages.add(message);
    }
}
