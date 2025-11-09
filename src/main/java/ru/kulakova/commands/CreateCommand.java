package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;

/**
 * Класс {@code CreateCommand} реализует команду создания нового банковского счёта.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции создания счёта.
 * </p>
 */
public class CreateCommand implements Commanding {

    private final BankingService _bankingService;

    private final String _password;

    public CreateCommand(BankingService bankingService, String password) {
        _bankingService = bankingService;
        _password = password;
    }

    /**
     * Метод выполняет команду создания нового банковского счёта.
     */
    @Override
    public void log() {
        _bankingService.createAccount(_password);
    }
}
