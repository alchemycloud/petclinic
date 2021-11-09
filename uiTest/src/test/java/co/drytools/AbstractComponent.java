package co.drytools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractComponent {

    private final AbstractUITest test;
    private final ChromeDriver driver;
    private final WebElement element;

    public AbstractComponent(AbstractUITest test, WebElement element) {
        this.element = element;
        this.test = test;
        this.driver = AbstractUITest.getDriver();
    }

    public WebElement getElement() {
        return element;
    }

    public ChromeDriver getDriver() {
        return driver;
    }

    public AbstractUITest getTest() {
        return test;
    }

    public WebElement waitToBeClickable(WebElement element, int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitToBeClickable(WebElement element) {
        return waitToBeClickable(element, 1);
    }

    public WebElement waitToBeVisible(By locator, int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout, 50);
        final WebElement locatedElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, locator));
        return wait.until(ExpectedConditions.visibilityOf(locatedElement));
    }

    public WebElement waitToBeVisible(By locator) {
        return waitToBeVisible(locator, 1);
    }

    public boolean waitToBeInvisible(By locator, int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout, 50);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean waitToBeInvisible(By locator) {
        return waitToBeInvisible(locator, 1);
    }
}
