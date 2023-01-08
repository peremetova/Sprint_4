package ru.peremetova.sprint4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.assertTrue;

// Класс главной страницы
public class MainPageSamokat {

    private final WebDriver driver;
    // Заголовок вопроса
    private final By questionItem = By.xpath(".//*[@class='accordion__item']");
    // Ответ на вопрос
    private final By answerItem = By.xpath(".//*[@class='accordion__panel']");
    // Кнопка "Заказать" сверху
    private final By buttonOrderTop = By.className("Button_Button__ra12g");
    // Кнопка "Заказать" внизу
    private final By buttonOrderBottom = By.xpath(".//div[@class='Home_FinishButton__1_cWm']//button");
    // Кнопка куки
    private final By buttonCookie = By.xpath(".//button[@class='App_CookieButton__3cvqF']");

    public MainPageSamokat(WebDriver driver) {
        this.driver = driver;
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickCookieButton() {
        driver.findElement(buttonCookie).click();
    }

    /**
     * Кликаем на каждый вопрос и проверяем что ответ показывается
     */
    public void clickQuestion(int index) {
        // нашли список вопросов
        List<WebElement> questions = driver.findElements(questionItem);
        System.out.println("найдено " + questions.size() + " вопросов");

        WebElement question = questions.get(index);
        System.out.println("Кликаем на вопрос: " + question.getText());
        scrollToElement(question);
        new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))
                .until(ExpectedConditions.visibilityOf(question));
        question.click();
        WebElement answer = question.findElement(answerItem);
        System.out.println("Ответ: " + answer.getText());
        System.out.println("Is Displayed: " + answer.isDisplayed());
        assertTrue("Ответ на вопрос: \"" + question.getText() + "\" не виден после клика на текст вопроса.", answer.isDisplayed());
    }

    public void clickTopOrderButton() {
        WebElement button = driver.findElement(buttonOrderTop);
        System.out.println("Найдена кнопка: " + button.getText());
        button.click();
    }

    public void clickBottomOrderButton() {
        WebElement button = driver.findElement(buttonOrderBottom);
        System.out.println("Найдена кнопка: " + button.getText());
        scrollToElement(button);
        button.click();
    }
}
