package co.aleksa.website.page;

import co.aleksa.AbstractPage;
import co.aleksa.AbstractUITest;
import co.aleksa.website.container.Footer;
import co.aleksa.website.container.PrivateHeader;
import co.aleksa.website.list.PetsList;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class PetsPage extends AbstractPage {

    public PetsPage(AbstractUITest test) {
        super(test);
    }

    @Override
    public String getPath() {
        return "/private/pets";
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

    public PetsList getPetsList() {
        return new PetsList(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='petsList']")));
    }

    public void scrollToPetsList() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='petsList']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void addPetSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='addPet']"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void scrollToAddPet() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='addPet']"));
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
