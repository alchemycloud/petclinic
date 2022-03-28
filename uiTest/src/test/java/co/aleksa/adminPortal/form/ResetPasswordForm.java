package co.aleksa.adminPortal.form;

import co.aleksa.AbstractComponent;
import co.aleksa.AbstractUITest;
import co.aleksa.util.OsValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ResetPasswordForm extends AbstractComponent {

    public ResetPasswordForm(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void setResetPasswordCode(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='resetPasswordCode']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToResetPasswordCode() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='resetPasswordCode']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setNewPassword(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='newPassword']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToNewPassword() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='newPassword']"));
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
