package ru.devray.day18.day18.pageobjects;

import org.junit.jupiter.api.Assertions;

public class TargetPage extends AbstractPage{
    public TargetPage() {
        super("");
        log.trace("Создал PageObject для целевой страницы.");
    }

    @Override
    public void open() {
        throw new UnsupportedOperationException("Невозможно открыть экземпляр Целевой страницы.");
    }

    @Override
    public void checkIsCurrentPage() {
        Assertions.assertTrue(!driver.getCurrentUrl().startsWith("https://yandex.ru"));
        log.info("Убедились, что находимся на одной из целевых страниц (не на яндексе).");
    }
}
