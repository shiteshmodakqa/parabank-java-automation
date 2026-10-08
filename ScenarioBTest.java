package com.parabank.tests;
import com.parabank.api.AdminApi; 
import com.parabank.models.User; 
import com.parabank.models.Transaction; 
import com.parabank.pages.*; 
import com.parabank.utils.*; 
import org.testng.Assert; 
import org.testng.annotations.Test; 
import java.util.*;
public class ScenarioBTest extends BaseTest 
{
 @Test(description="Scenario B - transfer aggregation and currency parsing")
 public void transactionAggregation()
 {
   
	 try 
	 { 
		 new AdminApi().cleanDatabase(); 
	  } 
	 catch(Exception e)
	 { 
		 throw new RuntimeException(e); 
	 }
   User u=TestData.newUser(); 
   LoginPage login=new LoginPage().open(); 
   login.register(); 
   new RegisterPage().register(u); 
   login.login(u.username(),u.password());
   AccountsPage accounts=new AccountsPage(); 
   accounts.openOverview(); 
   String origin=accounts.firstAccount(); 
   String destination=new OpenAccountPage().createChecking(origin);
   TransferPage transfer=new TransferPage(); 
   transfer.openPage(); 
   String[] amounts={"150.00","25.50","8.99"}; 
   for(String amount:amounts) 
	   transfer.transfer(amount,origin,destination);
   TransactionsPage tx=new TransactionsPage(); 
   tx.openPage(); 
   List<Transaction> rows=tx.transactions(origin); 
   long actual=rows.stream().filter(t->!t.debit().isBlank()).mapToLong(t->CurrencyUtils.cents(t.debit())).sum(); 
   long expected=Arrays.stream(amounts).mapToLong(CurrencyUtils::cents).sum(); 
   Assert.assertEquals(actual,expected,"Total debits parsed from HTML transaction table must equal transfers");
 }
 
}
