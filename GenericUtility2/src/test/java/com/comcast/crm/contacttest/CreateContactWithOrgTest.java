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

public class CreateContactWithOrgTest {

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
		String orgName = elib.getDataFromExcel("Contact", 7, 2) + randomInt;
		String LastName = elib.getDataFromExcel("Contact", 7, 3) + randomInt;

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

//		Navigate to organizations page and create one org
		driver.findElement(By.linkText("Organizations")).click();
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

		wdlib.sendKeysToElement(By.xpath("//input[@type='text' and @name='accountname']"), orgName);
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();

		WebElement verify = driver.findElement(By.xpath("//span[contains(text(), 'Updated today')]"));
		if (verify.isDisplayed()) {
			System.out.println("Org : " + orgName + " Created Successfully!!!");
		}

//		navigate to contact module and perform work
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		wdlib.sendKeysToElement(By.xpath("//input[@name='lastname']"), LastName);
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();

//		switch to child window using Utility class & perform work
		wdlib.switchToTabOnTitle(driver, "module=Accounts");

		wdlib.sendKeysToElement(By.name("search_text"), orgName);

		driver.findElement(By.name("search")).click();

		driver.findElement(By.xpath("//a[text()='" + orgName + "']")).click();

//		Again switch back to parent window using Utility class
		wdlib.switchToTabOnTitle(driver, "Contacts&action");

//		Click on save and verify
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();
		String verifyLn = driver.findElement(By.id("dtlview_Last Name")).getText();
		if (verifyLn.equals(LastName)) {
			System.out.println("Lastname : " + LastName + " given Successfully!!!");
		}

//		Log Out
		WebElement profile = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions action = new Actions(driver);
		action.moveToElement(profile).build().perform();
		driver.findElement(By.linkText("Sign Out")).click();

		driver.quit();
	}
}
