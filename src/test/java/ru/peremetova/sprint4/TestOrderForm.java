package ru.peremetova.sprint4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.peremetova.sprint4.pages.MainPageSamokat;
import ru.peremetova.sprint4.pages.OrderPageSamokat;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class TestOrderForm {

    private WebDriver driver;

    private String name;
    private String surname;
    private String address;
    private String phone;

    public TestOrderForm(String name,
                         String surname,
                         String address,
                         String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials(){
        return new Object[][]{
                {"Иван", "Иванов", "ул. Рылеева, д. 19", "89156350000"},
                {"Евгения", "Трубакова", "ул. Горького, д. 101", "80000000000"},
        };
    }

    @Before
    public void connectToServer() {
        // драйвер для браузера Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        //driver = new ChromeDriver(options);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testScooterOrder() {
        MainPageSamokat mainPageSamokat = new MainPageSamokat(driver);
        mainPageSamokat.clickTopOrderButton();

        OrderPageSamokat orderPageSamokat = new OrderPageSamokat(driver);
        orderPageSamokat.waitForOrderForm();
        orderPageSamokat.fillName(name);
        orderPageSamokat.fillSurname(surname);
        orderPageSamokat.fillAddress(address);
        orderPageSamokat.fillMetro();
        orderPageSamokat.fillTelephone(phone);

        orderPageSamokat.clickNextButton();
        orderPageSamokat.waitForRentalForm();
        orderPageSamokat.fillDate();
        orderPageSamokat.fillRental();
        orderPageSamokat.clickOrderButton();
        orderPageSamokat.waitForConfirmForm();
        orderPageSamokat.clickConfirmYesButton();
        orderPageSamokat.waitForSuccess();
    }

    @After
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}
