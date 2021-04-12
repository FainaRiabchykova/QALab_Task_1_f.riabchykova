import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.ENTER;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Task2Tests {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://accounts.google.com");
    }

    @Test
    public void checkSendMessageViaGmail() {

        driver.findElement(By.xpath("//div[@class='Xb9hP']//input[@type='email']")).sendKeys("qariatest@gmail.com\n");
        WebElement nextButton = driver.findElement(By.xpath("//span[text()='Далі']"));
        Actions actions = new Actions(this.driver);
        actions.click(nextButton);
        actions.build().perform();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.name("password")).sendKeys("Aaaa!111");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", nextButton);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement googleApps = driver.findElement(By.className("gb_D"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", googleApps);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://mail.google.com/mail/u/0/?tab=km#inbox");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()=\"Написати\"]")));
        WebElement composeButton = driver.findElement(By.xpath("//div[text()=\"Написати\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", composeButton);

        driver.findElement(By.xpath("//div[@class=\"wO nr l1\"]//textarea[@name=\"to\"]")).sendKeys("qualitydandelion@gmail.com", ENTER);
        driver.findElement(By.name("subjectbox")).sendKeys("testMessage", ENTER);
        driver.findElement(By.xpath("//div[@class=\"Am Al editable LW-avf tS-tW\"]")).sendKeys("testMessage", ENTER);
        driver.findElement(By.xpath("//div[text()='Надіслати']")).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span//a[contains(@href, 'sent')]")));

        WebElement sentMessages = driver.findElement(By.xpath("//span//a[contains(@href, 'sent')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", sentMessages);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        WebElement titleMessage = driver.findElement(By.xpath("(//span[@email='qualitydandelion@gmail.com'])[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", titleMessage);
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@dir=\"ltr\"])[1]")));
        WebElement letterMessage = driver.findElement(By.xpath("(//div[@dir=\"ltr\"])[1]"));
        assertEquals(letterMessage.getText(), "testMessage");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
