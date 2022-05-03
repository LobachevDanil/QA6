package ru_ozon;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCart {
    FirefoxDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "D:\\downloads\\geckodriver-v0.31.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("https://www.ozon.ru/cart?check=checktab0");
        WebElement cartTitle = driver.findElementByXPath("//h1");
        if (!cartTitle.getText().equals("Корзина пуста")) {
            driver.findElement(By.cssSelector(".k8a")).click();
            driver.findElement(By.cssSelector(".a7m .ui-g0")).click();
        }
    }

    @Test
    public void addProduct() {
        driver.get("https://www.ozon.ru/");

        driver.findElement(By.name("text")).sendKeys("ручка");
        WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
        wait.until(driver -> driver.findElement(By.cssSelector("a.x8t:nth-child(1)")));
        driver.findElement(By.cssSelector("a.x8t:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".ip2:nth-child(2) .ui-g0")).click();
        WebElement product = driver.findElementByXPath("//div[2]/div/a/span/span");
        String productName = product.getText();
        driver.findElement(By.cssSelector(".la")).click();

        WebElement elementCart = driver.findElementByXPath("//div[3]/div[3]/div/div/div/div/div");
        String title = elementCart.getText();
        String msg = String.format("Название страницы ожидаем: %s получили: %s", "Корзина", title);
        Assert.assertEquals(msg, "Корзина", title);

        WebElement elementCounter = driver.findElementByXPath("//div[@id='stickyHeader']/div[4]/a[2]/span");
        String count = elementCounter.getText();
        msg = String.format("Кол-во товаров ожидаем: %s получили: %s", "1", title);
        Assert.assertEquals(msg, "1", count);

        WebElement productInCart = driver.findElementByXPath("//div[2]/a/span/span");
        String nameProductInCart = productInCart.getText();
        msg = String.format("Кол-во товаров ожидаем: %s получили: %s", productName, nameProductInCart);
        Assert.assertEquals(msg, productName, nameProductInCart);
    }


    @After
    public void close() {
        //driver.quit();
    }
}
