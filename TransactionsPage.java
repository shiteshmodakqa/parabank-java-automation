package com.parabank.pages;
import com.parabank.models.Transaction; 
import org.openqa.selenium.*; 
import java.util.*;
public class TransactionsPage extends BasePage 
{ 
	public void openPage()
	{
		open("/findtrans.htm");
	} 
	public List<Transaction> transactions(String accountId)
	{
		selectValue(By.id("accountId"),accountId);
		click(By.cssSelector("input[value='Find Transactions']")); 
		return rows();
	} 
	private List<Transaction> rows()
	{
		List<Transaction> out=new ArrayList<>(); 
		for(WebElement tr:driver.findElements(By.cssSelector("table#transactionTable tbody tr")))
		{
			List<WebElement> td=tr.findElements(By.tagName("td"));
			if(td.size()>=5)
				out.add(new Transaction(td.get(0).getText(),td.get(1).getText(),td.get(2).getText(),td.get(3).getText(),td.get(4).getText()));
		} 
		return out;
	} 
	
}
