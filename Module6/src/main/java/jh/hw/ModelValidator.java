package jh.hw;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public final class ModelValidator {
    private static final Validator MODEL_VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final String DELIMITER = "\n\t";

    /* Validation of the specified parameter of a class. */
    public static String getParameterViolations(Class<?> clazz, String parameterName, String token) {
        return getViolations(clazz, MODEL_VALIDATOR.validateValue(clazz, parameterName, token));
    }

    /* Full validation of all parameters of a model bean. */
    public static <T> String getModelViolations(T model) {
        return getViolations(model.getClass(), MODEL_VALIDATOR.validate(model));
    }

    private static <T> String getViolations(Class<?> clazz, Set<ConstraintViolation<T>> violations) {
        if (violations.isEmpty()) {
            return null;
        }

        return "Validation failed for [" +  clazz.getSimpleName() + "]:" + DELIMITER +
                violations.stream()
                        .map(violation -> String.format("field: [%s] violation: [%s]", violation.getPropertyPath().toString(),
                                violation.getMessage()))
                        .collect(Collectors.joining(DELIMITER));
    }
}