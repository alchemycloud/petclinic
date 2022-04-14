package co.aleksa.website.page;

import co.aleksa.AbstractPage;
import co.aleksa.AbstractUITest;
import co.aleksa.website.container.Footer;
import co.aleksa.website.container.PrivateHeader;
import co.aleksa.website.form.PetCreateForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class PetCreatePage extends AbstractPage {

    public PetCreatePage(AbstractUITest test) {
        super(test);
    }

    @Override
    public String getPath() {
        return "/private/pet/new";
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
    }

    public PrivateHeader getHeader() {
        return new PrivateHeader(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='header']")));
    }

    public void scrollToHeader() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='header']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public PetCreateForm getPetCreateForm() {
        return new PetCreateForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='petCreateForm']")));
    }

    public void scrollToPetCreateForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='petCreateForm']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public Footer getFooter() {
        return new Footer(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='footer']")));
    }

    public void scrollToFooter() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='footer']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
