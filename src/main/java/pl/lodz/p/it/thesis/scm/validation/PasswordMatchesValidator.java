package pl.lodz.p.it.thesis.scm.validation;

import pl.lodz.p.it.thesis.scm.dto.user.UserPasswordAbstractDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserPasswordDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        //The default implementation is a no-op.
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserPasswordAbstractDTO userPasswordDTO = (UserPasswordAbstractDTO) object;
        return userPasswordDTO.getPassword().equals(userPasswordDTO.getRePassword());
    }
}
