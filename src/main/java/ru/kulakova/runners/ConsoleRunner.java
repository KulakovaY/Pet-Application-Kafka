package ru.kulakova.runners;

import ru.kulakova.bankAccounts.BankAccountService;
import ru.kulakova.bankAccounts.BankingService;
import ru.kulakova.bankAccounts.CurrentBankAccount;
import ru.kulakova.commands.Commanding;
import ru.kulakova.exceptions.*;
import ru.kulakova.handlers.Handleable;
import ru.kulakova.writers.ConsoleWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Класс {@code ConsoleRunner} представляет консольный интерфейс для взаимодействия с банковской системой.
 * <p>
 * Этот класс реализует интерфейс {@link Runnable} и предоставляет возможность выполнения команд, введенных пользователем через консоль. Он использует цепочку обработчиков ({@link Handleable}) для обработки запросов и выполнения соответствующих команд.
 * </p>
 */
public class ConsoleRunner implements Runnable {

    private Handleable _handler;

    public ConsoleRunner(Handleable handler) {
        _handler = handler;
    }

    /**
     * Метод запускает консольный интерфейс для взаимодействия с банковской системой.
     * <p>
     * Пользователь может вводить команды, которые будут обрабатываться и выполняться.
     * Для завершения работы программы необходимо ввести "OK".
     * </p>
     *
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     */
    @Override
    public void run() throws NoSuchAccountException, NotEnoughMoneyException, NoAccountProvidedException, WrongPasswordException {
        System.out.println("Type OK to exit the program");
        Scanner scanner = new Scanner(System.in);
        BankingService bankingService = new BankAccountService(new CurrentBankAccount(), new ConsoleWriter());

        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("ok")) break;

            String[] args = line.split(" ");
            List<String> request = Arrays.asList(args);
            if (request.isEmpty()) continue;

            Commanding outputCommand = _handler.handle(request, bankingService);
            if(outputCommand != null) outputCommand.log();
        }
    }
}
