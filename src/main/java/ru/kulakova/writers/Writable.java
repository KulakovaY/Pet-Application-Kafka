package ru.kulakova.writers;

import ru.kulakova.history.TransactionsHistory;

import java.util.UUID;

/**
 * Интерфейс {@code Writable} определяет методы для вывода информации, после выплнения операций с банковским счётом, для пользователя.
 */
public interface Writable {

    /**
     * Выводит идентификатор счёта.
     *
     * @param id идентификатор счёта
     */
    void write(UUID id);

    /**
     * Выводит баланс счёта.
     *
     * @param balance баланс счёта
     */
    void write(double balance);

    /**
     * Выводит историю транзакций.
     *
     * @param history история транзакций
     */
    void write(TransactionsHistory history);
}
