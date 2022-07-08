package com.example.taxiservicespring.controller.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = DifferentFieldsValidator.class)
@Documented
public @interface Different {

    String[] value();

    String message() default "{location.different}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
