package com.back.backend.exception;


import java.time.LocalDateTime;


public record ErrorMessage(LocalDateTime timestamp, String message, int status) {
}
