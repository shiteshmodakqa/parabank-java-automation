package com.parabank.utils;
import java.math.BigDecimal; 
import java.math.RoundingMode;
public final class CurrencyUtils 
{
 private CurrencyUtils()
 {
	 
 }
 public static long cents(String value)
 { 
	 if(value==null||value.isBlank()) 
		 return 0; 
	 String clean=value.replace("$","").replace(",","").trim(); 
	 return new BigDecimal(clean).movePointRight(2).setScale(0,RoundingMode.HALF_UP).longValueExact(); 
	 
 }
 public static BigDecimal dollars(long cents)
 { 
	 return BigDecimal.valueOf(cents,2); 
 }
}
