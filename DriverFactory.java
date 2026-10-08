package com.parabank.driver;

import com.parabank.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory 
{
  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
  private DriverFactory() 
  {
	  
  }
  public static void start()
  { 
	  WebDriverManager.chromedriver().setup(); 
	  ChromeOptions o=new ChromeOptions(); 
	  if(Config.headless()) 
		  o.addArguments("--headless=new"); 
	      o.addArguments("--window-size=1440,1000","--disable-notifications"); 
	      DRIVER.set(new ChromeDriver(o)); 
  }
  public static WebDriver get()
  { 
	  return DRIVER.get(); 
  }
  public static void stop()
  { 
	  WebDriver d=DRIVER.get(); 
	  if(d!=null)
	  {
		  d.quit(); 
	  DRIVER.remove();
	  } 
  }
}
