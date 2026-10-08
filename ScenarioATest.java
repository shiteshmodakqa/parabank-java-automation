package com.parabank.tests;
import com.parabank.api.AdminApi; 
import com.parabank.models.User; 
import com.parabank.pages.*; 
import com.parabank.utils.TestData; 
import org.testng.Assert; 
import org.testng.annotations.Test;
public class ScenarioATest extends BaseTest 
{
 @Test(description="Scenario A - global state, dynamic account and loan orchestration")
 public void loanOrchestration()
 {
   try 
   { 
	   
	   new AdminApi().cleanDatabase(); 
	   
   } 
   catch(Exception e)
   { 
	   throw new RuntimeException("API database cleanup failed",e); 
	   
   }
   AdminPage admin=new AdminPage(); 
   admin.openPage(); 
   admin.selectLoanProvider("Web Service");
   User u=TestData.newUser();
   LoginPage login=new LoginPage().open(); 
   login.register(); 
   new RegisterPage().register(u); 
   login.login(u.username(),u.password());
   AccountsPage accounts=new AccountsPage(); 
   accounts.openOverview(); 
   String source=accounts.firstAccount();
   OpenAccountPage open=new OpenAccountPage(); 
   open.openPage(); 
   String checking=open.createChecking(source); 
   Assert.assertTrue(checking.matches("\\d+"),"Checking account ID should be numeric");
   LoanPage loan=new LoanPage(); 
   loan.openPage(); 
   loan.apply(checking,"1000","100"); 
   String body=loan.response(); 
   Assert.assertTrue(body.toLowerCase().contains("approved"),"Loan should be approved: "+body); 
   String loanAccount=loan.loanAccount(); 
   Assert.assertTrue(loanAccount.matches("\\d+"));
   // ParaBank loan approval deposits the approved amount into the new loan account; verify via account details UI.
   driver().get(com.parabank.config.Config.baseUrl()+"/activity.htm?id="+loanAccount); 
   String page=driver().getPageSource(); 
   Assert.assertTrue(page.contains("900")||page.contains("1000"),"Expected loan deposit to be visible for loan account "+loanAccount);
   
 }
 private org.openqa.selenium.WebDriver driver()
 {
	 return com.parabank.driver.DriverFactory.get();
 }
}
