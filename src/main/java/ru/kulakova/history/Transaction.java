package ru.kulakova.history;

/**
 * Класс {@code Transaction}, описыающий банковскую операцию (транзакцию).
 */
public class Transaction {

    /**
     * Тип транзакции.
     */
    public final TransactionType _type;

    /**
     * Сумма транзакции.
     */
    public final double _amount;

    public Transaction(TransactionType type, double amount) {
        _type = type;
        _amount = amount;
    }
}
