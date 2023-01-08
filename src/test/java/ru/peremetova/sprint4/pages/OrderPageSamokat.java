package ru.peremetova.sprint4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

// Класс оформления заказа
public class OrderPageSamokat {

    private final WebDriver driver;
    // поле ввода "Имя"
    private final By nameField = By.xpath(".//*[@placeholder='* Имя']");
    // поле ввода "Фамилия"
    private final By surnameField = By.xpath(".//*[@placeholder='* Фамилия']");
    // поле ввода "Адрес: куда привезти заказ"
    private final By addressField = By.xpath(".//*[@placeholder='* Адрес: куда привезти заказ']");
    // поле выбора станции метро
    private final By metroField = By.className("select-search");
    // поле ввода "Телефон: на него позвонит курьер"
    private final By telephoneField = By.xpath(".//*[@placeholder='* Телефон: на него позвонит курьер']");
    // строка в выподающем меню для выбора станции метро
    private final By rowItemField = By.className("select-search__row");
    // кнопка "Далее"
    private final By nextButton = By.xpath("//*[text()='Далее']");
    // поле "Когда привезти самокат"
    private final By dateField = By.xpath(".//*[@placeholder='* Когда привезти самокат']");
    // поле выбора срока аренды
    private final By rentalPeriod = By.className("Dropdown-root");
    // выпадающее меню с выбором срока аренды
    private final By rentalRowItemField = By.className("Dropdown-option");
    // ячейки с датой 1 на выпадающем календаре
    private final By datePickerFirstDates = By.xpath("//*[@class='react-datepicker']//*[text()='1']");
    // кнопка заказать на форме "Про аренду"
    private final By orderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']//button[text()='Заказать']");
    // кнопка "Да" на форме подтверждения заказа
    private final By confirmYesButton = By.xpath("//div[@class='Order_Buttons__1xGrp']//button[text()='Да']");
    // заголовок формы "Про аренду"
    private final By rentalFormTitle = By.xpath("//*[text()='Про аренду']");
    // заголовок формы "Для кого самокат"
    private final By orderFormTitle = By.xpath("//*[text()='Для кого самокат']");
    // форма подтверждения
    private final By confirmFormTitle = By.xpath("//*[text()='Хотите оформить заказ?']");
    // заголовок окна успешного заказа
    private final By successFormTitle = By.xpath("//*[text()='Заказ оформлен']");

    public OrderPageSamokat(WebDriver driver) {
        this.driver = driver;
    }

    public void fillName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void fillSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void fillAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void fillMetro() {
        WebElement elementMetro = driver.findElement(metroField);
        elementMetro.click();
        List<WebElement> rows = elementMetro.findElements(rowItemField);
        rows.get(0).click();
    }

    public void fillTelephone(String telephone) {
        driver.findElement(telephoneField).sendKeys(telephone);
    }

    public void clickNextButton() {
        WebElement element = driver.findElement(nextButton);
        element.click();
    }

    public void clickOrderButton() {
        WebElement element = driver.findElement(orderButton);
        element.click();
    }

    public void clickConfirmYesButton() {
        WebElement element = driver.findElement(confirmYesButton);
        element.click();
    }

    public void fillDate() {
        driver.findElement(dateField).click();
        List<WebElement> firstDates = driver.findElements(datePickerFirstDates);
        // кликаем на последнюю дату в календаре у которой день 1,
        // то есть в январе это будет 1 февраля, в феврале 1 марта и т.д.
        firstDates.get(firstDates.size() - 1).click();
    }

    public void fillRental() {
        WebElement elementRental = driver.findElement(rentalPeriod);
        elementRental.click();
        List<WebElement> rows = elementRental.findElements(rentalRowItemField);
        rows.get(0).click();
    }

    public void waitForRentalForm() {
        new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))
                .until(driver -> driver.findElement(rentalFormTitle).isDisplayed());
    }

    public void fillOrderForm(String name, String surname, String address, String phone) {
        waitForOrderForm();
        fillName(name);
        fillSurname(surname);
        fillAddress(address);
        fillMetro();
        fillTelephone(phone);
    }

    public void waitForOrderForm() {
        new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))
                .until(driver -> driver.findElement(orderFormTitle).isDisplayed());
    }

    public void waitForConfirmForm() {
        new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))
                .until(driver -> driver.findElement(confirmFormTitle).isDisplayed());
    }

    public void waitForSuccess() {
        new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))
                .until(driver -> driver.findElement(successFormTitle).isDisplayed());
    }

    public void fillRentalForm() {
        waitForRentalForm();
        fillRental();
        fillDate();
    }

    public void processOrder(String name, String surname, String address, String phone) {
        fillOrderForm(name, surname, address, phone);
        clickNextButton();
        fillRentalForm();
        clickOrderButton();
        waitForConfirmForm();
        clickConfirmYesButton();
        waitForSuccess();
    }
}
