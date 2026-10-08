package com.parabank.pages;
import com.parabank.config.Config; 
import com.parabank.driver.DriverFactory; 
import com.parabank.utils.WaitUtils; 
import org.openqa.selenium.*; 
import org.openqa.selenium.support.ui.Select;
public abstract class BasePage 
{ 
	protected final WebDriver driver=DriverFactory.get(); 
	protected void click(By by)
	{
		WaitUtils.clickable(driver,by);
	    driver.findElement(by).click();
	} 
	protected void type(By by,String v)
	{
		WaitUtils.visible(driver,by).clear();
		driver.findElement(by).sendKeys(v);
	} 
	protected void select(By by,String v)
	{
		new Select(WaitUtils.visible(driver,by)).selectByVisibleText(v);
	} 
	protected void selectValue(By by,String v)
	{
		new Select(WaitUtils.visible(driver,by)).selectByValue(v);
	} 
	protected String text(By by)
	{
		return WaitUtils.visible(driver,by).getText();
	}
	protected void open(String path)
	{
		driver.get(Config.baseUrl()+path);
	} 
	
}
