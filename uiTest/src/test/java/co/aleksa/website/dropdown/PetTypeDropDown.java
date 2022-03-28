package co.aleksa.website.dropdown;

import co.aleksa.AbstractComponent;
import co.aleksa.AbstractUITest;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class PetTypeDropDown extends AbstractComponent {

    public PetTypeDropDown(AbstractUITest test, WebElement element) {
        super(test, element);
    }

    public void setValue(String value) {
        getElement().click();
        final List<WebElement> options = getDriver().findElements(By.xpath("//mat-option"));
        for (WebElement option : options) {
            final String currentValue = option.getText();
            if (currentValue.equals(value)) {
                option.click();
                AbstractUITest.sleep(200);
                break;
            }
        }
    }

    public void close() {
        final WebElement webElement = waitToBeVisible(By.xpath("//*[@data-qa='dropdownItems']"));
        webElement.sendKeys(Keys.ESCAPE);
        waitToBeInvisible(By.xpath("//*[@class='cdk-overlay-pane']"));
    }

    public void open() {
        waitToBeClickable(getElement()).click();
        AbstractUITest.sleep(50);
    }
}
