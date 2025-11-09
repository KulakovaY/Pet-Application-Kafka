package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.exceptions.NoAccountProvidedException;

/**
 * Класс {@code CheckBalanceCommand} реализует команду проверки баланса текущего банковского счёта.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции проверки баланса.
 * </p>
 */
public class CheckBalanceCommand implements Commanding {

    private final BankingService _bankService;

    public CheckBalanceCommand(BankingService bankService) {
        _bankService = bankService;
    }

    /**
     * Метод выполняет команду проверки баланса.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    @Override
    public void log() throws NoAccountProvidedException {
        _bankService.checkBalance();
    }
}
