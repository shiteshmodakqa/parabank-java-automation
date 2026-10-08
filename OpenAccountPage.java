package com.parabank.pages;
import org.openqa.selenium.*; 
import java.time.Duration; 
import org.openqa.selenium.support.ui.*;
public class OpenAccountPage extends BasePage 
{ 
	private By type=By.id("type"), from=By.id("fromAccountId"), open=By.cssSelector("input[value='Open New Account']"); 
	public void openPage()
	{
		open("/openaccount.htm");
	} 
	public String createChecking(String sourceId)
	{ 
		new WebDriverWait(driver,Duration.ofSeconds(15)).until(d->new Select(d.findElement(from)).getOptions().stream().anyMatch(o->o.getText().contains(sourceId))); 
		select(type,"CHECKING"); 
		selectValue(from,sourceId); 
		click(open); 
		return new WebDriverWait(driver,Duration.ofSeconds(15)).until(d->d.findElement(By.id("newAccountId")).getText()); 
	} 
	}
