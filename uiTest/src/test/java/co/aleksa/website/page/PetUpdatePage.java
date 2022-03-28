package co.aleksa.website.page;

import co.aleksa.AbstractPage;
import co.aleksa.AbstractUITest;
import co.aleksa.website.container.Footer;
import co.aleksa.website.container.PrivateHeader;
import co.aleksa.website.form.PetUpdateForm;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class PetUpdatePage extends AbstractPage {

    private Integer id;

    public PetUpdatePage(AbstractUITest test) {
        super(test);
    }

    public PetUpdatePage(Integer id, AbstractUITest test) {
        super(test);
        this.id = id;
    }

    @Override
    public String getPath() {
        return "/private/pet" + "/" + id;
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

    public PetUpdateForm getForm() {
        return new PetUpdateForm(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='form']")));
    }

    public void scrollToForm() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='form']"));
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
