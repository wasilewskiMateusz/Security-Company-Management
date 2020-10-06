package pl.lodz.p.it.thesis.scm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestException extends RuntimeException{
    private final String message;
}
