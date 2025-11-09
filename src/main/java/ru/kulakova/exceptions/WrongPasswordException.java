package ru.kulakova.exceptions;

/**
 * Исключение {@code WrongPasswordException} выбрасывается, когда введён неверный пароль для текущего банковского счёта.
 * <p>
 * Это исключение является подклассом {@link Exception}.
 * </p>
 */
public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
        super("Wrong password");
    }
}
