package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.CheckBalanceCommand;
import ru.kulakova.commands.Commanding;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code CheckBalanceHandler} представляет обработчик команды проверки баланса счёта.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде проверки баланса, он передается следующему обработчику в цепочке.
 * </p>
 */
public class CheckBalanceHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на проверку баланса.
     * <p>
     * Если первый элемент запроса равен "check" и запрос содержит только один элемент (команда),
     * создается и возвращается команда {@link CheckBalanceCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link CheckBalanceCommand}, если запрос соответствует команде проверки баланса, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "check")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 1) {
            return new CheckBalanceCommand(bankingService);
        } else {
            return null;
        }
    }
}
