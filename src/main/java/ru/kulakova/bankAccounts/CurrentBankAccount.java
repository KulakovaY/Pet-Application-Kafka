package ru.kulakova.bankAccounts;

import ru.kulakova.history.TransactionsHistory;

/**
 * Класс {@code CurrentBankAccount}, описывающий текущий активный банковский счёт и его историю операций.
 */
public class CurrentBankAccount {

    /**
     * Текущий банковский счёт.
     */
    public BankAccount _bankAccount = null;

    /**
     * История транзакций текущего счёта.
     */
    public TransactionsHistory _transactions = null;

    /**
     * Метод для для установления текущего банковского счёта.
     *
     * @param bankAccount банковский счёт, который будет установлен, в качестве текущего
     * @return текущий объект {@code CurrentBankAccount} для цепочки вызовов
     */
    public CurrentBankAccount setBankAccount(BankAccount bankAccount) {
        _bankAccount = bankAccount;
        return this;
    }

    /**
     * Метод для выхода из текущего счёта. Сбрасывая его значение на {@code null}.
     */
    public void exitAccount() {
        _bankAccount = null;
    }

    /**
     * Метод для удаления истории операций текущего счёта. Сбрасывая ее значение на {@code null}.
     */
    public void deleteHistory() {
        _transactions = null;
    }
}
