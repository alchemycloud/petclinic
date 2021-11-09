package co.drytools.website.list;

import co.drytools.AbstractComponent;
import co.drytools.AbstractUITest;
import co.drytools.util.OsValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class PetsList extends AbstractComponent {

    public PetsList(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void setId(Integer value, Integer order) {
        final WebElement webElement = getElement().findElements(By.xpath(".//*[@data-qa='id']")).get(order);
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value.toString());
        AbstractUITest.sleep(100);
    }

    public void scrollToId(Integer order) {
        final WebElement webElement = getElement().findElements(By.xpath(".//*[@data-qa='id']")).get(order);
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setName(String value, Integer order) {
        final WebElement webElement = getElement().findElements(By.xpath(".//*[@data-qa='name']")).get(order);
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToName(Integer order) {
        final WebElement webElement = getElement().findElements(By.xpath(".//*[@data-qa='name']")).get(order);
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void setUserLastName(String value, Integer order) {
        final WebElement webElement = getElement().findElements(By.xpath(".//*[@data-qa='userLastName']")).get(order);
        if (OsValidator.isMac()) {
            webElement.sendKeys(Keys.COMMAND + "a");
        } else {
            webElement.sendKeys(Keys.CONTROL + "a");
        }
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(value);
        AbstractUITest.sleep(100);
    }

    public void scrollToUserLastName(Integer order) {
        final WebElement webElement = getElement().findElements(By.xpath(".//*[@data-qa='userLastName']")).get(order);
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }

    public void selectItemsPerPage(String itemsPerPage) {
        final WebElement webElement = waitToBeVisible(By.xpath("//mat-option/span[contains(.,'" + itemsPerPage + "')]"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void clickPaginator() {
        final WebElement webElement = waitToBeVisible(By.cssSelector(".mat-select-value"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void pagerArrowRight() {
        final WebElement paginatorNextPage = waitToBeVisible(By.className("mat-paginator-navigation-next"));
        waitToBeClickable(paginatorNextPage).click();
        AbstractUITest.sleep(200);
    }

    public void pagerArrowLeft() {
        final WebElement paginatorPreviousPage = waitToBeVisible(By.className("mat-paginator-navigation-previous"));
        waitToBeClickable(paginatorPreviousPage).click();
        AbstractUITest.sleep(200);
    }
}
