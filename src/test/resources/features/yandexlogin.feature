Feature: Yandex search and search results navigation
  Scenario: Search car model and check one search result
    Given use uses ChromeBrowser on Windows
    When the user opens yandex main page
    Then check is navigation section buttons and logos are present

#    When user enters search query 'porsche 356B 1:18 model'
#    And user click search button
#    Then search page contains at least '2' results

#  1. Открыть страницу yandex.ru
#  2. Проверить, отображаются ли над поисковой строкой кнопки "Маркет", "Видео", "Картинки", "Карты" (проверяется наличие элементов логотип + текст).
#  3. Ввести в поле поиска запрос "porsche 356B 1:18 model"
#  4. Нажать кнопку найти
#  5. Проверить, что по данному поисковому запросу получено как минимум два результата
#  6. Проверить, что по данному поисковому запросу есть как минимум 3 страницы результатов
#  7. Перейти на 3-ю страницу результатов
#  8. Проверить, что мы все еще находимся в поисковике Яндекс (URL)
#  9. Открыть 2-й поисковый результат в выдаче на данной странице
#  10. Убедиться что произошел переход со страницы поисковика на целевую страницу