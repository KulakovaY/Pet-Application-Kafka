package ru.kulakova.exceptions;

/**
 * Исключение {@code NoSuchAccountException} выбрасывается, когда запрашиваемый банковский счёт не найден.
 * <p>
 * Это исключение является подклассом {@link Exception}.
 * </p>
 */
public class NoSuchAccountException extends Exception {

    public NoSuchAccountException() {
        super("No such account");
    }
}
