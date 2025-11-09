package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.ShowHistoryCommand;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code ShowHistoryHandler} представляет обработчик команды отображения истории операций текущего счёта.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде отображения истории, он передается следующему обработчику в цепочке.
 * </p>
 */
public class ShowHistoryHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на отображение истории операций текущего счёта.
     * <p>
     * Если первый элемент запроса равен "show" и запрос содержит только один элемент (команда),
     * создается и возвращается команда {@link ShowHistoryCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link ShowHistoryCommand}, если запрос соответствует команде отображения истории, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "show")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 1) {
            return new ShowHistoryCommand(bankingService);
        } else {
            return null;
        }
    }
}
