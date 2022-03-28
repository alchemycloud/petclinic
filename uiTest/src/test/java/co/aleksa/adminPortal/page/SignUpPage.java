package co.aleksa.adminPortal.page;

import co.aleksa.AbstractPage;
import co.aleksa.AbstractUITest;
import co.aleksa.adminPortal.form.SignUpForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class SignUpPage extends AbstractPage {

    public SignUpPage(AbstractUITest test) {
        super(test);
    }

    @Override
    public String getPath() {
        return "/sign-up";
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
    }

    public SignUpForm getSignUpForm() {
        return new SignUpForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='signUpForm']")));
    }

    public void scrollToSignUpForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signUpForm']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public co.aleksa.adminPortal.page.SignInPage signInClick() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signIn']"));
        waitToBeClickable(webElement).click();
        return getTest().waitForAdminPortalSignInPage();
    }

    public void scrollToSignIn() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signIn']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
