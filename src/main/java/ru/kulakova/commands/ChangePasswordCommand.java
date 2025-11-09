package ru.kulakova.commands;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.exceptions.NoAccountProvidedException;
import ru.kulakova.exceptions.WrongPasswordException;

/**
 * Класс {@code ChangePasswordCommand} реализует команду изменения пароля текущего банковского счета.
 * <p>
 * Этот класс реализует интерфейс {@link Commanding} и использует сервис {@link BankingService} для выполнения операции изменения пароля.
 * </p>
 */
public class ChangePasswordCommand implements Commanding {

    private final BankingService _bankService;

    private final String _oldPassword;

    private final String _newPassword;

    public ChangePasswordCommand(BankingService bankService, String oldPassword, String newPassword) {
        _bankService = bankService;
        _oldPassword = oldPassword;
        _newPassword = newPassword;
    }

    /**
     * Метод выполняет команду изменения пароля.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если старый пароль введен неверно
     */
    @Override
    public void log() throws NoAccountProvidedException, WrongPasswordException {
        _bankService.changePassword(_oldPassword, _newPassword);
    }
}
