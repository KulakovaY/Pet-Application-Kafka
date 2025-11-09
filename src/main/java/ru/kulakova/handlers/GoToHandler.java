package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.GoToCommand;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс {@code GoToHandler} представляет обработчик команды перехода к указанному банковскому счёту.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде перехода, он передается следующему обработчику в цепочке.
 * </p>
 */
public class GoToHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на переход к указанному банковскому счёту.
     * <p>
     * Если первый элемент запроса равен "goto" и запрос содержит три элемента (команда, идентификатор счёта, пароль),
     * создается и возвращается команда {@link GoToCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link GoToCommand}, если запрос соответствует команде перехода, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "goto")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 3) {
            return new GoToCommand(bankingService, UUID.fromString(request.get(1)), request.get(2));
        } else {
            return null;
        }
    }
}
