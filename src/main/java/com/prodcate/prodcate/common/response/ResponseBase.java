package com.prodcate.prodcate.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBase<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ResponseBase<T> success(T data) {
        return new ResponseBase<>(true, "İşleminiz başarılı bir şekilde gerçekleşmiştir.", data, LocalDateTime.now());
    }

    public static <T> ResponseBase<T> success(String message, T data) {
        return new ResponseBase<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ResponseBase<T> fail(String message){
        return new ResponseBase<>(false, message, null, LocalDateTime.now());
    }
}
