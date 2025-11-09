package ru.kulakova.exceptions;

/**
 * Исключение {@code NotEnoughMoneyException} выбрасывается, когда на счёте недостаточно средств для выполнения операции снятия.
 * <p>
 * Это исключение является подклассом {@link Exception}.
 * </p>
 */
public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        super("Not enough money");
    }
}
