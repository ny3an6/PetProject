package com.nikita.simpleProject.dto.response;

import com.nikita.simpleProject.dto.ApiResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiMessage {
    private HttpStatus status;
    private String message;
    private Object data;
    private ApiResult result;
}
