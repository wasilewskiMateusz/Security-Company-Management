package pl.lodz.p.it.thesis.scm.util;

import lombok.Getter;

import java.util.List;

@Getter
public class RestMessage {
    private String message;
    private List<String> detailedMessages;

    public RestMessage(String message) {
        this.message = message;
    }

    public RestMessage(String message, List<String> detailedMessages) {
        this.message = message;
        this.detailedMessages = detailedMessages;
    }
}
