package pl.lodz.p.it.thesis.scm.dto.responses;

public class SuccessResponse extends AppResponse{

    public SuccessResponse(String message) {
        super(true);
        addFullMessage(message);
    }
    public SuccessResponse() {
        this(null);
    }
}
