package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.WithdrawCommand;

import java.util.List;
import java.util.Objects;

/**
 * Класс {@code WithdrawHandler} представляет обработчик команды снятия средств с текущего счёта.
 * <p>
 * Этот класс наследуется от {@link BaseHandler} и обрабатывает запросы, связанные с снятием средств.
 * Если запрос не соответствует команде снятия, он передается следующему обработчику в цепочке.
 * </p>
 */
public class WithdrawHandler extends BaseHandler {

    /**
     * Метод обрабатывает запрос на снятие средств с текущего счёта.
     * <p>
     * Если первый элемент запроса равен "withdraw" и запрос содержит три элемента (команда, сумма, пароль),
     * создается и возвращается команда {@link WithdrawCommand}. В противном случае запрос передается следующему обработчику.
     * </p>
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда {@link WithdrawCommand}, если запрос соответствует команде снятия, иначе {@code null}
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        if (!Objects.equals(request.getFirst(), "withdraw")) {
            return _nextHandler.handle(request, bankingService);
        }

        if (request.size() == 3) {
            return new WithdrawCommand(bankingService, Double.parseDouble(request.get(1).replace(',', '.')), request.get(2));
        } else {
            return null;
        }
    }
}
