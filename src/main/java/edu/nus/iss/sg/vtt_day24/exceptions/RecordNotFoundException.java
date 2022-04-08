package edu.nus.iss.sg.vtt_day24.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
