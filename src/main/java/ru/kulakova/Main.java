package ru.kulakova;

import ru.kulakova.exceptions.NoAccountProvidedException;
import ru.kulakova.exceptions.NoSuchAccountException;
import ru.kulakova.exceptions.NotEnoughMoneyException;
import ru.kulakova.exceptions.WrongPasswordException;
import ru.kulakova.handlers.*;
import ru.kulakova.runners.ConsoleRunner;

/**
 * Класс {@code Main} является точкой входа в программу.
 * <p>
 * Этот класс создает цепочку обработчиков ({@link Handleable}) для обработки команд,
 * передает ее в {@link ConsoleRunner} и запускает консольный интерфейс.
 * </p>
 */
public class Main {

    /**
     * Точка входа в программу.
     * <p>
     * Создает цепочку обработчиков для команд, передает ее в {@link ConsoleRunner}
     * и запускает выполнение консольного интерфейса.
     * </p>
     *
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     */
    public static void main() throws NoSuchAccountException, NotEnoughMoneyException, NoAccountProvidedException, WrongPasswordException {

        Handleable handler = new CreateHandler().
                addNext(new GoToHandler()).
                addNext(new TopUpHandler()).
                addNext(new WithdrawHandler()).
                addNext(new CheckBalanceHandler()).
                addNext(new ChangePasswordHandler()).
                addNext(new ExitAccountHandler()).
                addNext(new ShowHistoryHandler());

        var runner = new ConsoleRunner(handler);
        runner.run();
    }
}