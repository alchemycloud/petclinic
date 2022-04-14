package co.aleksa.adminPortal.page;

import co.aleksa.AbstractPage;
import co.aleksa.AbstractUITest;
import co.aleksa.adminPortal.form.SignInForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class SignInPage extends AbstractPage {

    public SignInPage(AbstractUITest test) {
        super(test);
    }

    @Override
    public String getPath() {
        return "/sign-in";
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
    }

    public SignInForm getSignInForm() {
        return new SignInForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='signInForm']")));
    }

    public void scrollToSignInForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signInForm']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public co.aleksa.adminPortal.page.SignUpPage signUpClick() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signUp']"));
        waitToBeClickable(webElement).click();
        return getTest().waitForAdminPortalSignUpPage();
    }

    public void scrollToSignUp() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='signUp']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
