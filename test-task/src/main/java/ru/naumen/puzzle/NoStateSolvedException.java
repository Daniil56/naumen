package ru.naumen.puzzle;

public class NoStateSolvedException extends RuntimeException {
    public NoStateSolvedException(String s) {
        super(s);
    }
}