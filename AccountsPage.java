package com.parabank.pages;
import org.openqa.selenium.*; import java.util.*;
public class AccountsPage extends BasePage 
{ 
	private By accounts=By.cssSelector("a[href*='overview.htm']"); 
	public void openOverview()
	{
		click(accounts);
	}
	public List<String> accountIds()
	{
		List<WebElement> els=driver.findElements(By.cssSelector("table#accountTable tbody tr td:first-child a")); 
		return els.stream().map(WebElement::getText).toList();
	}
	public String firstAccount()
	{
		return accountIds().get(0);
	} 
}
