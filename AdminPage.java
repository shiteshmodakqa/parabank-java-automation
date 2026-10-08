package com.parabank.pages;
import org.openqa.selenium.By;
public class AdminPage extends BasePage 
{ 
	public void openPage()
	{
		driver.get(com.parabank.config.Config.adminUrl());
	} 
	public void selectLoanProvider(String provider)
	{
		select(By.id("loanProvider"),provider);
		click(By.cssSelector("input[value='Submit']"));
	} 
	public void clean()
	{
		click(By.cssSelector("input[value='Clean']"));
	} 
}
