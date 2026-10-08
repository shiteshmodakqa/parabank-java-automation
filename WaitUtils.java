package com.parabank.utils;
import org.openqa.selenium.*; 
import org.openqa.selenium.support.ui.*; 
import java.time.Duration;
public final class WaitUtils 
{
 private WaitUtils()
 {
	 
 }
 public static WebElement visible(WebDriver d, By by)
 { 
	 return new WebDriverWait(d,Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(by)); 
}
 public static void clickable(WebDriver d, By by)
 { 
	 new WebDriverWait(d,Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(by)); 
	 
 }
 public static boolean textPresent(WebDriver d, By by,String text)
 { 
	 return new WebDriverWait(d,Duration.ofSeconds(15)).until(ExpectedConditions.textToBePresentInElementLocated(by,text)); 
	 
 }
}
