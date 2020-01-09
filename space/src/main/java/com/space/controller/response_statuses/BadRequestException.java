package com.space.controller.response_statuses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Id not valid!")
public class BadRequestException extends RuntimeException {
}