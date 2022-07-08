package com.example.taxiservicespring.controller.validation;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class DifferentFieldsValidator implements ConstraintValidator<Different, Object> {
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private String[] fields;

    @Override
    public void initialize(Different constraintAnnotation) {
        fields = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Set<Object> set = Stream.of(fields)
                .map(field -> PARSER.parseExpression(field).getValue(value))
                .collect(Collectors.toSet());
        return set.size() == fields.length;
    }
}
