package pl.lodz.p.it.thesis.scm.dto.responses;

public class ErrorResponse extends AppResponse{

    public ErrorResponse(String errorMessage) {
        super(false);
        addFullMessage(errorMessage);
    }
}
