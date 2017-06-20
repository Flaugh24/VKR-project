package org.barmaley.vkr.validator;

import org.barmaley.vkr.dto.ActDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TicketFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ActDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ticketsId", "tickets.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "act.department", "department.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "act.institute", "institute.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "act.position", "position.required");
    }
}
