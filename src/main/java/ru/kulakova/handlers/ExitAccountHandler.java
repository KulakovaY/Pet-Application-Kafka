package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.ExitAccountCommand;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code ExitAccountHandler} представляет обработчик команды выхода из текущего банковского счёта.
 * <p>
 * Этот класс наследуется от {@link BaseHandler}.
 * Если запрос не соответствует команде выхода, он передается следующему обработчику в цепочке.
 * </p>
 */
public class ExitAccountHandler extends BaseHandler{

    /**
     * Метод обрабатывает запрос на выход из текущего банковского счёта.
     * <p>
     * Если первый элемент запроса равен "exit" и запрос содержит только один элемент (команда),
     * создается и возвращается команда {@link ExitAccountCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link ExitAccountCommand}, если запрос соответствует команде выхода, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "exit")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 1) {
            return new ExitAccountCommand(bankingService);
        } else {
            return null;
        }
    }
}
