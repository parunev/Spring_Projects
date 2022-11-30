package softuni.exam.util.ValidationUtil;

import javax.validation.ConstraintViolation;

public interface ValidationUtil {
    <E> boolean isValid(E entity);
}
