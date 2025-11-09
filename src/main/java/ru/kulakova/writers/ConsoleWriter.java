package ru.kulakova.writers;


import ru.kulakova.history.TransactionType;
import ru.kulakova.history.TransactionsHistory;

import java.util.UUID;

/**
 * Класс {@code ConsoleWriter} реализует интерфейс {@link Writable} для вывода информации в консоль.
 */
public class ConsoleWriter implements Writable {

    /**
     * Метод выводит идентификатор счёта в консоль.
     *
     * @param id идентификатор счёта
     */
    @Override
    public void write(UUID id) {
        var strId = id.toString();
        System.out.println("Your id: " + strId);
    }

    /**
     * Ментод выводит баланс счёта в консоль.
     *
     * @param balance баланс счёта
     */
    @Override
    public void write(double balance) {
        System.out.println("Your balance: " + balance);
    }

    /**
     * Метод выводит историю транзакций в консоль.
     *
     * @param history история транзакций
     */
    @Override
    public void write(TransactionsHistory history) {
        System.out.println("Transactions history ");
        var transactions = history.getTransactions();
        for (var transaction : transactions) {
            if (transaction._type == TransactionType.TOP_UP){
                System.out.println("Top up   " + transaction._amount);
            } else{
                System.out.println("Withdraw " + transaction._amount);
            }
        }
    }
}
