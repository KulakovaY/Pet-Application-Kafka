package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.exceptions.NoAccountProvidedException;

/**
 * Класс {@code TopUpCommand} реализует команду пополнения баланса текущего банковского счёта.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции пополнения баланса.
 * </p>
 */
public class TopUpCommand implements Commanding {

    private final BankingService _bankingService;

    /**
     * Сумма, на которую необходимо пополнить счёт.
     */
    private final double _amount;

    public TopUpCommand(BankingService bankingService, double amount) {
        _bankingService = bankingService;
        _amount = amount;
    }

    /**
     * Метод выполняет команду пополнения баланса текущего счёта.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    @Override
    public void log() throws NoAccountProvidedException {
        _bankingService.topUp(_amount);
    }
}
