package com.example.school.validations;

import com.example.school.annotation.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        var fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        var fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);
//        if (fieldValue != null) {
//            if(fieldValue.toString().startsWith("$2a")){
//                return true;
//            }else{
//                return fieldValue.equals(fieldMatchValue);
//            }
//        } else {
//            return fieldMatchValue == null;
//        }

        if (fieldValue != null) {
            {
                return fieldValue.equals(fieldMatchValue);
            }
        } else {
            return fieldMatchValue == null;
        }
    }
}
