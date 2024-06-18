package com.example.kotlinback.global.validation

import com.example.kotlinback.global.exception.FormValidationException
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import java.util.function.Consumer

@Component
object ValidationUtil {
    fun confirmError(bindingResult: BindingResult) {
        if (bindingResult.hasErrors()) {
            val sb = StringBuilder()
            val errorMessages = bindingResult.allErrors.stream().map { error: ObjectError -> error.defaultMessage }
                .toList()
            errorMessages.forEach(Consumer { message: String? -> sb.append("%s\n".format(message)) })
            throw FormValidationException(sb.toString())
        }
    }
}
