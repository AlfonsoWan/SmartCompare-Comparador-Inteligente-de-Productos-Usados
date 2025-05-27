package com.smartcompare.searchhistory.domain.exception;

public class SearchHistoryNotFoundException extends RuntimeException {
    public SearchHistoryNotFoundException(String message) {
        super(message);
    }

    public SearchHistoryNotFoundException(Long id) {
        super("Historial de búsqueda no encontrado con ID: " + id);
    }
}
