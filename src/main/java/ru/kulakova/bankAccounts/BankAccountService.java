package ru.kulakova.bankAccounts;

import ru.kulakova.exceptions.NoAccountProvidedException;
import ru.kulakova.exceptions.NoSuchAccountException;
import ru.kulakova.exceptions.NotEnoughMoneyException;
import ru.kulakova.exceptions.WrongPasswordException;
import ru.kulakova.history.Transaction;
import ru.kulakova.history.TransactionType;
import ru.kulakova.history.TransactionsHistory;
import ru.kulakova.repositories.BankAccountsRepository;
import ru.kulakova.writers.Writable;

import java.util.UUID;

/**
 * Класс {@code BankAccountService}, предоставляющий сервис для управления банковскими счетами.
 * <p>
 * Этот класс реализует интерфейс {@link BankingService}.
 * </p>
 */
public class BankAccountService implements BankingService {

    public CurrentBankAccount _currentBankAccount;

    /**
     * Репозиторий для сохранения всех созданных банковских счетов.
     */
    private final BankAccountsRepository _usersRepository = BankAccountsRepository.initialize();

    /**
     * Экземпляр класса, позволяющего выводить некоторые данные пользователю.
     */
    private final Writable _writer;

    public BankAccountService(CurrentBankAccount currentBankAccount, Writable writer) {
        _currentBankAccount = currentBankAccount;
        _writer = writer;
    }

    /**
     * Метод для создания нового банковского счёта с указанным паролем.
     *
     * @param password пароль для нового счёта
     */
    @Override
    public void createAccount(String password) {
        var bankAccount = new BankAccount(password);
        _currentBankAccount.setBankAccount(bankAccount);
        _currentBankAccount._transactions = new TransactionsHistory();
        _usersRepository.addAccount(bankAccount);

        _writer.write(_currentBankAccount._bankAccount.getId());
    }

    /**
     * Метолд для перехода к указанному банковскому счёту по идентификатору и паролю.
     *
     * @param id идентификатор счёта к, которому необходимо перейти
     * @param password пароль для доступа к счёту
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws WrongPasswordException если введен неверный пароль
     */
    @Override
    public void goTo(UUID id, String password) throws NoSuchAccountException, WrongPasswordException {
        var bankAccount = _usersRepository.findAccount(id);

        if (bankAccount == null) {
            throw new NoSuchAccountException();
        }

        if (bankAccount.checkPassword(password)) {
            _currentBankAccount.setBankAccount(bankAccount);
            _currentBankAccount.deleteHistory();
        } else {
            throw new WrongPasswordException();
        }
    }

    /**
     * Метод для пополнения баланса текущего счёта на указанную сумму.
     *
     * @param amount сумма для пополнения
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    @Override
    public void topUp(double amount) throws NoAccountProvidedException {
        if (_currentBankAccount._bankAccount == null) throw new NoAccountProvidedException();

        _usersRepository.addMoney(_currentBankAccount._bankAccount, amount);
        _currentBankAccount._transactions.addTransaction(new Transaction(TransactionType.TOP_UP, amount));
    }

    /**
     * Метод для снятия указанной суммы с текущего счёта.
     *
     * @param amount сумма для снятия
     * @param password пароль для подтверждения операции
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     */
    @Override
    public void withdraw(double amount, String password) throws NoAccountProvidedException, WrongPasswordException, NotEnoughMoneyException {
        if (_currentBankAccount._bankAccount == null) throw new NoAccountProvidedException();

        if (!_currentBankAccount._bankAccount.checkPassword(password)) {
            throw new WrongPasswordException();
        }

        _usersRepository.withdrawMoney(_currentBankAccount._bankAccount, amount);
        _currentBankAccount._transactions.addTransaction(new Transaction(TransactionType.WITHDRAW, amount));
    }

    /**
     * Метод для получения баланса текущего счёта
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    @Override
    public void checkBalance() throws NoAccountProvidedException {
        if (_currentBankAccount._bankAccount == null) throw new NoAccountProvidedException();

        double balance = _usersRepository.getBalance(_currentBankAccount._bankAccount);

        _writer.write(balance);
    }

    /**
     * Метод для получения и вывода истории текущего счёта.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    @Override
    public void showHistory() throws NoAccountProvidedException {
        if (_currentBankAccount._bankAccount == null) throw new NoAccountProvidedException();

        _writer.write(_currentBankAccount._transactions);
    }

    /**
     * Метод для изменения пароля текущего счёта.
     *
     * @param oldPassword старый пароль
     * @param newPassword новый пароль
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если старый пароль введен неверно
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) throws NoAccountProvidedException, WrongPasswordException {
        if (_currentBankAccount._bankAccount == null) throw new NoAccountProvidedException();

        if (!_currentBankAccount._bankAccount.setPassword(oldPassword, newPassword)) {
            throw new WrongPasswordException();
        }
    }

    /**
     * Метод для выхода из текукщего счёта и удаления истории операций
     */
    @Override
    public void exit() {
        _currentBankAccount.exitAccount();
        _currentBankAccount.deleteHistory();
    }

    /**
     * Метод для получения текущего банковского счёта.
     *
     * @return текущий банковский счёт
     */
    public CurrentBankAccount getCurrentBankAccount() {
        return _currentBankAccount;
    }
}
