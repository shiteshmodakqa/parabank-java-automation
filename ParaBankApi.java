package com.parabank.api;
import com.fasterxml.jackson.databind.*; 
import com.parabank.config.Config; 
import java.io.*; 
import java.net.*; 
import java.net.http.*; 
import java.nio.charset.StandardCharsets; 
import java.util.*;
public class ParaBankApi 
{
 private final HttpClient client=HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build(); 
 private final ObjectMapper mapper=new ObjectMapper();
 private HttpResponse<String> send(String method,String url,String body) throws Exception 
 { 
	 HttpRequest.Builder b=HttpRequest.newBuilder(URI.create(url)).header("Accept","application/json"); 
	 if(body!=null) 
		 b.header("Content-Type","application/x-www-form-urlencoded"); 
	 if(method.equals("POST")) 
		 b.POST(HttpRequest.BodyPublishers.ofString(body==null?"":body)); 
	 else 
		 b.GET(); 
	 return client.send(b.build(),HttpResponse.BodyHandlers.ofString()); 
}
 public String get(String path) throws Exception 
 { 
	 return send("GET",Config.apiBase()+path,null).body(); 
}
 public String post(String path,Map<String,String> params) throws Exception 
 { 
	 String body=String.join("&",params.entrySet().stream().map(e->URLEncoder.encode(e.getKey(),StandardCharsets.UTF_8)+"="+URLEncoder.encode(e.getValue(),StandardCharsets.UTF_8)).toList()); 
	 HttpResponse<String> r=send("POST",Config.apiBase()+path,body); 
	 if(r.statusCode()>=300) 
		 throw new IOException("HTTP "+r.statusCode()+": "+r.body()); 
	 return r.body(); 
}
 public JsonNode json(String path) throws Exception 
 { 
	 return mapper.readTree(get(path)); 
}
 public JsonNode postJson(String path,Map<String,String> p) throws Exception 
 { 
	 return mapper.readTree(post(path,p)); 
}
 public ObjectMapper mapper()
 {
	 return mapper;
 }
}
