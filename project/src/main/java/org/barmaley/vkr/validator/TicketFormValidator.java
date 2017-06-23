package org.barmaley.vkr.validator;

import org.barmaley.vkr.domain.Ticket;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TicketFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Ticket.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titleEng", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "annotation", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "annotationEng", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "filePdf", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeOfPublic", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeOfPublicEng", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "headOfDepartment", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullNameCurator", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullNameCuratorEng", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "posOfCurator", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "posOfCuratorEng", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "degreeOfCurator", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "degreeOfCuratorEng", "field.required");

    }
}
