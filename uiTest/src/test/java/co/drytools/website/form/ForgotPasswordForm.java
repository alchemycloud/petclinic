package co.drytools.website.form;

import co.drytools.AbstractComponent;
import co.drytools.AbstractUITest;
import co.drytools.util.OsValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ForgotPasswordForm extends AbstractComponent {

    public ForgotPasswordForm(AbstractUITest test, WebElement element) {
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

    public void forgotPasswordFormConfirmCloseSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath("//*[@data-qa='close']"));
        waitToBeClickable(webElement).click();
        waitToBeInvisible(By.xpath("//*[@class='cdk-overlay-backdrop']"));
    }

    public void forgotPasswordFormConfirmCancelSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath("//*[@data-qa='cancel']"));
        waitToBeClickable(webElement).click();
        waitToBeInvisible(By.xpath("//*[@class='cdk-overlay-backdrop']"));
    }
}
