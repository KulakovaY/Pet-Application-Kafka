package ru.kulakova.history;

/**
 * Перечисление {@code TransactionType} представляет типы банковских транзакций.
 */
public enum TransactionType {
    /**
     * Тип транзакции: пополнение счета.
     */
    TOP_UP,
    /**
     * Тип транзакции: снятие средств со счета.
     */
    WITHDRAW
}
