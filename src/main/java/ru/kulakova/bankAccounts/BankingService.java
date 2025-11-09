package ru.kulakova.bankAccounts;

import ru.kulakova.exceptions.NoAccountProvidedException;
import ru.kulakova.exceptions.NoSuchAccountException;
import ru.kulakova.exceptions.NotEnoughMoneyException;
import ru.kulakova.exceptions.WrongPasswordException;

import java.util.UUID;

/**
 * Интерфейс {@code BankingService}, определяющий методы для управления банковскими счетами.
 */
public interface BankingService {

    /**
     * Метод для создания нового банковского счёта с указанным паролем.
     *
     * @param password пароль для нового счёта
     */
    void createAccount(String password);

    /**
     * Метолд для перехода к указанному банковскому счёту по идентификатору и паролю.
     *
     * @param id идентификатор счёта к, которому необходимо перейти
     * @param password пароль для доступа к счёту
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws WrongPasswordException если введен неверный пароль
     */
    void goTo(UUID id, String password) throws NoSuchAccountException, WrongPasswordException;

    /**
     * Метод для пополнения баланса текущего счёта на указанную сумму.
     *
     * @param amount сумма для пополнения
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    void topUp(double amount) throws NoAccountProvidedException;

    /**
     * Метод для снятия указанной суммы с текущего счёта.
     *
     * @param amount сумма для снятия
     * @param password пароль для подтверждения операции
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     */
    void withdraw(double amount, String password) throws NoAccountProvidedException, WrongPasswordException, NotEnoughMoneyException;

    /**
     * Метод для получения баланса текущего счёта
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    void checkBalance() throws NoAccountProvidedException;

    /**
     * Метод для получения и вывода истории текущего счёта.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    void showHistory() throws NoAccountProvidedException;

    /**
     * Метод для изменения пароля текущего счёта.
     *
     * @param oldPassword старый пароль
     * @param newPassword новый пароль
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если старый пароль введен неверно
     */
    void changePassword(String oldPassword, String newPassword) throws NoAccountProvidedException, WrongPasswordException;

    /**
     * Метод для выхода из текукщего счёта и удаления истории операций
     */
    void exit();
}
