package co.drytools.website.page;

import co.drytools.AbstractPage;
import co.drytools.AbstractUITest;
import co.drytools.website.form.ResetPasswordForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ResetPasswordPage extends AbstractPage {

    private String resetPasswordCode;

    public ResetPasswordPage(AbstractUITest test) {
        super(test);
    }

    public ResetPasswordPage(String resetPasswordCode, AbstractUITest test) {
        super(test);
        this.resetPasswordCode = resetPasswordCode;
    }

    @Override
    public String getPath() {
        return "/reset-password" + "/" + resetPasswordCode;
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
    }

    public ResetPasswordForm getResetPasswordForm() {
        return new ResetPasswordForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='resetPasswordForm']")));
    }

    public void scrollToResetPasswordForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='resetPasswordForm']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
