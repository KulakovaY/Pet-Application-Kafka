package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.exceptions.NoAccountProvidedException;
import ru.kulakova.exceptions.NotEnoughMoneyException;
import ru.kulakova.exceptions.WrongPasswordException;

/**
 * Класс {@code WithdrawCommand} реализует команду снятия средств с текущего банковского счёта.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции снятия средств.
 * </p>
 */
public class WithdrawCommand implements Commanding {

    private final BankingService _bankingService;

    /**
     * Сумма, которую необходимо списать со счёта.
     */
    private final double _amount;

    private final String _password;

    public WithdrawCommand(BankingService bankingService, double amount, String password) {
        _bankingService = bankingService;
        _amount = amount;
        _password = password;
    }

    /**
     * Метод выполняет команду снятия средств с текущего счёта.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     */
    @Override
    public void log() throws NoAccountProvidedException, WrongPasswordException, NotEnoughMoneyException {
        _bankingService.withdraw(_amount, _password);
    }
}
