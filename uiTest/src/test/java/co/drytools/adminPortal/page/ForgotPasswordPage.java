package co.drytools.adminPortal.page;

import co.drytools.AbstractPage;
import co.drytools.AbstractUITest;
import co.drytools.adminPortal.form.ForgotPasswordForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ForgotPasswordPage extends AbstractPage {

    public ForgotPasswordPage(AbstractUITest test) {
        super(test);
    }

    @Override
    public String getPath() {
        return "/forgot-password";
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
    }

    public ForgotPasswordForm getForgotPasswordForm() {
        return new ForgotPasswordForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='forgotPasswordForm']")));
    }

    public void scrollToForgotPasswordForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='forgotPasswordForm']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
