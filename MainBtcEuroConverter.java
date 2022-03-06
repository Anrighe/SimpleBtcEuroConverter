import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class MainBtcEuroConverter 
{
	public static void main(String[] args) throws IOException 
	{
	    //Make a URL to the web page
	    URL url = new URL("https://www.investing.com/crypto/bitcoin/btc-eur");
	    
	    
	    //<span class="text-2xl" data-test="instrument-price-last">35,100.1</span>
	    //(<!-- -->-2.08<!-- -->%)
	    
	    //Get the input stream through URL Connection
	    URLConnection con = url.openConnection();
	    InputStream is = con.getInputStream();
	    
	    System.out.println(con.getInputStream()); //debug
	    
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) 
	    {
	        String line = null;
	        String content = "";
	        
	    
	        //appending content
	        while ((line = br.readLine()) != null) 
	        {
	        	content = content + "\n" + line;	
	        }
	        
	        String patternStart = "<span class=\"text-2xl\" data-test=\"instrument-price-last\">";
	        String patternEnd = "</span>";

	        Pattern pattern = Pattern.compile(patternStart + "[0-9]+(,){1}[0-9]+\\.{0,1}[0-9]*" + patternEnd);

	        Matcher matcher = pattern.matcher(content);
	        boolean matchFound = matcher.find();
	        
	        String patternStart2 = "\\(<!-- -->";
	        String patternEnd2 = "<!-- -->%\\)";

	        Pattern pattern2 = Pattern.compile(patternStart2 + "(-|\\+)[0-9]+\\.[0-9]+" + patternEnd2);

	        Matcher matcher2 = pattern2.matcher(content);
	        boolean matchFound2 = matcher2.find();	        
	        
	        String subcontent2 = content.substring(matcher2.start(), matcher2.end());
	        System.out.println("Subcontent2: " + subcontent2); //debug
	        
	        
	        Pattern pattern3 = Pattern.compile("(-|\\+)[0-9]+\\.[0-9]+");

	        Matcher matcher3 = pattern3.matcher(subcontent2);
	        boolean matchFound3 = matcher3.find();	  
	        
	        String subcontent3 = subcontent2.substring(matcher3.start(), matcher3.end());
	        System.out.println("Subcontent3: " + subcontent3); //debug
	        

	        if(matchFound == true && matchFound2 == true && matchFound3 == true) 
	        {
	        	System.out.println("Match found"); //debug
	        	System.out.println(matcher.start()); //debug
	        	String subcontent = content.substring(matcher.start(), matcher.end());
	        	System.out.println("Subcontent: " + subcontent); //debug
	        	
	        	String refinedSubcontent = subcontent.substring(patternStart.length(), subcontent.length()-7);
	        	
	        	refinedSubcontent = refinedSubcontent.replace(",", "");
	        	//refinedSubcontent = refinedSubcontent.replace(",", ".");
	        	System.out.println("Refined Subcontent: " + refinedSubcontent); //debug
	        	
	        	Double btcEuroValue;

	        	btcEuroValue = Double.parseDouble(refinedSubcontent);

	        	
	        	
	        	//trimming the first part	        	
	        	int signIndex;
	        	boolean varSign; //fasle if negative, true if positive
	        	
	        	if (subcontent3.indexOf('+') == -1)
	        	{
	        		System.out.println("Negative"); //debug
	        		signIndex = subcontent3.indexOf('-');
	        		System.out.println("SignIndex: " + signIndex); //debug
	        		subcontent3 = subcontent3.substring(signIndex, subcontent3.length());
	        		varSign = false;
	        	}
	        	else
	        	{
	        		System.out.println("Positive"); //debug
	        		signIndex = subcontent3.indexOf('+');
	        		System.out.println("SignIndex: " + signIndex); //debug
	        		subcontent3 = subcontent3.substring(signIndex, subcontent3.length());
	        		varSign = true;
	        	}
	        	
	        	System.out.println("Refined Subcontent3: " + subcontent3); //debug
	        	String variation = subcontent3;
	        	
	        	
	        	Gui Frame = new Gui(btcEuroValue, variation, varSign);
	        	
	        	Frame.getF().setVisible(true);
	        	
	        } 
	        else 
	        {
	        	System.out.println("Match not found"); //debug
				String messaggio = "<html><font color=\"red\">Could not fetch information</html>";
				
	    		JOptionPane.showMessageDialog(null, messaggio, "Error", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	}
}
