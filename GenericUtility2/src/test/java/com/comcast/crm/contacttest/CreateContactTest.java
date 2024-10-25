package com.comcast.crm.contacttest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateContactTest {

	public static void main(String[] args) throws Throwable {
		// Read common data from property file
		FileUtility flib = new FileUtility();
		JavaUtility jlib = new JavaUtility();

		String BROWSER = flib.getDataFromPropertyFile("browser");
		String URL = flib.getDataFromPropertyFile("url");
		String USERNAME = flib.getDataFromPropertyFile("username");
		String PASSWORD = flib.getDataFromPropertyFile("password");

		// Generate random number
		int randomInt = jlib.getRandomNum();

		// Read testScript data from from excel file
		ExcelUtility elib = new ExcelUtility();
		String LastName = elib.getDataFromExcel("Contact", 1, 2) + randomInt;
//		System.out.println(LastName);

		
		WebDriver driver = null;

		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}

		WebDriverUtility wdlib = new WebDriverUtility(driver);
		wdlib.waitForPageToLoad(15);		
		driver.manage().window().maximize();
		driver.get(URL);

		wdlib.sendKeysToElement(By.name("user_name"), USERNAME);
		wdlib.sendKeysToElement(By.name("user_password"), PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		wdlib.sendKeysToElement(By.xpath("//input[@name='lastname']"), LastName);

		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();

		String verifyLn = driver.findElement(By.id("dtlview_Last Name")).getText();
		if (verifyLn.equals(LastName)) {
			System.out.println(LastName + " given Successfully!!!");
		}

		// Log Out 
		WebElement profile = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions action = new Actions(driver);
		action.moveToElement(profile).build().perform();
		driver.findElement(By.linkText("Sign Out")).click();

		driver.quit();

	}

}
