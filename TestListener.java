package com.parabank.listeners;

import com.parabank.driver.DriverFactory;
import org.testng.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import org.testng.xml.XmlSuite;
public class TestListener implements ITestListener, IReporter 
{
  private final List<ITestResult> results=Collections.synchronizedList(new ArrayList<>());
  public void onTestSuccess(ITestResult r)
  {
	  results.add(r);
  }
  public void onTestFailure(ITestResult r)
  {
	  results.add(r);
  }
  public void onTestSkipped(ITestResult r)
  {
	  results.add(r);
   }
  public void generateReport(List<XmlSuite> xmlSuites,List<ISuite> suites,String outputDirectory)
  {
    Path dir=Paths.get("reports"); 
    try{
    	Files.createDirectories(dir);
    	}
    catch(IOException e)
    {
    	throw new RuntimeException(e);
    }
    int pass=0,fail=0,skip=0; 
    StringBuilder rows=new StringBuilder();
    for(ITestResult r:results)
    {
    	String status=r.getStatus()==ITestResult.SUCCESS?"PASS":r.getStatus()==ITestResult.FAILURE?"FAIL":"SKIP";
    	if(status.equals("PASS"))
    		pass++;
    	else if(status.equals("FAIL"))
    		fail++;
    	else skip++; 
    	String cls=r.getTestClass().getName();
    	String err=r.getThrowable()==null?"":escape(r.getThrowable().toString()); 
    	rows.append("<tr><td>").append(escape(cls)).append("</td><td>").append(escape(r.getName())).append("</td><td class='"+status.toLowerCase()+"'>").append(status).append("</td><td>").append(err).append("</td></tr>");
    	}
    String html="""
S
        <!doctype html>
        <html>
        <head>
        <meta charset='UTF-8'>
        <title>ParaBank QA Dashboard</title>

        <style>
        :root{
            --accent:#F48031;
            --bg:#0f172a;
            --card:rgba(255,255,255,.10);
            --border:rgba(255,255,255,.18)
        }

        *{
            box-sizing:border-box
        }

        body{
            margin:0;
            font-family:Inter,Arial,sans-serif;
            color:#f8fafc;
            background:
                radial-gradient(
                    circle at 10%% 10%%,
                    rgba(244,128,49,.22),
                    transparent 30%%
                ),
                linear-gradient(
                    135deg,
                    #0b1020,
                    #172033 55%%,
                    #111827
                );
            min-height:100vh;
            padding:36px
        }

        .wrap{
            max-width:1200px;
            margin:auto
        }

        .hero{
            display:flex;
            justify-content:space-between;
            align-items:center;
            margin-bottom:28px
        }

        .hero h1{
            margin:0;
            color:var(--accent);
            font-size:32px
        }

        .hero p{
            color:#cbd5e1
        }

        .grid{
            display:grid;
            grid-template-columns:repeat(4,1fr);
            gap:18px
        }

        .card{
            background:var(--card);
            border:1px solid var(--border);
            border-radius:20px;
            padding:22px;
            backdrop-filter:blur(16px);
            -webkit-backdrop-filter:blur(16px);
            box-shadow:0 12px 40px rgba(0,0,0,.25)
        }

        .metric{
            font-size:34px;
            font-weight:800;
            margin-top:8px
        }

        .accent{
            color:var(--accent)
        }

        .panel{
            margin-top:24px
        }

        .tabs{
            display:flex;
            gap:10px;
            margin-bottom:14px
        }

        .tab{
            background:rgba(255,255,255,.08);
            padding:10px 16px;
            border-radius:999px
        }

        .active{
            background:var(--accent);
            color:white
        }

        .table{
            overflow:hidden;
            border-radius:18px
        }

        .table table{
            width:100%%;
            border-collapse:collapse;
            background:rgba(255,255,255,.06)
        }

        th,td{
            text-align:left;
            padding:14px;
            border-bottom:1px solid rgba(255,255,255,.1)
        }

        th{
            color:var(--accent)
        }

        .pass{
            color:#F48031;
            font-weight:800
        }

        .fail{
            color:#fb7185;
            font-weight:800
        }

        .skip{
            color:#fbbf24;
            font-weight:800
        }

        .footer{
            margin-top:22px;
            color:#94a3b8;
            font-size:13px
        }

        @media(max-width:800px){
            .grid{
                grid-template-columns:1fr 1fr
            }
        }
        </style>

        </head>

        <body>

        <div class='wrap'>

            <div class='hero'>

                <div>
                    <h1>ParaBank QA Dashboard</h1>
                    <p>
                        Custom Selenium WebDriver + TestNG execution report
                    </p>
                </div>

                <div class='card' style='padding:12px 18px'>
                    <b class='accent'>#F48031</b>
                </div>

            </div>

            <div class='grid'>

                <div class='card'>
                    <div>Total</div>
                    <div class='metric'>%d</div>
                </div>

                <div class='card'>
                    <div>Passed</div>
                    <div class='metric accent'>%d</div>
                </div>

                <div class='card'>
                    <div>Failed</div>
                    <div class='metric'>%d</div>
                </div>

                <div class='card'>
                    <div>Skipped</div>
                    <div class='metric'>%d</div>
                </div>

            </div>

            <div class='panel'>

                <div class='tabs'>
                    <div class='tab active'>Execution Results</div>
                    <div class='tab'>Glassmorphism UI</div>
                </div>

                <div class='table'>

                    <table>

                        <thead>
                            <tr>
                                <th>Class</th>
                                <th>Test</th>
                                <th>Status</th>
                                <th>Error</th>
                            </tr>
                        </thead>

                        <tbody>
                            %s
                        </tbody>

                    </table>

                </div>

            </div>

            <div class='footer'>
                Generated %s • Accent requirement: #F48031 • Static waits: prohibited
            </div>

        </div>

        </body>
        </html>
        """.formatted(
                pass + fail + skip,
                pass,
                fail,
                skip,
                rows,
                Instant.now()
        );

try {
    Files.writeString(dir.resolve("index.html"), html);
} catch (IOException e) {
    throw new RuntimeException(e);
}


    try
    {
    	
    	Files.writeString(dir.resolve("index.html"),html);
    }
    catch(IOException e)
    {
    	throw new RuntimeException(e);
    }
  }
  private String escape(String s)
  {
	  return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
   }
}
