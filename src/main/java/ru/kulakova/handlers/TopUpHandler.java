package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.TopUpCommand;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code TopUpHandler} представляет обработчик команды пополнения баланса текущего счёта.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде пополнения, он передается следующему обработчику в цепочке.
 * </p>
 */
public class TopUpHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на пополнение баланса текущего счёта.
     * <p>
     * Если первый элемент запроса равен "topup" и запрос содержит два элемента (команда и сумма),
     * создается и возвращается команда {@link TopUpCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link TopUpCommand}, если запрос соответствует команде пополнения, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "topup")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 2) {
            return new TopUpCommand(bankingService, Double.parseDouble(request.get(1).replace(',', '.')));
        } else {
            return null;
        }
    }
}
