package co.drytools.website.container;

import co.drytools.AbstractComponent;
import co.drytools.AbstractUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Footer extends AbstractComponent {

    public Footer(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void aboutSubmit() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='about']"));
        waitToBeClickable(webElement).click();
        AbstractUITest.sleep(200);
    }

    public void scrollToAbout() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='about']"));
        getTest().scrollToElement(webElement, false);
        AbstractUITest.sleep(100);
    }
}
