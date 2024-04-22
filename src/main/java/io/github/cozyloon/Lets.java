package io.github.cozyloon;

/***************************************************************************************
 *    Title: <Lets selenium based automation framework>
 *    Author: <Chathumal Sangeeth>
 *    Date: <22/04/2024>
 *    Code version: <1.0.0>
 *    Availability: <Maven Dependency>
 *
 ***************************************************************************************/

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Lets {
    static WebDriver driver;
    static final String SCREENSHOT_PATH = "./ScreenShots/";

    public Lets() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void launchDriver(String browserType) {
        if (browserType.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        } else if (browserType.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        } else if (browserType.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
            driver.manage().window().maximize();
        }
    }

    public static void launchDriverWithHeadlessMode(String browserType) {
        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless=new");
            driver = new FirefoxDriver(options);
        }
    }

    public static void closeDriver() {
        driver.quit();
    }

    public static void getUrl(String url) {
        driver.get(url);
    }

    public static void back() {
        driver.navigate().back();
    }

    public static void forward() {
        driver.navigate().forward();
    }

    public static void refresh() {
        driver.navigate().refresh();
    }

    public static void click(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public static void type(By locator, String text) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public static void selectDrpDwnByVisibleText(By locator, String text) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public static void selectDrpDwnByValue(By locator, String value) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public static void selectDrpDwnByIndex(By locator, int index) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public static void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public static void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public static void mouseHoverAndClick(By locator, long waitTimeInSeconds) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds)).until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public static boolean elementIsDisplayed(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.isDisplayed();
    }

    public static boolean elementIsSelected(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        return element.isSelected();
    }

    public static String getText(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    public static void scrollToPageBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToPageTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToElement(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void javaScriptClick(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void highLightTheElement(By locator) throws InterruptedException {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("arguments[0].setAttribute('style', 'background: red; border: 2px solid black;');", element);
        Thread.sleep(500);
        javascript.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
    }

    public static void takePageScreenShot() throws IOException {
        Screenshot screenshot = (new AShot()).shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        File f = new File(SCREENSHOT_PATH);
        Date date;
        SimpleDateFormat dateFormat;
        if (!f.exists() || !f.isDirectory()) {
            (new File(SCREENSHOT_PATH)).mkdir();
        }
        date = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        ImageIO.write(screenshot.getImage(), "jpg", new File(SCREENSHOT_PATH + dateFormat.format(date) + ".jpg"));
    }

    public static void waitTillElementToBeVisible(By locator, long waitTimeInSecond) throws InterruptedException {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSecond)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.wait();
    }

    public static void waitTillElementToBeClickable(By locator, long waitTimeInSecond) throws InterruptedException {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSecond)).until(ExpectedConditions.elementToBeClickable(locator));
        element.wait();
    }

    public static void fileUploadChooser(By locator, String filePath) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(filePath);
    }

    public static void moveIntoTheIframe(By locator) {
        WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.switchTo().frame(iframe);
    }

    public static void moveBackToTheParentIframe() {
        driver.switchTo().parentFrame();
    }

    public static void moveBackFromIframe() {
        driver.switchTo().defaultContent();
    }

    public static String getPageTitle() {
        return driver.getTitle();
    }

    public static boolean elementIsEnabled(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.isEnabled();
    }

    public static void takeSS(By locator) {
        try {
            File screenshot = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .getScreenshotAs(OutputType.FILE);

            File directory = new File(SCREENSHOT_PATH);
            if (!directory.exists() || !directory.isDirectory()) {
                FileUtils.forceMkdir(directory);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String fileName = SCREENSHOT_PATH + dateFormat.format(new Date()) + ".jpg";

            FileUtils.copyFile(screenshot, new File(fileName));
        } catch (IOException e) {
            e.getMessage();
        }

    }
}
