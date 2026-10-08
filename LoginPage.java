package com.parabank.pages;
import org.openqa.selenium.By;
public class LoginPage extends BasePage 
{ 
	private final By user=By.name("username"), pass=By.name("password"), login=By.cssSelector("input[value='Log In']"); 
	public LoginPage open()
	{
		open("/index.htm");
		return this;
	} 
	public void login(String u,String p)
	{
		type(user,u);
		type(pass,p);
		click(login);
	} public void register()
	{
		click(By.linkText("Register"));
	} 
}
