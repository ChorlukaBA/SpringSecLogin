package com.lm.SpringSecLogin.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class RegistrationException extends RuntimeException
{
    private final String errorMessage;
}