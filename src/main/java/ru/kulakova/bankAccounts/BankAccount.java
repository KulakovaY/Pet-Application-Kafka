package ru.kulakova.bankAccounts;

import ru.kulakova.exceptions.NotEnoughMoneyException;

import java.util.UUID;

/**
 * Класс {@code BankAccount}, описывающий объект "Банковский счёт" с уникальным идентификатором,
 * паролем для доступа и балансом.
 */
public class BankAccount {

    private final UUID _id;
    private String _password;
    private double _balance = 0;

    public BankAccount(String password) {
        _id = UUID.randomUUID();
        _password = password;
    }

    /**
     * Метод для получения идентификатора счёта.
     *
     * @return уникальный идентификатор счёта
     */
    public UUID getId() {
        return _id;
    }

    /**
     * Метод для получения баланса счёта.
     *
     * @return баланс счёта
     */
    public double getBalance() {
        return _balance;
    }


    /**
     * Метод, проверяющий совпадения пароля счёта с предоставленным паролем.
     *
     * @param password пароль для сопоставления
     * @return уникальный идентификатор счёта
     */
    public boolean checkPassword(String password) {
        return _password.equals(password);
    }

    /**
     * Метод, проверяющий совпадения пароля счёта с предоставленным паролем.
     *
     * @param oldPassword старый пароль
     * @param newPassword новый пароль
     * @return {@code true}, если пароль успешно изменен, иначе {@code false}
     */
    public boolean setPassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(_password)) {
            _password = newPassword;
            return true;
        }

        return false;
    }

    /**
     * Метод пополняет баланса счёта на указанную сумму.
     *
     * @param amount сумма для пополнения
     */
    public void topUpBalance(double amount) {
        _balance += amount;
    }

    /**
     * Метод снятия денег со счёта на указанную сумму.
     *
     * @param amount сумма для снятия
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     */
    public void withdrawMoney(double amount) throws NotEnoughMoneyException {
        if (_balance < amount) {
            throw new NotEnoughMoneyException();
        }
        _balance -= amount;
    }
}
