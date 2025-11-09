package ru.kulakova.handlers;

import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.commands.Commanding;

import java.util.List;

/**
 * Интерфейс {@code Handleable} определяет методы для обработки запросов в цепочке обязанностей.
 */
public interface Handleable {

    /**
     * Метод добавляет следующий обработчик в цепочку обязанностей.
     *
     * @param next следующий обработчик
     * @return текущий обработчик для поддержки цепочки вызовов
     */
    Handleable addNext(Handleable next);

    /**
     * Метод обрабатывает запрос. Если текущий обработчик не может обработать запрос, он передает его следующему обработчику в цепочке.
     *
     * @param request список строк, представляющих запрос
     * @param bankingService сервис для выполнения банковских операций
     * @return команда, соответствующая запросу, или {@code null}, если запрос не может быть обработан
     */
    Commanding handle(List<String> request, BankingService bankingService);

}
