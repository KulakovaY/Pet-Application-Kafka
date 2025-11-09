package ru.kulakova.history;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code TransactionsHistory}, определяющий историю транзакций банковского счета.
 */
public class TransactionsHistory {

    private List<Transaction> _transactions;

    public TransactionsHistory() {
        _transactions = new ArrayList<Transaction>();
    }

    /**
     * Ментод добавляет новую транзакцию в историю.
     *
     * @param transaction транзакция для добавления
     */
    public void addTransaction(Transaction transaction) {
        _transactions.add(transaction);
    }

    /**
     * Метод возвращает список всех транзакций.
     *
     * @return список транзакций
     */
    public List<Transaction> getTransactions() {
        return _transactions;
    }

}
