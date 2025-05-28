package com.smartcompare.comparison.domain.exception;

public class ComparisonNotFoundException extends RuntimeException {
    public ComparisonNotFoundException(String message) {
        super(message);
    }
}