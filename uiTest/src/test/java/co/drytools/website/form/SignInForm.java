package co.drytools.website.form;

import co.drytools.AbstractComponent;
import co.drytools.AbstractUITest;
import co.drytools.util.OsValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class SignInForm extends AbstractComponent {

    public SignInForm(AbstractUITest test, WebElement element) {
        super(test, element);
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

    public co.drytools.website.page.ForgotPasswordPage forgotPasswordClick() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='forgotPassword']"));
        waitToBeClickable(webElement).click();
        return getTest().waitForWebsiteForgotPasswordPage();
    }

    public void scrollToForgotPassword() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='forgotPassword']"));
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
