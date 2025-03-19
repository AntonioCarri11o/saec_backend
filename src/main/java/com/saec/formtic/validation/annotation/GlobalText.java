package com.saec.formtic.validation.annotation;

import com.saec.formtic.validation.validator.GlobalTextValidator;
import com.saec.formtic.validation.validator.enums.TextPattern;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GlobalTextValidator.class)
public @interface GlobalText {
    String message() default "Text is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    TextPattern textPattern() default TextPattern.GLOBAL;
    String field() default "text";
    boolean mandatory() default false;
}
