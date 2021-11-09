package co.drytools.adminPortal.page;

import co.drytools.AbstractPage;
import co.drytools.AbstractUITest;
import co.drytools.adminPortal.form.VerifyEmailForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class VerifyEmailPage extends AbstractPage {

    private String emailVerificationCode;

    public VerifyEmailPage(AbstractUITest test) {
        super(test);
    }

    public VerifyEmailPage(String emailVerificationCode, AbstractUITest test) {
        super(test);
        this.emailVerificationCode = emailVerificationCode;
    }

    @Override
    public String getPath() {
        return "/verify-email" + "/" + emailVerificationCode;
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
    }

    public VerifyEmailForm getVerifyEmailForm() {
        return new VerifyEmailForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='verifyEmailForm']")));
    }

    public void scrollToVerifyEmailForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='verifyEmailForm']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
