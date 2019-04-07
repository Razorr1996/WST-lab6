package ru.basa62.wst.lab6;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private boolean error = false;
    private String message;
    private T result;

    public static <T> Result<T> left(String message) {
        return new Result<>(true, message, null);
    }

    public static <T> Result<T> right(T o) {
        return new Result<>(false, null, o);
    }
}
