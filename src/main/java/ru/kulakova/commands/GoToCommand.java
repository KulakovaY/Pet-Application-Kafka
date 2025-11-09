package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.exceptions.NoSuchAccountException;
import ru.kulakova.exceptions.WrongPasswordException;

import java.util.UUID;

/**
 * Класс {@code GoToCommand} реализует команду перехода к указанному банковскому счёту.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции перехода к счёту по его идентификатору и паролю.
 * </p>
 */
public class GoToCommand implements Commanding {

    private final BankingService _bankingService;

    private final UUID _id;

    private final String _password;

    public GoToCommand(BankingService bankingService, UUID id, String password) {
        _bankingService = bankingService;
        _id = id;
        _password = password;
    }

    /**
     * Метод выполняет команду перехода к указанному банковскому счёту.
     *
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws WrongPasswordException если введен неверный пароль
     */
    @Override
    public void log() throws NoSuchAccountException, WrongPasswordException {
        _bankingService.goTo(_id, _password);
    }
}
