package space.sotis.jlamademo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DecimalValidator implements ConstraintValidator<Decimal, Number> {
    private double min;
    private double max;
    private int maxDecimalPlaces;
    private int minDecimalPlaces;
    private String minMessage;
    private String maxMessage;
    private String maxDecimalPlacesMessage;
    private String minDecimalPlacesMessage;

    @Override
    public void initialize(Decimal constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.maxDecimalPlaces = constraintAnnotation.maxDecimalPlaces();
        this.minDecimalPlaces = constraintAnnotation.minDecimalPlaces();
        this.minMessage = constraintAnnotation.minMessage();
        this.maxMessage = constraintAnnotation.maxMessage();
        this.maxDecimalPlacesMessage = constraintAnnotation.maxDecimalPlacesMessage();
        this.minDecimalPlacesMessage = constraintAnnotation.minDecimalPlacesMessage();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        double doubleValue = value.doubleValue();
        if (setContextMessage(context, doubleValue < min, minMessage, doubleValue > max, maxMessage)) {
            return false;
        }

        String stringValue = value.toString();
        int decimalPlaces = stringValue.length() - stringValue.indexOf('.') - 1;
        return !setContextMessage(context, decimalPlaces > maxDecimalPlaces, maxDecimalPlacesMessage, decimalPlaces < minDecimalPlaces, minDecimalPlacesMessage);
    }

    private boolean setContextMessage(ConstraintValidatorContext context, boolean minViolation, String minMessage, boolean maxViolation, String maxMessage) {
        if (minViolation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(minMessage)
                    .addConstraintViolation();
            return true;
        }

        if (maxViolation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(maxMessage)
                    .addConstraintViolation();
            return true;
        }
        return false;
    }
}
