import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainBtcEuroConverter 
{
	public static void main(String[] args) throws IOException 
	{
	    //Make a URL to the web page
	    URL url = new URL("https://www.money.it/+Bitcoin-Euro-Quotazione+");
	    
	    //Get the input stream through URL Connection
	    URLConnection con = url.openConnection();
	    InputStream is = con.getInputStream();
	    
	    System.out.println(con.getInputStream()); //debug
	    
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) 
	    {
	        String line = null;
	        String content = "";
	    
	        //read each line and write to System.out
	        while ((line = br.readLine()) != null) 
	        {
	            //appending content
	        	content = content + "\n" + line;	
	        }
	        
	        String patternStart = "<p id=\"price\" class=\"text-4xl flex items-center price\">";
	        String patternEnd = "</p>";

	        Pattern pattern = Pattern.compile(patternStart + ".*" + patternEnd);

	        Matcher matcher = pattern.matcher(content);
	        boolean matchFound = matcher.find();
	        
	        
	        if(matchFound == true) 
	        {
	        	System.out.println("Match found"); //debug
	        	System.out.println(matcher.start()); //debug
	        	String subcontent = content.substring(matcher.start(), matcher.end());
	        	System.out.println("Subcontent: " + subcontent); //debug
	        	
	        	String refinedSubcontent = subcontent.substring(patternStart.length(), subcontent.length()-4);
	        	refinedSubcontent = refinedSubcontent.replace(",", ".");
	        	System.out.println("Refined Subcontent: " + refinedSubcontent); //debug
	        	
	        	Double btcEuroValue;

	        	btcEuroValue = Double.parseDouble(refinedSubcontent);
	        	
	        	Gui Frame = new Gui(btcEuroValue);
	        	Frame.getF().setVisible(true);
	        	
	        } 
	        else 
	        {
	        	System.out.println("Match not found"); //debug
	        }
	    }
	}
}
