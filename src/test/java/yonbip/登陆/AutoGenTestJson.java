package yonbip.登陆;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AutoGenTestJson {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://c2.yonyoucloud.com/");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("13717761827");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("【实时获取或修改】");
        driver.findElement(By.id("submit_btn_login")).click();

        driver.quit();
    }
}