/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgz.server.exceptionhandlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author samg.zun
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomError {

    @Getter
    @NonNull
    private String message;

    @Getter
    @NonNull
    private String name;

    @Getter
    private List<FieldErrorResponse> errors;

    public LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }

}
