package com.parabank.pages;
import org.openqa.selenium.*; 
import java.time.Duration; 
import org.openqa.selenium.support.ui.*;
public class TransferPage extends BasePage 
{ 
	private By amount=By.id("amount"),from=By.id("fromAccountId"),to=By.id("toAccountId"),button=By.cssSelector("input[value='Transfer']"); 
	public void openPage()
	{
		open("/transfer.htm");
	} 
	public void transfer(String amt,String source,String destination)
	{
		new WebDriverWait(driver,Duration.ofSeconds(15)).until(d->new Select(d.findElement(from)).getOptions().stream().anyMatch(o->o.getText().contains(source)));
		selectValue(from,source);
		new WebDriverWait(driver,Duration.ofSeconds(15)).until(d->new Select(d.findElement(to)).getOptions().stream().anyMatch(o->o.getText().contains(destination)));
		selectValue(to,destination);
		type(amount,amt);
		click(button);
	} 
}
