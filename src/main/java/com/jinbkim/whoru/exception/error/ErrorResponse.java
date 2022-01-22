package com.jinbkim.whoru.exception.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {
    private LocalDateTime localDateTime;
    private String message;
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)  // null 데이터는 JSON에 나타나지 않음
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrorList;

    @Builder
    public ErrorResponse(String message, int status) {
        localDateTime = LocalDateTime.now();
        this.message = message;
        this.status = status;
    }

    public void setCustomFieldErrorList(Errors errors) {
        List<FieldError> fieldErrors = errors.getFieldErrors();
        customFieldErrorList = new ArrayList<>();
        fieldErrors.forEach(error -> {
            customFieldErrorList.add(new CustomFieldError(error.getCodes()[0], error.getRejectedValue(), error.getDefaultMessage()));
        });
    }

    @AllArgsConstructor
    @Getter
    public static class CustomFieldError {
        private String field;
        private Object value;
        private String reason;
    }
}
