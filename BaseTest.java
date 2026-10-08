package com.parabank.tests;
import com.parabank.driver.DriverFactory; 
import org.testng.annotations.*;
public abstract class BaseTest
 { 
@BeforeMethod(alwaysRun=true) 
public void start()
{
	DriverFactory.start();
}
 @AfterMethod(alwaysRun=true) 
public void stop()
{
	 DriverFactory.stop();
} 
}
