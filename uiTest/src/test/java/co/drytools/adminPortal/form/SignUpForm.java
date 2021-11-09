package co.drytools.adminPortal.form;

import co.drytools.AbstractComponent;
import co.drytools.AbstractUITest;
import co.drytools.util.OsValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class SignUpForm extends AbstractComponent {

    public SignUpForm(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void setFirstName(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='firstName']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToFirstName() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='firstName']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setLastName(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='lastName']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToLastName() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='lastName']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setBirthdate(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='birthdate']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(50);
    }

    public void scrollToBirthdate() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='birthdate']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setActive() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='active']"));
        webElement.click();
        AbstractUITest.sleep(300);
    }

    public void scrollToActive() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='active']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setEmail(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='email']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToEmail() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='email']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setPassword(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='password']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToPassword() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='password']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void submitSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='submit']"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void scrollToSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='submit']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
