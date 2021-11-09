package co.drytools.website.container;

import co.drytools.AbstractComponent;
import co.drytools.AbstractUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PublicHeader extends AbstractComponent {

    public PublicHeader(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void signInButtonSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signInButton']"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void scrollToSignInButton() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signInButton']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void signUpButtonSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signUpButton']"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void scrollToSignUpButton() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signUpButton']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
