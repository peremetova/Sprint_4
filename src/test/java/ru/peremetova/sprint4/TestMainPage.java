package ru.peremetova.sprint4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.peremetova.sprint4.pages.MainPageSamokat;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class TestMainPage {

    private WebDriver driver;
    private int index;

    public TestMainPage(int index) {
        this.index = index;
    }

    @Parameterized.Parameters(name = "Кликаем на вопрос номер {0}")
    public static Object[][] getCredentials() {
        return new Object[][]{
                {0},
                {1},
                {2},
                {3},
                {4},
                {5},
                {6},
                {7},
        };
    }

    @Before
    public void connectToServer() {
        // драйвер для браузера Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        //driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testMainPageQuestions() {
        MainPageSamokat mainPageSamokat = new MainPageSamokat(driver);
        mainPageSamokat.clickCookieButton();
        mainPageSamokat.clickQuestion(index);
        boolean result = mainPageSamokat.isAnswerDisplayed(index);
        Assert.assertTrue("Ответ на вопрос: \"" + index + "\" не виден после клика на текст вопроса.", result);
    }

    @After
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}
