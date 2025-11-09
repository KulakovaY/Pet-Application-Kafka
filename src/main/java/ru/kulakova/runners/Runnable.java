package ru.kulakova.runners;

import ru.kulakova.exceptions.*;

/**
 * Интерфейс {@code Runnable} определяет метод для выполнения операций, связанных с банковской системой.
 */
public interface Runnable {

    /**
     * Метод выполняет операции, связанные с банковской системой.
     *
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     */
    void run() throws NoSuchAccountException, NotEnoughMoneyException, NoAccountProvidedException, WrongPasswordException;
}
