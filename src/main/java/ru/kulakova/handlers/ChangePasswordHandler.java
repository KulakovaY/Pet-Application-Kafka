package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.ChangePasswordCommand;
import ru.kulakova.commands.Commanding;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code ChangePasswordHandler} представляет обработчик команды изменения пароля.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде изменения пароля, он передается следующему обработчику в цепочке.
 * </p>
 */
public class ChangePasswordHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на изменение пароля.
     * <p>
     * Если первый элемент запроса равен "changePass" и запрос содержит три элемента (команда, старый пароль, новый пароль),
     * создается и возвращается команда {@link ChangePasswordCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link ChangePasswordCommand}, если запрос соответствует команде изменения пароля, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "changePass")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 3) {
            return new ChangePasswordCommand(bankingService, request.get(1), request.get(2));
        } else {
            return null;
        }
    }
}
