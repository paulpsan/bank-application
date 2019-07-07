package com.paulsan.appbank.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.Collections;

public class DTOModelMapper extends RequestResponseBodyMethodProcessor {

    public DTOModelMapper(ObjectMapper objectMapper) {
        super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
    }

    @Override
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        binder.validate();
    }
}