package com.example.kotlinback.global.validation;

import com.examback.common.exception.FormValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
public class ValidationUtil {
    public static void confirmError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            errorMessages.forEach(message -> sb.append("%s\n".formatted(message)));
            throw new FormValidationException(sb.toString());
        }
    }
}
