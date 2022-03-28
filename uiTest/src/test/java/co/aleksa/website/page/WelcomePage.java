package co.aleksa.website.page;

import co.aleksa.AbstractPage;
import co.aleksa.AbstractUITest;
import co.aleksa.website.container.Footer;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WelcomePage extends AbstractPage {

    public WelcomePage(AbstractUITest test) {
        super(test);
    }

    @Override
    public String getPath() {
        return "/welcome-page";
    }

    @Override
    public Optional<ExpectedCondition<WebElement>> getExpectation() {
        return Optional.empty();
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
