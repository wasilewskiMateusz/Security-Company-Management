package pl.lodz.p.it.thesis.scm.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IfMatchValueException extends RuntimeException {
    private final String IF_MATCH_CODE="Exception.different.version";

}
