package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.CreateCommand;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code CreateHandler} представляет обработчик команды создания нового банковского счёта.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде создания счёта, он передается следующему обработчику в цепочке.
 * </p>
 */
public class CreateHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на создание нового банковского счёта.
     * <p>
     * Если первый элемент запроса равен "create" и запрос содержит два элемента (команда и пароль),
     * создается и возвращается команда {@link CreateCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link CreateCommand}, если запрос соответствует команде создания счёта, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "create")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 2) {
            return new CreateCommand(bankingService, request.get(1));
        } else {
            return null;
        }
    }
}
