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

public class CreateContactWithSupportDateTest {

	public static void main(String[] args) throws Throwable {
		FileUtility flib = new FileUtility();
		ExcelUtility elib = new ExcelUtility();
		JavaUtility jlib = new JavaUtility();

		// Read common data from property file
		String BROWSER = flib.getDataFromPropertyFile("browser");
		String URL = flib.getDataFromPropertyFile("url");
		String USERNAME = flib.getDataFromPropertyFile("username");
		String PASSWORD = flib.getDataFromPropertyFile("password");

		// Generate random number
		int randomInt = jlib.getRandomNum();

		// Read testScript data from from excel file
		String LastName = elib.getDataFromExcel("Contact", 4, 2) + randomInt;
		System.out.println("LastName is : " + LastName);

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

		//Date
		String StartDate = jlib.getSystemDateYYYYMMDD();
		String EndDate = jlib.getRequiredDateYYYYMMDD(StartDate, 30);
		
		System.out.println("Start date : " + StartDate + " End date : " + EndDate);

		wdlib.sendKeysToElement(By.xpath("//input[@name='lastname']"), LastName);

		driver.findElement(By.name("support_start_date")).clear();
		wdlib.sendKeysToElement(By.name("support_start_date"), StartDate);

		driver.findElement(By.name("support_end_date")).clear();
		wdlib.sendKeysToElement(By.name("support_end_date"), EndDate);

		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();

		String StartDateVerify = driver.findElement(By.id("dtlview_Support Start Date")).getText();
		if (StartDateVerify.equals(StartDate)) {
			System.out.println(StartDate + " given Successfully!!!");
		}
		String EndDateVerify = driver.findElement(By.id("dtlview_Support End Date")).getText();
		if (EndDateVerify.equals(EndDate)) {
			System.out.println(EndDate + " given Successfully!!!");
		}

//		Log Out
		WebElement profile = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions action = new Actions(driver);
		action.moveToElement(profile).build().perform();
		driver.findElement(By.linkText("Sign Out")).click();

		driver.quit();

	}

}
