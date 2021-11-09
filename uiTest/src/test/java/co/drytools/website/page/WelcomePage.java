package co.drytools.website.page;

import co.drytools.AbstractPage;
import co.drytools.AbstractUITest;
import co.drytools.website.container.Footer;
import co.drytools.website.container.PublicHeader;
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

    public PublicHeader getHeader() {
        return new PublicHeader(getTest(), waitToBeVisible(By.xpath(".//*[@data-qa='header']")));
    }

    public void scrollToHeader() {
        final WebElement webElement = waitToBeVisible(By.xpath(".//*[@data-qa='header']"));
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
