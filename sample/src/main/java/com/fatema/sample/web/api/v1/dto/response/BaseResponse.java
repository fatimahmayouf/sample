package com.fatema.sample.web.api.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    public static ResponseEntity<Object> responseBuilder(
            String message,
            HttpStatus status,
            Object responseObject
    ){
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", responseObject);


        return new ResponseEntity<>(response,status);
    }
}
