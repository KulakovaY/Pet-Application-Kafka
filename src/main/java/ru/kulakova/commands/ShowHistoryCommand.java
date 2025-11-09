package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.exceptions.NoAccountProvidedException;

/**
 * Класс {@code ShowHistoryCommand} реализует команду отображения истории операций текущего банковского счёта.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции отображения истории операций.
 * </p>
 */
public class ShowHistoryCommand implements Commanding {

    private final BankingService _bankService;

    public ShowHistoryCommand(BankingService bankService) {
        _bankService = bankService;
    }

    /**
     * Метод выполняет команду отображения истории операций текущего счёта.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     */
    @Override
    public void log() throws NoAccountProvidedException{
        _bankService.showHistory();
    }
}
