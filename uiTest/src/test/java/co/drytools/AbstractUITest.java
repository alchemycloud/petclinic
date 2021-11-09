package co.drytools;

import co.drytools.util.ImageDifference;
import co.drytools.util.OsValidator;
import co.drytools.util.ScriptRunner;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.ImageMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class AbstractUITest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractUITest.class);
    protected TestInfo info;

    private static String WEBSITE_URL;
    private static String ADMINPORTAL_URL;
    private static String BACKEND_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static String DB_URL;
    private static String BACKEND_DATABASE_NAME;
    private static final Boolean HEADLESS = true;

    protected static String WEBSITE_SETUP_DB = "sql/website/";
    protected static String ADMINPORTAL_SETUP_DB = "sql/adminPortal/";
    private final String databaseDumpPath;

    private static ChromeDriver driver = null;
    public static ChromeDriverService chromeDriverService;
    public static final Integer DIALOG_POPUP_DELAY = 500;

    private static final boolean OVERRIDE_SNAPSHOTS = false;
    private static final double DEVIATION = 30;

    private static final String PNG = "png";
    private static final String DOT_PNG = "." + "png";

    protected final String screenShotsFolder;
    private Integer screenShotsCount;
    private Integer failedScreenShotsCount;

    protected AbstractUITest(String screenShotsFolder, String databaseDumpPath) {
        this.screenShotsFolder = screenShotsFolder;
        this.databaseDumpPath = databaseDumpPath;
    }

    @BeforeAll
    public static void setupDriver() throws IOException {

        if (driver != null) {
            return;
        }
        LOG.debug("Setup driver");

        final Properties props = new Properties();
        try (final InputStream is = ClassLoader.getSystemResourceAsStream("test.properties")) {
            assert is != null;
            props.load(is);
        }
        WEBSITE_URL = props.getProperty("websiteUrl");
        ADMINPORTAL_URL = props.getProperty("adminPortalUrl");
        BACKEND_URL = props.getProperty("backendUrl");
        DB_URL = props.getProperty("databaseUrl");
        DB_USER = props.getProperty("databaseUser");
        DB_PASSWORD = props.getProperty("databasePassword");

        BACKEND_DATABASE_NAME = props.getProperty("backendDatabaseName");

        LOG.debug("Setup driver create preferences");
        final Map<String, Object> preferences = new Hashtable<>();
        preferences.put("browser.enable_spellchecking", "false");
        preferences.put("browser.enable_autospellcorrect", "false");
        preferences.put("spellcheck.use_spelling_service", "");
        preferences.put("spellcheck.dictionary", "");
        preferences.put("translate.enabled", "false");
        // cookies
        preferences.put("profile.managed_default_content_settings.cookies", 1);
        preferences.put("security.cookie_behavior", 0);
        preferences.put("profile.default_content_setting_values.cookies", 1);
        preferences.put("profile.default_content_settings.cookies", 1);
        preferences.put("profile.block_third_party_cookies", "false");

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", preferences);
        chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--proxy-server='direct://'");
        chromeOptions.addArguments("--force-device-scale-factor=1");
        chromeOptions.addArguments("--proxy-bypass-list=*");
        chromeOptions.addArguments("--proxy-server=");
        chromeOptions.addArguments("--window-size=1920,1080");

        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--aggressive-cache-discard");
        chromeOptions.addArguments("--disable-cache");
        chromeOptions.addArguments("--disable-application-cache");
        chromeOptions.addArguments("--disable-offline-load-stale-cache");
        chromeOptions.addArguments("--disk-cache-size=0");
        chromeOptions.addArguments("--dns-prefetch-disable");
        chromeOptions.addArguments("--disable-browser-side-navigation");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        if (HEADLESS) {
            LOG.debug("Starting in headless mode");
            chromeOptions.addArguments("--headless");
        }

        chromeDriverService = new ChromeDriverService.Builder().withWhitelistedIps("").withSilent(true).build();

        LOG.debug("Setup driver starting chrome: " + preferences);
        driver = new ChromeDriver(chromeDriverService, chromeOptions);
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    AbstractUITest.driver.quit();
                                    AbstractUITest.chromeDriverService.stop();
                                }));
    }

    private static void logMemoryStatus() {
        final long mb = 1024 * 1024;
        final long freeMemory = Runtime.getRuntime().freeMemory() / mb;
        final long maxMemory = Runtime.getRuntime().maxMemory() / mb;
        final long totalMemory = Runtime.getRuntime().totalMemory() / mb;
        System.out.println("Free memory: " + freeMemory + " mb. Max memory: " + maxMemory + " mb. Total memory: " + totalMemory + " mb.");
    }

    @BeforeEach
    public void setup() throws IOException {
        final File screenshotsFolder = new File(screenShotsFolder);

        logMemoryStatus();
        closeAllBrowserTabs();

        if (screenshotsFolder.exists() && OVERRIDE_SNAPSHOTS) {
            LOG.debug("Delete the screenshot folder: " + screenshotsFolder);
            delete(screenshotsFolder);
        }
        if (!screenshotsFolder.exists()) {
            LOG.debug("Create the screenshot folder" + screenshotsFolder);
            screenshotsFolder.mkdirs();
        }
        screenShotsCount = 0;
        failedScreenShotsCount = 0;

        requestDbRedeploy(BACKEND_DATABASE_NAME, databaseDumpPath + "backendSetupDB.sql");

        clearDriver();
        maximizeWindow();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterEach
    public void after() {
        if (failedScreenShotsCount > 0) {
            Assertions.fail("Check failed screenshots");
        }
        isTestBroken();
        closeAllBrowserTabs();

        LOG.debug("End test -> " + info.getTestMethod().get().getName());
    }

    protected void maximizeWindow() {
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    private void closeAllBrowserTabs() {
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        for (int i = 1; i < tabs.size(); i++) {
            getDriver().switchTo().window(tabs.get(i));
            driver.getLocalStorage().clear();
            getDriver().close();
        }
        getDriver().switchTo().window(tabs.get(0));
        try {
            driver.getLocalStorage().clear();
        } catch (Exception e) {
            LOG.error("Cleaning local storage error: ", e);
        }
    }

    private void removeAllocationIndicatorsForScreenshots() {
        List<WebElement> allocationIndicators = getDriver().findElementsByClassName("c-allocation-indicator-pulse");
        JavascriptExecutor javascriptExecutor = getDriver();
        if (!allocationIndicators.isEmpty()) {
            for (WebElement allocationIndicator : allocationIndicators) {
                javascriptExecutor.executeScript("arguments[0].classList.remove('c-allocation-indicator-pulse')", allocationIndicator);
            }
        }
    }

    private void isTestBroken() {
        AShot aShot;
        if (OsValidator.isMac()) {
            aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000));
        } else {
            aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100));
        }

        aShot.coordsProvider(new WebDriverCoordsProvider());

        final Screenshot screenshot = aShot.takeScreenshot(driver);

        final String testName = info.getTestMethod().get().getName() + "_" + screenShotsCount;

        writeImage(screenshot.getImage(), new File(screenShotsFolder + testName + "-check-if-broken-" + "FAILED" + DOT_PNG));
    }

    private void requestDbRedeploy(String databaseName, String sqlFile) {

        LOG.debug("Request db redeploy" + databaseName + "<" + sqlFile);

        try (Connection conn =
                DriverManager.getConnection(
                        String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", DB_URL, databaseName),
                        DB_USER,
                        DB_PASSWORD)) {

            final Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("DROP DATABASE IF EXISTS %s;", databaseName));
            stmt.executeUpdate(String.format("CREATE DATABASE %s;", databaseName));
            stmt.executeUpdate(String.format("USE %s;", databaseName));

            LOG.debug("Running script: " + stmt);
            final ScriptRunner scriptRunner = new ScriptRunner(conn, false, false);
            scriptRunner.setLogWriter(null);
            scriptRunner.runScript(new FileReader("../" + sqlFile));
            stmt.executeUpdate("UPDATE " + databaseName + ".Tenant SET identifier=\"alchemy\" where id=1;");

            LOG.debug("Request db redeploy completed");
        } catch (SQLException | FileNotFoundException e) {
            LOG.error("Unable to redoplay DB", e);
            throw new IllegalStateException("FAILED");
        } catch (IOException e) {
            LOG.error("Unable to open file", e);
            throw new IllegalStateException("FAILED");
        }
    }

    private void requestDropTestTenantDB(String databaseName) {
        LOG.debug("Request test tenant db drop " + databaseName);
        try (final Connection conn =
                DriverManager.getConnection(
                        String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", DB_URL, databaseName),
                        DB_USER,
                        DB_PASSWORD)) {

            final Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("DROP DATABASE IF EXISTS %s;", databaseName));
            LOG.debug("Request test tenant db drop completed");
        } catch (SQLException e) {
            LOG.error("Unable to drop DB", e);
            throw new IllegalStateException("FAILED");
        }
    }

    /** Assert snapshots */
    public void assertSnapshot() {
        assertSnapshot(Optional.empty(), Optional.of(500), Optional.empty());
    }

    public void assertSnapshot(Integer delay) {
        assertSnapshot(Optional.empty(), Optional.of(delay), Optional.empty());
    }

    public void assertSnapshot(Coords ignoreArea) {
        assertSnapshot(Optional.of(ignoreArea), Optional.of(500), Optional.empty());
    }

    public void assertSnapshot(Coords ignoreArea, Integer delay) {
        assertSnapshot(Optional.of(ignoreArea), Optional.of(delay), Optional.empty());
    }

    public void assertSnapshot(WebElement webElement) {
        assertSnapshot(Optional.empty(), Optional.empty(), Optional.of(webElement));
    }

    public void assertSnapshot(WebElement webElement, Integer delay) {
        assertSnapshot(Optional.empty(), Optional.of(delay), Optional.of(webElement));
    }

    public void assertSnapshot(Coords ignoreArea, Integer delay, WebElement webElement) {
        assertSnapshot(Optional.of(ignoreArea), Optional.of(delay), Optional.of(webElement));
    }

    public void assertSnapshot(Coords ignoreArea, WebElement webElement) {
        assertSnapshot(Optional.of(ignoreArea), Optional.empty(), Optional.of(webElement));
    }

    public void assertSnapshot(Optional<Coords> ignoreArea, Optional<Integer> delay, Optional<WebElement> webElement) {
        LOG.debug("Assert snapshot: " + screenShotsCount);
        delay.ifPresent(AbstractUITest::sleep);

        removeAllocationIndicatorsForScreenshots();

        AShot aShot;
        if (OsValidator.isMac()) {
            aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000));
        } else {
            aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100));
        }

        if (ignoreArea.isPresent()) aShot = aShot.addIgnoredArea(ignoreArea.get());

        aShot.coordsProvider(new WebDriverCoordsProvider());

        Screenshot screenshot;

        if (webElement.isPresent()) {
            screenshot = aShot.takeScreenshot(driver, webElement.get());
        } else {
            screenshot = aShot.takeScreenshot(driver);
        }

        final String testName = info.getTestMethod().get().getName() + "_" + screenShotsCount;
        final Path path = Paths.get(screenShotsFolder + testName + DOT_PNG);

        if (!Files.exists(path)) {
            writeImage(screenshot.getImage(), new File(screenShotsFolder + testName + DOT_PNG));
        }

        try {
            Optional<ImageDifference> differenceImage = getDifferenceImage(ImageIO.read(path.toFile()), screenshot);

            if (differenceImage.isPresent() && differenceImage.get().getPixelError() > DEVIATION) {
                writeImage(differenceImage.get().getImage(), new File(screenShotsFolder + testName + "FAILED" + DOT_PNG));
                writeImage(screenshot.getImage(), new File(screenShotsFolder + testName + "-current-" + "FAILED" + DOT_PNG));
                LOG.error("-----> Screenshot failed: " + screenShotsFolder + testName);
                failedScreenShotsCount++;
            }

        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        screenShotsCount++;
    }

    public void assertSnapshotMultipleElementsIgnored(List<Coords> ignoreArea, Optional<Integer> delay) {
        assertSnapshotMultipleElementsIgnored(ignoreArea, delay, Optional.empty());
    }

    public void assertSnapshotMultipleElementsIgnored(List<Coords> ignoreArea, Optional<Integer> delay, Optional<WebElement> webElement) {
        LOG.debug("Assert snapshot: " + screenShotsCount);
        delay.ifPresent(AbstractUITest::sleep);

        removeAllocationIndicatorsForScreenshots();

        AShot aShot;
        if (OsValidator.isMac()) {
            aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000));
        } else {
            aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100));
        }

        for (Coords ignoredCoords : ignoreArea) {
            aShot = aShot.addIgnoredArea(ignoredCoords);
        }

        aShot.coordsProvider(new WebDriverCoordsProvider());

        Screenshot screenshot;

        if (webElement.isPresent()) {
            screenshot = aShot.takeScreenshot(driver, webElement.get());
        } else {
            screenshot = aShot.takeScreenshot(driver);
        }

        final String testName = this.getClass().getSimpleName() + "_" + screenShotsCount;
        final Path path = Paths.get(screenShotsFolder + testName + DOT_PNG);

        if (!Files.exists(path)) {
            writeImage(screenshot.getImage(), new File(screenShotsFolder + testName + DOT_PNG));
        }

        try {
            Optional<ImageDifference> differenceImage = getDifferenceImage(ImageIO.read(path.toFile()), screenshot);

            if (differenceImage.isPresent() && differenceImage.get().getPixelError() > DEVIATION) {

                writeImage(differenceImage.get().getImage(), new File(screenShotsFolder + testName + "FAILED" + DOT_PNG));
                writeImage(screenshot.getImage(), new File(screenShotsFolder + testName + "-current-" + "FAILED" + DOT_PNG));
                LOG.error("-----> Screenshot failed: " + screenShotsFolder + testName);
                failedScreenShotsCount++;
            }

        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        screenShotsCount++;
    }

    protected void assertSnapshotMailHogInbox() {
        final List<WebElement> webElementsKb = getDriver().findElements(By.xpath("//*[contains(.,'kB')]"));
        List<WebElement> webElementsAgo = getDriver().findElements(By.xpath("//*[contains(.,'ago')]"));
        List<Coords> listOfCoords = new ArrayList<>();
        for (WebElement webElement : webElementsKb) {
            listOfCoords.add(
                    new Coords(
                            webElement.getLocation().getX(),
                            webElement.getLocation().getY(),
                            webElement.getSize().getWidth(),
                            webElement.getSize().getHeight()));
        }
        for (WebElement webElement : webElementsAgo) {
            listOfCoords.add(
                    new Coords(
                            webElement.getLocation().getX(),
                            webElement.getLocation().getY(),
                            webElement.getSize().getWidth(),
                            webElement.getSize().getHeight()));
        }
        assertSnapshotMultipleElementsIgnored(listOfCoords, Optional.empty());
    }

    protected void assertSnapshotMailHogSingleEmail() {
        WebElement inboxElement = getDriver().findElement(By.className("tab-content"));
        Coords inboxIgnoreArea =
                new Coords(
                        inboxElement.getLocation().getX(),
                        inboxElement.getLocation().getY(),
                        inboxElement.getSize().getWidth(),
                        inboxElement.getSize().getHeight());

        assertSnapshotMultipleElementsIgnored(Arrays.asList(inboxIgnoreArea), Optional.of(1000));
    }

    private static Optional<ImageDifference> getDifferenceImage(BufferedImage img1, Screenshot img2) {
        ImageDiffer imageDifferWithIgnored = new ImageDiffer();
        imageDifferWithIgnored.withColorDistortion(30);
        imageDifferWithIgnored.withIgnoredColor(Color.BLUE);
        imageDifferWithIgnored.withDiffMarkupPolicy(new ImageMarkupPolicy());
        Screenshot previousImage = new Screenshot(img1);
        previousImage.setIgnoredAreas(img2.getIgnoredAreas());
        previousImage.setCoordsToCompare(img2.getCoordsToCompare()); // set coordinates which are going to be compared for the master screen

        ImageDiff diff = imageDifferWithIgnored.makeDiff(previousImage, img2);

        if (diff.hasDiff()) {
            return Optional.of(new ImageDifference(diff.getDiffSize(), diff.getMarkedImage()));
        } else {
            return Optional.empty();
        }
    }

    public void clearDriver() {
        attempt(() -> driver.getLocalStorage().clear(), "Clear local storage");
        sleep(2000);
    }

    private boolean attempt(Runnable function, String message) {
        try {
            function.run();
            System.out.println(message);
            return true;
        } catch (Throwable t) {
            System.out.println("Failed to: " + message);
            return false;
        }
    }

    private void waitForPageLoaded() {

        ExpectedCondition<Boolean> expectation =
                driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(expectation);
        } catch (Throwable error) {
            Assertions.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public static void sleep(Integer milliseconds) {
        long secondsLong = (long) milliseconds;
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : Objects.requireNonNull(f.listFiles())) delete(c);
        }
        if (!f.delete()) throw new FileNotFoundException("Failed to delete file: " + f);
    }

    private static void writeImage(BufferedImage imageFile, File fileToWriteTo) {
        try {
            ImageIO.write(imageFile, AbstractUITest.PNG, fileToWriteTo);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ChromeDriver getDriver() {
        return driver;
    }

    private void waitForPage(AbstractPage page) {
        waitForPageLoaded();
        if (page.getExpectation().isPresent()) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(page.getExpectation().get());
        }
        if (page.waitForPage().isPresent()) sleep(page.waitForPage().get());
    }

    protected void switchTab(Integer tabOrder) {
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(tabOrder));
        resetResolution(1920, 1080);
    }

    protected void closeTab() {
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().close();
        getDriver().switchTo().window(tabs.get(0));
    }

    public void resetResolution(Integer x, Integer y) {
        getDriver().manage().window().setSize(new Dimension(x, y));
        sleep(500);
    }

    public void scrollToElement(WebElement element, Boolean up) {
        driver.executeScript("arguments[0].scrollIntoView(" + up + ");", element);
    }

    public void scrollElementUpBy(WebElement element, Integer pixels) {
        driver.executeScript("arguments[0].scrollBy(0," + pixels + ");", element);
        sleep(400);
    }

    public co.drytools.website.page.WelcomePage waitForWebsiteWelcomePage() {
        final co.drytools.website.page.WelcomePage page = new co.drytools.website.page.WelcomePage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.WelcomePage goToWebsiteWelcomePage() {
        final co.drytools.website.page.WelcomePage page = new co.drytools.website.page.WelcomePage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.AboutPage waitForWebsiteAboutPage() {
        final co.drytools.website.page.AboutPage page = new co.drytools.website.page.AboutPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.AboutPage goToWebsiteAboutPage() {
        final co.drytools.website.page.AboutPage page = new co.drytools.website.page.AboutPage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.PetsPage waitForWebsitePetsPage() {
        final co.drytools.website.page.PetsPage page = new co.drytools.website.page.PetsPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.PetsPage goToWebsitePetsPage() {
        final co.drytools.website.page.PetsPage page = new co.drytools.website.page.PetsPage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.PetCreatePage waitForWebsitePetCreatePage() {
        final co.drytools.website.page.PetCreatePage page = new co.drytools.website.page.PetCreatePage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.PetCreatePage goToWebsitePetCreatePage() {
        final co.drytools.website.page.PetCreatePage page = new co.drytools.website.page.PetCreatePage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.PetUpdatePage waitForWebsitePetUpdatePage() {
        final co.drytools.website.page.PetUpdatePage page = new co.drytools.website.page.PetUpdatePage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.PetUpdatePage goToWebsitePetUpdatePage(Integer id) {
        final co.drytools.website.page.PetUpdatePage page = new co.drytools.website.page.PetUpdatePage(id, this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.SignInPage waitForWebsiteSignInPage() {
        final co.drytools.website.page.SignInPage page = new co.drytools.website.page.SignInPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.SignInPage goToWebsiteSignInPage() {
        final co.drytools.website.page.SignInPage page = new co.drytools.website.page.SignInPage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.SignUpPage waitForWebsiteSignUpPage() {
        final co.drytools.website.page.SignUpPage page = new co.drytools.website.page.SignUpPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.SignUpPage goToWebsiteSignUpPage() {
        final co.drytools.website.page.SignUpPage page = new co.drytools.website.page.SignUpPage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.VerifyEmailPage waitForWebsiteVerifyEmailPage() {
        final co.drytools.website.page.VerifyEmailPage page = new co.drytools.website.page.VerifyEmailPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.VerifyEmailPage goToWebsiteVerifyEmailPage(String emailVerificationCode) {
        final co.drytools.website.page.VerifyEmailPage page = new co.drytools.website.page.VerifyEmailPage(emailVerificationCode, this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.ForgotPasswordPage waitForWebsiteForgotPasswordPage() {
        final co.drytools.website.page.ForgotPasswordPage page = new co.drytools.website.page.ForgotPasswordPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.ForgotPasswordPage goToWebsiteForgotPasswordPage() {
        final co.drytools.website.page.ForgotPasswordPage page = new co.drytools.website.page.ForgotPasswordPage(this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.ResetPasswordPage waitForWebsiteResetPasswordPage() {
        final co.drytools.website.page.ResetPasswordPage page = new co.drytools.website.page.ResetPasswordPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.website.page.ResetPasswordPage goToWebsiteResetPasswordPage(String resetPasswordCode) {
        final co.drytools.website.page.ResetPasswordPage page = new co.drytools.website.page.ResetPasswordPage(resetPasswordCode, this);
        driver.get(WEBSITE_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.SignInPage waitForAdminPortalSignInPage() {
        final co.drytools.adminPortal.page.SignInPage page = new co.drytools.adminPortal.page.SignInPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.SignInPage goToAdminPortalSignInPage() {
        final co.drytools.adminPortal.page.SignInPage page = new co.drytools.adminPortal.page.SignInPage(this);
        driver.get(ADMINPORTAL_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.SignUpPage waitForAdminPortalSignUpPage() {
        final co.drytools.adminPortal.page.SignUpPage page = new co.drytools.adminPortal.page.SignUpPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.SignUpPage goToAdminPortalSignUpPage() {
        final co.drytools.adminPortal.page.SignUpPage page = new co.drytools.adminPortal.page.SignUpPage(this);
        driver.get(ADMINPORTAL_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.VerifyEmailPage waitForAdminPortalVerifyEmailPage() {
        final co.drytools.adminPortal.page.VerifyEmailPage page = new co.drytools.adminPortal.page.VerifyEmailPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.VerifyEmailPage goToAdminPortalVerifyEmailPage(String emailVerificationCode) {
        final co.drytools.adminPortal.page.VerifyEmailPage page = new co.drytools.adminPortal.page.VerifyEmailPage(emailVerificationCode, this);
        driver.get(ADMINPORTAL_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.ForgotPasswordPage waitForAdminPortalForgotPasswordPage() {
        final co.drytools.adminPortal.page.ForgotPasswordPage page = new co.drytools.adminPortal.page.ForgotPasswordPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.ForgotPasswordPage goToAdminPortalForgotPasswordPage() {
        final co.drytools.adminPortal.page.ForgotPasswordPage page = new co.drytools.adminPortal.page.ForgotPasswordPage(this);
        driver.get(ADMINPORTAL_URL + page.getPath());
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.ResetPasswordPage waitForAdminPortalResetPasswordPage() {
        final co.drytools.adminPortal.page.ResetPasswordPage page = new co.drytools.adminPortal.page.ResetPasswordPage(this);
        waitForPage(page);
        return page;
    }

    public co.drytools.adminPortal.page.ResetPasswordPage goToAdminPortalResetPasswordPage(String resetPasswordCode) {
        final co.drytools.adminPortal.page.ResetPasswordPage page = new co.drytools.adminPortal.page.ResetPasswordPage(resetPasswordCode, this);
        driver.get(ADMINPORTAL_URL + page.getPath());
        waitForPage(page);
        return page;
    }
}
