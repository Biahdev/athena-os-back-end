package dev.abeatriz.athena_os.exception.validators.enums;

import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class StatusClientTypeSubSetValidator implements ConstraintValidator<StatusClientTypeSubset, ClientStatus> {
    private ClientStatus[] subset;

    @Override
    public void initialize(StatusClientTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(ClientStatus value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}