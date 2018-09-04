package com.ifast.common.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/9/4 12:04 | Aron</small>
 */
public class ValidateUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationResult validateEntity(T obj) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (ValidateUtils.isNotEmpty(set)) {
            result.setHasErrors(true);
            List<String> errorMsg = new ArrayList<>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.add(cv.getPropertyPath() + cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (ValidateUtils.isNotEmpty(set)) {
            result.setHasErrors(true);
            List<String> errorMsg = new ArrayList<>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.add(cv.getMessageTemplate());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

    private static boolean isNotEmpty(Collection<?> c) {
        return (c != null) && (!c.isEmpty());
    }

}
