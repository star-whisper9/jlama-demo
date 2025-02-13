package space.sotis.jlamademo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DecimalValidator.class)
@Documented
public @interface Decimal {
    String message() default "Invalid decimal value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default Double.MIN_VALUE;

    double max() default Double.MAX_VALUE;

    int maxDecimalPlaces() default 1;

    int minDecimalPlaces() default 0;

    String minMessage() default "值必须大于等于 {min}";

    String maxMessage() default "值必须小于等于 {max}";

    String maxDecimalPlacesMessage() default "值必须少于等于 {maxDecimalPlaces} 位小数";

    String minDecimalPlacesMessage() default "值必须多于等于 {minDecimalPlaces} 位小数";
}
