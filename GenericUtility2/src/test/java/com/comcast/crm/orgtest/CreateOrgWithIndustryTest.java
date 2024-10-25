package com.comcast.crm.orgtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateOrgWithIndustryTest {

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
		String orgName = elib.getDataFromExcel("Sheet3", 1, 2) + randomInt;
		String industryName = elib.getDataFromExcel("Sheet3", 4, 3) + randomInt;
		String typeName = elib.getDataFromExcel("Sheet3", 4, 4) + randomInt;

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

		driver.findElement(By.linkText("Organizations")).click();

		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();

		wdlib.sendKeysToElement(By.xpath("//input[@type='text' and @name='accountname']"), orgName);

		WebElement indDD = driver.findElement(By.name("industry"));
		Select selInd = new Select(indDD);
		selInd.selectByVisibleText(industryName);

		WebElement typeDD = driver.findElement(By.name("accounttype"));
		Select selType = new Select(typeDD);
		selType.selectByVisibleText(typeName);

		driver.findElement(By.xpath("(//input[@type='button' and @class='crmbutton small save'])[2]")).click();

		WebElement verify = driver.findElement(By.xpath("//span[contains(text(), 'Updated today')]"));
		if (verify.isDisplayed()) {
			System.out.println(orgName + " Created Successfully!!!");
		}

		String IndDD1 = driver.findElement(By.id("dtlview_Industry")).getText();
		if (IndDD1.equals(industryName)) {
			System.out.println(industryName + " selected Successfully!!!");
		}

		String TypeDD2 = driver.findElement(By.id("dtlview_Type")).getText();
		if (TypeDD2.equals(typeName)) {
			System.out.println(typeName + " selected Successfully!!!");
		}

		// Log Out
		WebElement profile = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions action = new Actions(driver);
		action.moveToElement(profile).build().perform();
		driver.findElement(By.linkText("Sign Out")).click();

		driver.quit();

	}

}