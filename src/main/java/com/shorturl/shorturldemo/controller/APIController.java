package com.shorturl.shorturldemo.controller;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(path = "/v1/api")
public @interface APIController {
    //public String value() default "";
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    public String path() default "";
}
