package com.space.controller.response_statuses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ship not found!")
public class ResourceNotFoundException extends RuntimeException {
}