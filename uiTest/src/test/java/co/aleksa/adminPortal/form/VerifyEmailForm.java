package co.aleksa.adminPortal.form;

import co.aleksa.AbstractComponent;
import co.aleksa.AbstractUITest;
import co.aleksa.util.OsValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class VerifyEmailForm extends AbstractComponent {

    public VerifyEmailForm(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void setEmailVerificationCode(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='emailVerificationCode']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToEmailVerificationCode() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='emailVerificationCode']"));
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
