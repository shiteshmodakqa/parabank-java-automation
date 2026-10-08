package com.parabank.pages;
import com.parabank.models.User; 
import org.openqa.selenium.By;
public class RegisterPage extends BasePage 
{ 
	private By f(String n)
	{
		return By.id("customer."+n);
	} 
	public RegisterPage register(User u)
	{
		type(f("firstName"),u.firstName());
		type(f("lastName"),u.lastName());
		type(f("address.street"),u.address());
		type(f("address.city"),u.city());
		type(f("address.state"),u.state());
		type(f("address.zipCode"),u.zipCode());
		type(f("phoneNumber"),u.phone());
		type(f("ssn"),u.ssn());
		type(f("username"),u.username());
		type(f("password"),u.password());
		type(f("repeatedPassword"),u.password());
		click(By.cssSelector("input[value='Register']"));
		return this;
	} 
}
