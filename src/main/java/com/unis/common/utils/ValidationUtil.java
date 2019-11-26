package com.unis.common.utils;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationUtil {

	private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
			.buildValidatorFactory().getValidator();

	public static <T> void validate(T obj) throws RuntimeException {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
		if (constraintViolations.size() > 0) {
			throw new RuntimeException(constraintViolations.iterator().next().getMessage());
		}
	}
}