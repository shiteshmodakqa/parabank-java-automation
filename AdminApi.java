package com.parabank.api;
import com.parabank.config.Config; 
import java.net.*; 
import java.net.http.*; 
import java.nio.charset.StandardCharsets;
public class AdminApi 
{
 private final HttpClient client=HttpClient.newHttpClient();
 private String soap(String action,String inner) throws Exception 
 { 
	 String env="<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:par=\"http://service.parabank.parasoft.com/\"><soap:Body><par:"+action+">"+inner+"</par:"+action+"></soap:Body></soap:Envelope>"; 
	 HttpRequest r=HttpRequest.newBuilder(URI.create(Config.baseUrl()+"/services/ParaBank")).header("Content-Type","text/xml; charset=utf-8").POST(HttpRequest.BodyPublishers.ofString(env,StandardCharsets.UTF_8)).build(); 
	 HttpResponse<String> x=client.send(r,HttpResponse.BodyHandlers.ofString()); 
	 if(x.statusCode()>=300) 
		 throw new RuntimeException("Admin SOAP failed: "+x.statusCode()+" "+x.body()); 
	 return x.body(); 
}
 public void cleanDatabase() throws Exception 
 { 
	 soap("cleanDB",""); 
}
 public void initializeDatabase() throws Exception 
 { soap("initializeDB",""); 
 }
 public void setParameter(String name,String value) throws Exception 
 { soap("setParameter","<par:name>"+escape(name)+"</par:name><par:value>"+escape(value)+"</par:value>"); 
 }
 private String escape(String s)
 {
	 return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
  }
}
