import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class Enter {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\kgavr\\OneDrive\\Desktop\\Javaapp\\Javaapp\\apks\\org.wikipedia.apk");
        capabilities.setCapability("orientation", "PORTRAIT");

        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void Assert_title() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wiki input",
                10
        );

        SendKeys(
                By.xpath("//*[contains (@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                10

        );
        WebElement title_element=waitForElementAndClick(
               By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_title'][@text='Java']"),
               "Cannot find Search Java input",
               10
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);
        String title_after_rotation = waitforelementGetAtribute(
                By.id("org.wikipedia:id/search_container"),
                "text",
                "cannot find title of article ",
                15
        );

        driver.rotate(ScreenOrientation.PORTRAIT);
        String title_after_second_rotation = waitforelementGetAtribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article ",
                15
        );

        assertElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/view_page_title_text'][@text='Java']"),
                "Cant find title element"
        );

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInseconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInseconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement SendKeys(By by, String value, String error_message, long timeoutSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.click();
        return element;
    }

    private boolean waitForelementNot(By by, String error_message, long timeoutInsecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInsecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitAndClear(By by, String error_message, long timeoutInsecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInsecond);
        element.clear();
        return element;
    }
    private boolean waitFormenutoRender(By by, String error_message, long timeoutInsecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInsecond);
        wait.withMessage(error_message + "\n");
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.click();
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
        protected void swipeElementToLeft(By by, String error_message) {
            WebElement element = waitForElementPresent(by,
                    error_message,
                    15);

            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;


            TouchAction action = new TouchAction(driver);
            action
                    .press(right_x, middle_y)
                    .waitAction(200)
                    .moveTo(left_x, middle_y)
                    .release().perform();

        }
    private void assertElementPresent(By by, String error_message) {
     try {
         driver.findElement(by);
     }
     catch (NoSuchElementException exception) {
            throw new AssertionError( error_message);
        }
    }
    private String waitforelementGetAtribute(By by, String attribute, String error_message, long timeoutinseconds) {
        WebElement element = waitForElementPresent(by,error_message, timeoutinseconds);
        return element.getAttribute(attribute);

    }

    }


