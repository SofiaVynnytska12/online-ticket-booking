package duikt.practice.otb.resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.SerializationFeature.*;

public class ControllerAdvice {

    public static <T> String asJsonString(final T obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
