package softuni.exam.util.Validation;

import javax.validation.ConstraintViolation;


public interface ValidationUtil {

    <E> boolean isValid(E entity);
}
