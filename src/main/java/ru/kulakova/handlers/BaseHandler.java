package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;

import java.util.List;

/**
 * Абстрактный класс {@code BaseHandler} представляет базовый обработчик запросов в цепочке обязанностей.
 * <p>
 * Этот класс реализует интерфейс {@link Handleable}.
 * </p>
 */
public abstract class BaseHandler implements Handleable {

    /**
     * Следующий обработчик в цепочке обязанностей.
     */
    public Handleable _nextHandler;

    /**
     * Метод добавляет следующий обработчик в цепочку обязанностей.
     *
     * @param nextHandler следующий обработчик
     * @return текущий обработчик для поддержки цепочки вызовов
     */
    public final Handleable addNext(Handleable nextHandler) {
        if (_nextHandler == null) {
            _nextHandler = nextHandler;
        } else {
            _nextHandler.addNext(nextHandler);
        }

        return this;
    }

    /**
     * Обрабатывает запрос. Если текущий обработчик не может обработать запрос, он передает его следующему обработчику в цепочке.
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда, соответствующая запросу, или {@code null}, если запрос не может быть обработан
     */
    @Override
    public Commanding handle(List<String> request, BankingService bankingService) {
        return null;
    }
}
