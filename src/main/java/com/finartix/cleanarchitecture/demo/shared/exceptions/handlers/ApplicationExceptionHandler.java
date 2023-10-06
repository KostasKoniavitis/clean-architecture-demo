package com.finartix.cleanarchitecture.demo.shared.exceptions.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler extends RequestExceptionHandler {}
