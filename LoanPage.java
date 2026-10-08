package com.parabank.pages;
import org.openqa.selenium.*; 
import java.time.Duration; 
import org.openqa.selenium.support.ui.*;
public class LoanPage extends BasePage 
{ 
	private By amount=By.id("amount"), 
	down=By.id("downPayment"), from=By.id("fromAccountId"), 
	request=By.cssSelector("input[value='Apply Now']"); 
	public void openPage()
	{
		open("/requestloan.htm");
	} 
	public void apply(String source,String amt,String dp)
	{
		new WebDriverWait(driver,Duration.ofSeconds(15)).until(d->new Select(d.findElement(from)).getOptions().stream().anyMatch(o->o.getText().contains(source)));
		selectValue(from,source);
		type(amount,amt);
		type(down,dp);
		click(request);
	} 
	public String result()
	{
		return text(By.id("loanStatus"));
	} 
	public String loanAccount()
	{
		return text(By.id("loanAccountId"));
	} 
	public String response()
	{
		return driver.findElement(By.tagName("body")).getText();
	} 
	
}
