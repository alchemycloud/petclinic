package co.aleksa.website.form;

import co.aleksa.AbstractComponent;
import co.aleksa.AbstractUITest;
import co.aleksa.util.OsValidator;
import co.aleksa.website.dropdown.PetTypeDropDown;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class PetUpdateForm extends AbstractComponent {

    public PetUpdateForm(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void setOwnerId(Integer value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='ownerId']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value.toString());
        AbstractUITest.sleep(100);
    }

    public void scrollToOwnerId() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='ownerId']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setName(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='name']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToName() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='name']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setBirthday(String value) {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='birthday']"));
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(50);
    }

    public void scrollToBirthday() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='birthday']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public PetTypeDropDown getPetType() {
        return new PetTypeDropDown(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='petType']")));
    }

    public void scrollToPetType() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='petType']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setVaccinated() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='vaccinated']"));
        webElement.click();
        AbstractUITest.sleep(300);
    }

    public void scrollToVaccinated() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='vaccinated']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void deleteButtonSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='deleteButton']"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void scrollToDeleteButton() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='deleteButton']"));
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
}
