package com.comcast.crm.generic.webdriverutility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility {
	WebDriver driver;
	WebDriverWait wait;
	Actions act;

	public WebDriverUtility(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.act = new Actions(driver);
	}

	public void waitForElementPresent(WebDriver driver, WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Implicit Wait
	public void waitForPageToLoad(int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));

	}

	// Browser Related
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public void closeBrowser() {
		driver.close();
	}

	public void quitBrowser() {
		driver.quit();
	}

	// Switching between windows
	public void switchToTabOnTitle(WebDriver driver, String partialTitle) {
		Set<String> set = driver.getWindowHandles();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String windowID = it.next();
			driver.switchTo().window(windowID);

			String actUrl = driver.getCurrentUrl();
			if (actUrl.contains(partialTitle)) {
				break;
			}
		}
	}

	// Web Element Related
	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	// Getters
	public String getText(By by) {
		return findElement(by).getText();
	}

	public String getAttribute(By by, String attribute) {
		return findElement(by).getAttribute(attribute);
	}

	public String getCssValue(By by, String css) {
		return findElement(by).getCssValue(css);
	}

	// Navigation
	public void navigateBack() {
		driver.navigate().back();
	}

	public void navigateForward() {
		driver.navigate().forward();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	// Alerts
	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
	}

	// Frames
	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrame(String name) {
		driver.switchTo().frame(name);
	}

	public void switchToFrame(WebElement element) {
		driver.switchTo().frame(element);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}

	// Select class - Drop down
	public void select(WebElement element, int index) {
		Select sel = new Select(element);
		sel.selectByIndex(index);
	}

	public void select(WebElement element, String text) {
		Select sel = new Select(element);
		sel.selectByVisibleText(text);
	}

	public void select(String value, WebElement element) {
		Select sel = new Select(element);
		sel.selectByValue(value);
	}

	// Action class
	// Method to move to an element and click
	public void moveToElementAndClick(By locator) {
		WebElement element = driver.findElement(locator);
		act.moveToElement(element).click().perform();
	}

	// Method to perform a double click
	public void doubleClick(By locator) {
		WebElement element = driver.findElement(locator);
		act.doubleClick(element).perform();
	}

	// Method to perform a right-click (context click)
	public void rightClick(By locator) {
		WebElement element = driver.findElement(locator);
		act.contextClick(element).perform();
	}

	// Method to send keys to an element
	public void sendKeysToElement(By locator, CharSequence... keysToSend) {
		WebElement element = driver.findElement(locator);
		act.sendKeys(element, keysToSend).perform();
	}

	// Method to drag and drop from one element to another
	public void dragAndDrop(By src, By tgt) {
		WebElement source = driver.findElement(src);
		WebElement target = driver.findElement(tgt);
		act.dragAndDrop(source, target).perform();
	}

	// Method to drag and drop by offset
	public void dragAndDropByOffset(By locator, int xOffset, int yOffset) {
		WebElement element = driver.findElement(locator);
		act.dragAndDropBy(element, xOffset, yOffset).perform();
	}

	// Method to hold down a key
	public void keyDown(Keys key) {
		act.keyDown(key).perform();
	}

	// Method to release a key
	public void keyUp(Keys key) {
		act.keyUp(key).perform();
	}

	// Method to pause the action for a duration
	public void pause(long milliseconds) {
		act.pause(java.time.Duration.ofMillis(milliseconds)).perform();
	}

	// Handle cookies
	public void addCookie(String name, String value) {
		driver.manage().addCookie(new org.openqa.selenium.Cookie(name, value));
	}

	public void deleteCookie(String name) {
		driver.manage().deleteCookieNamed(name);
	}

	// Screenshot
	public void takeScreenshot(WebElement element, String path) throws IOException {
		File src = element.getScreenshotAs(OutputType.FILE);
		File dest = new File(path);
		FileHandler.copy(src, dest);
	}
}
