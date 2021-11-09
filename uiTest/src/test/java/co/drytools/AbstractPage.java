package co.drytools;

import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

    private final AbstractUITest test;
    private final ChromeDriver driver;

    public AbstractPage(AbstractUITest test) {
        this.test = test;
        this.driver = AbstractUITest.getDriver();
    }

    public ChromeDriver getDriver() {
        return driver;
    }

    public AbstractUITest getTest() {
        return test;
    }

    public WebElement waitToBeClickable(WebElement element, int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout, 50);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitToBeClickable(WebElement element) {
        return waitToBeClickable(element, 1);
    }

    public WebElement waitToBeVisible(By locator, int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout, 50);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitToBeVisible(By locator) {
        return waitToBeVisible(locator, 1);
    }

    public boolean waitToBeInvisible(By locator, int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout, 50);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean waitToBeInvisible(By locator) {
        return waitToBeInvisible(locator, 2);
    }

    public Optional<Integer> waitForPage() {
        return Optional.empty();
    }

    public abstract String getPath();

    public abstract Optional<ExpectedCondition<WebElement>> getExpectation();
}
