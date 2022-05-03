package ru_ozon;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteFromCart {
    FirefoxDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "D:\\downloads\\geckodriver-v0.31.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10, 1000);
    }

    @Test
    public void deleteProduct() {
        driver.get("https://www.ozon.ru/highlight/globalpromo/");
        wait.until(driver -> driver.findElement(By.cssSelector(".k1ab:nth-child(4) .d1j:nth-child(1) .ui-g0:nth-child(1)")));
        driver.findElement(By.cssSelector(".k1ab:nth-child(4) .d1j:nth-child(1) .ui-g0:nth-child(1)")).click();

        driver.get("https://www.ozon.ru/cart");
        wait.until(driver -> driver.findElement(By.cssSelector(".cc2 > button:nth-child(1) > span:nth-child(1) > svg:nth-child(1)")));
        driver.findElement(By.cssSelector(".cc2 > button:nth-child(1) > span:nth-child(1) > svg:nth-child(1)")).click();

        String titleXpath = "/html/body/div[1]/div/div[1]/div/div/div[4]/div[3]/div/div/div/div/div[2]";
        wait.until(driver -> driver.findElement(By.xpath(titleXpath)));
        WebElement elementCounter = driver.findElementByXPath(titleXpath);
        int count = Integer.parseInt(elementCounter.getText());
        String msg = String.format("Кол-во элементов в корзине ожидалось больше 0, получено: %s", count);
        Assert.assertTrue(msg, count > 0);

        driver.findElement(By.cssSelector(".an1:nth-child(2) .ui-g0")).click();
        driver.findElement(By.cssSelector(".a7m .ui-g0")).click();
        msg = String.format("Счетчик должен был быть удален");
        Assert.assertTrue(msg, driver.findElements(By.xpath(titleXpath)).isEmpty());

        wait.until(driver -> driver.findElement(By.xpath("//h1")));
        WebElement elementEmptyCart = driver.findElementByXPath("//h1");
        String emptyCartText = elementEmptyCart.getText();
        msg = String.format("Название страницы ожидаем: %s получили: %s", "Корзина пуста", emptyCartText);
        Assert.assertEquals(msg, "Корзина пуста", emptyCartText);
    }

    @After
    public void close() {
        driver.quit();
    }
}
