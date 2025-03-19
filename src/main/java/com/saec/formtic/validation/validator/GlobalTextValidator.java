package com.saec.formtic.validation.validator;

import com.saec.formtic.validation.annotation.GlobalText;
import com.saec.formtic.validation.validator.enums.TextPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import java.util.regex.Pattern;

@NoArgsConstructor
public class GlobalTextValidator implements ConstraintValidator<GlobalText, String> {
    private String field = "text";
    private String errorMessage = "";
    private TextPattern textPattern = TextPattern.GLOBAL;
    private boolean mandatory = false;

    @Override
    public void initialize(GlobalText constraintAnnotation) {
        this.mandatory = constraintAnnotation.mandatory();
        this.field = constraintAnnotation.field();
        this.textPattern = constraintAnnotation.textPattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (this.mandatory && (value == null || value.isEmpty())) {
            context.buildConstraintViolationWithTemplate("The " + this.field + "is mandatory").addConstraintViolation();
            return false;
        }
        boolean isValid = isValidText(value);
        if (!isValid)
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        return isValid;
    }


    private boolean isValidText(String value) {
        Pattern pattern;
        switch (textPattern) {
            case GLOBAL:
                pattern = Pattern.compile("^(?!\\s)(?!.*\\s$)[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9 ]+$");
                errorMessage = "The " + field + " must only include numbers and alphabeat letters";
                break;
            case ONLY_LETTERS:
                pattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+$");
                errorMessage = "The " + field + " must only include alphabeat letters";
                break;
            case EMAIL:
                pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
                errorMessage = "The " + field + " is not a valid email address";
                break;
            case NUMBER:
                pattern = Pattern.compile("^[0-9]*$");
                errorMessage = "The " + field + " must only include numbers";
                break;
            case PHONE:
                pattern = Pattern.compile("^\\+?\\d{1,3}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{3,4}[-.\\s]?\\d{4}$");
                errorMessage = "The " + field + " is not a valid phone number";
                break;
            default:
                return false;
        }
        return pattern.matcher(value).matches();
    }
}
