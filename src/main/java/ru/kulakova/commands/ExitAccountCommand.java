package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;

/**
 * Класс {@code ExitAccountCommand} реализует команду выхода из текущего банковского счёта.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции выхода из текущего счёта.
 * </p>
 */
public class ExitAccountCommand implements Commanding {

    private final BankingService _bankingService;

    public ExitAccountCommand(BankingService bankingService) {
        _bankingService = bankingService;
    }

    /**
     * Метод выполняет команду выхода из текущего банковского счёта.
     */
    @Override
    public void log() {
        _bankingService.exit();
    }
}
