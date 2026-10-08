package com.parabank.utils;
import com.parabank.models.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
public final class TestData 
{
 private TestData()
 {
	 
 }
 public static User newUser()
 { 
	 String id=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+UUID.randomUUID().toString().substring(0,5); 
	 return new User("QA","Engineer","Automation Street","Pune","Maharashtra","411057","9999999999","SSN"+id.substring(id.length()-8),"qa_"+id,"Pwd@12345"); 
	 
 }
}
