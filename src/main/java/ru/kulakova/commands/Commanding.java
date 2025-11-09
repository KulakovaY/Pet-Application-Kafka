package ru.kulakova.commands;

import ru.kulakova.exceptions.NoAccountProvidedException;
import ru.kulakova.exceptions.NoSuchAccountException;
import ru.kulakova.exceptions.NotEnoughMoneyException;
import ru.kulakova.exceptions.WrongPasswordException;

/**
 * Интерфейс {@code Commanding}, определяющий метод для выполнения команды, связанной с банковскими операциями.
 * <p>
 * Этот интерфейс предоставляет метод {@code log}, который может выбрасывать исключения, связанные с отсутствием счёта, неверным паролем, отсутствием указанного счёта или недостатком средств на счёте.
 * </p>
 */
public interface Commanding {

    /**
     * Выполняет команду, связанную с банковской операцияей.
     *
     * @throws NoAccountProvidedException если текущий счёт не установлен
     * @throws WrongPasswordException если введен неверный пароль
     * @throws NoSuchAccountException если счёт с указанным идентификатором не найден
     * @throws NotEnoughMoneyException если на счёте недостаточно средств
     */
    void log() throws NoAccountProvidedException, WrongPasswordException, NoSuchAccountException, NotEnoughMoneyException;
}
