package ru.kulakova.repositories;

import ru.kulakova.bankAccounts.BankAccount;
import ru.kulakova.exceptions.NotEnoughMoneyException;

import java.util.*;

/**
 * Класс {@code BankAccountsRepository}, определяющий репозиторий для хранения и управления банковскими счётами.
 * <p>
 * Этот класс реализует паттерн Singleton, обеспечивая единственный экземпляр репозитория.
 * </p>
 */
public class BankAccountsRepository {

    private static final BankAccountsRepository _instance = null;

    private final List<BankAccount> _catalogue;

    private BankAccountsRepository() {
        _catalogue = new ArrayList<>();
    }

    /**
     * Метод инициализирует и возвращает единственный экземпляр репозитория.
     *
     * @return экземпляр {@code BankAccountsRepository}
     */
    public static BankAccountsRepository initialize() {
        return Objects.requireNonNullElseGet(_instance, BankAccountsRepository::new);
    }

    /**
     * Метод добавляет банковский счёт в репозиторий.
     *
     * @param account банковский счёт для добавления
     * @return текущий экземпляр репозитория для поддержки цепочки вызовов
     */
    public BankAccountsRepository addAccount(BankAccount account) {
        _catalogue.add(account);

        return this;
    }

    /**
     * Метод находит банковский счёт по его идентификатору.
     *
     * @param accountId идентификатор счёта
     * @return найденный банковский счёт или {@code null}, если счёт не найден
     */
    public BankAccount findAccount(UUID accountId) {
        Optional<BankAccount> result = _catalogue.stream()
                .filter(catalogueAccount -> catalogueAccount.getId().equals(accountId))
                .findFirst();

        return result.orElse(null);
    }

    /**
     * Метод удаляет банковский счёт из репозитория.
     *
     * @param account банковский счёт для удаления
     * @return текущий экземпляр репозитория для поддержки цепочки вызовов
     */
    public BankAccountsRepository deleteAccount(BankAccount account) {
        var bankAccount = findAccount(account.getId());
        if (bankAccount != null) {
            _catalogue.remove(bankAccount);
        }

        return this;
    }

    /**
     * Метод для пополнения баланса указанного банковского счёта.
     *
     * @param account банковский счёт
     * @param money сумма для пополнения
     * @return текущий экземпляр репозитория для поддержки цепочки вызовов
     */
    public BankAccountsRepository addMoney(BankAccount account, double money) {
        var bankAccountNum = findIndex(account);
        if (bankAccountNum != -1) {
            _catalogue.get(bankAccountNum).topUpBalance(money);
        }

        return this;
    }

    /**
     * Метод для снятия средств с указанного банковского счёта.
     *
     * @param account банковский счёт
     * @param money сумма для снятия
     * @return текущий экземпляр репозитория для поддержки цепочки вызовов
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     */
    public BankAccountsRepository withdrawMoney(BankAccount account, double money) throws NotEnoughMoneyException {
        var bankAccountNum = findIndex(account);
        if (bankAccountNum != -1) {
            _catalogue.get(bankAccountNum).withdrawMoney(money);
        }
        
        return this;
    }

    /**
     * Метод возвращает баланс указанного банковского счёта.
     *
     * @param account банковский счёт
     * @return баланс счёта или {@code -1}, если счёт не найден
     */
    public double getBalance(BankAccount account) {
        var bankAccountNum = findIndex(account);
        if (bankAccountNum != -1) {
            return _catalogue.get(bankAccountNum).getBalance();
        }
        return -1;
    }

    /**
     * Метод находит индекс банковского счёта в списке.
     *
     * @param account банковский счёт
     * @return индекс счёта в списке или {@code -1}, если счёт не найден
     */
    private int findIndex(BankAccount account) {
        return _catalogue.indexOf(account);
    }

}
