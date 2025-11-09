package ru.kulakova.exceptions;

/**
 * Исключение {@code NoAccountProvidedException} выбрасывается, когда операция требует наличия текущего счёта, но он не был установлен заранее.
 * <p>
 * Это исключение является подклассом {@link Exception}.
 * </p>
 */
public class NoAccountProvidedException extends Exception {

    public NoAccountProvidedException() {
        super("No account provided");
    }
}
