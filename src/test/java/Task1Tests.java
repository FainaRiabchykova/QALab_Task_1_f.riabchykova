import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.ENTER;
import static org.testng.Assert.assertTrue;

public class Task1Tests {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/");
    }

    @Test
    public void isSearchByImageIconDisplayed() {
        driver.findElement(By.name("q")).sendKeys("Apple", ENTER);
        assertTrue(driver.getTitle().contains("Apple - Google Search"));
        driver.findElement(By.xpath("//a[text()='Images']")).click();
        WebElement element = driver.findElement(By.xpath("//span[@aria-label='Search by image']"));
       assertTrue(element.isDisplayed());
    }

    @Test
    public void resultsContainTagIMG() {
        driver.findElement(By.name("q")).sendKeys("Apple", ENTER);
        assertTrue(driver.getCurrentUrl().contains("Apple"));
        driver.findElement(By.xpath("//a[text()='Images']")).click();

        List<WebElement> elementList = driver.findElements(By.xpath("//*[@class='rg_i Q4LuWd']"));
        for (WebElement webElement : elementList) {
            assertTrue(webElement.getTagName().contains("img"));
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
