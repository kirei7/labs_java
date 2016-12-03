package ua.vntu.lab11.jdbc;

// виключення, яке ми будемо кидати, якщо користувач намагається зберегти уже збережений в БД автомобіль
public class RowAlreadyExistsException extends RuntimeException {
}
