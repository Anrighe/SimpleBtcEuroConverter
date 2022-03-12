import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static double btcEuroValue;
	private static String variation;
	private static boolean varSign;
	
	static ReturningValues fetchData(Double btcEuroValue, String variation, boolean varSign)
	{
		
		try 
		{
	    //Make a URL to the web page
	    URL url = new URL("https://www.investing.com/crypto/bitcoin/btc-eur");
	    
	    //NEGATIVE VARIATION EXAMPLE
	    //<span class="text-2xl" data-test="instrument-price-last">35,100.1</span>
	    //(<!-- -->-2.08<!-- -->%)
	    
	    //POSITIVE VARIATION EXAMPLE
	    //<span class="text-2xl" data-test="instrument-price-last">35,814.9</span>
	    //(<!-- -->+<!-- -->0.59<!-- -->%)
	    
	    //Get the input stream through URL Connection
	    URLConnection con = url.openConnection();
	    InputStream is = con.getInputStream();
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    
	    String line = null;
	    String content = "";
	        
	    //appending html to content
	    while ((line = br.readLine()) != null) 
	    {
	    	content = content + "\n" + line;	
	    }
	        
        String patternStart = "<span class=\"text-2xl\" data-test=\"instrument-price-last\">";
        String patternEnd = "</span>";

        Pattern pattern = Pattern.compile(patternStart + "[0-9]+(,){1}[0-9]+\\.{0,1}[0-9]*" + patternEnd);

        Matcher matcher = pattern.matcher(content);
        boolean matchFound = matcher.find();
        
        
        String positiveIncrementStart = "\\(<!-- -->\\+<!-- -->";
        String positiveIncrementEnd = "<!-- -->%\\)";
        
        Pattern positiveIncrement = Pattern.compile(positiveIncrementStart + "[0-9]+\\.[0-9]+" + positiveIncrementEnd);
        Matcher positiveMatcher = positiveIncrement.matcher(content);
        boolean incrementFound = positiveMatcher.find();	
        
        if(matchFound == true) //If the value of BTC has been found
        {
        	String value = content.substring(matcher.start(), matcher.end());
        	System.out.println("Value: " + value);
        	String refinedValue = value.substring(patternStart.length(), value.length()-patternEnd.length());
        	refinedValue = refinedValue.replace(",", "");
        	System.out.println("RefinedValue: " + refinedValue);
        	
        	btcEuroValue = Double.parseDouble(refinedValue);
        	
        
        	
        	if (incrementFound == true) //If there is a postive variation to BTC value
        	{
        		System.out.println("There is a positive variation"); //debug
        		varSign = true; //Segno positivo
        		String positiveSubcontent = content.substring(positiveMatcher.start(), positiveMatcher.end());
        		System.out.println("PositiveSubcontent: " + positiveSubcontent); //debug
        		String refinedPositive = positiveSubcontent.substring(positiveIncrementStart.length()-2, positiveSubcontent.length()-positiveIncrementEnd.length()+1); //i -1 e +1 sono dovuti ai doppi backslash nelle regex
        		System.out.println("RefinedPositive: " + refinedPositive); //debug
        		variation = refinedPositive;
        	}
        	else //If there is a negative variation to BTC value
        	{
        		String negativeDecrementStart = "\\(<!-- -->-";
        		String negativeDecrementEnd = "<!-- -->%\\)";
        		
        		Pattern negativeDecrement = Pattern.compile(negativeDecrementStart + "[0-9]+\\.[0-9]+" + negativeDecrementEnd);
        		Matcher negativeMatcher = negativeDecrement.matcher(content);
        		boolean decrementFound = negativeMatcher.find();
        		
        		if (decrementFound == true)
        		{
        			System.out.println("There is a negative variation"); //debug
        			varSign = false; //Segno negativo
            		String negativeSubcontent = content.substring(negativeMatcher.start(), negativeMatcher.end());
            		System.out.println("NegativeSubcontent: " + negativeSubcontent); //debug
            		String refinedNegative = negativeSubcontent.substring(negativeDecrementStart.length()-1, negativeSubcontent.length()-negativeDecrementEnd.length()+1); //i -1 e +1 sono dovuti ai doppi backslash nelle regex
            		System.out.println("RefinedNegative: " + refinedNegative); //debug
            		variation = refinedNegative;
            		
        		}
        	}
        	
        }
        else 
        {
        	System.out.println("Match not found"); //debug
			String messaggio = "<html><font color=\"red\">Could not fetch information</html>";
			
    		JOptionPane.showMessageDialog(null, messaggio, "Error", JOptionPane.INFORMATION_MESSAGE);
    		return new ReturningValues(-1.0, "NaN", false);
        }
	    }
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		ReturningValues fetchedData = new ReturningValues(btcEuroValue, variation, varSign);
		return fetchedData;
	}
	
	
	
	public static void main(String[] args) 
	{
		ReturningValues fetchedData = fetchData(btcEuroValue, variation, varSign);
		System.out.println("Fetched BtcEuroValue: " + fetchedData.btcEuroValue);
		System.out.println("Fetched Variation: " + fetchedData.variation);
		System.out.println("Fetched VarSign: " + fetchedData.varSign);
		
    	Gui frame = new Gui(fetchedData.btcEuroValue, fetchedData.variation, fetchedData.varSign);
    	
    	frame.getF().setVisible(true);
		frame.getRefresher().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Refresh Button"); //debug
				ReturningValues fetchedData = fetchData(btcEuroValue, variation, varSign);
				frame.setBtcEuroValue(fetchedData.btcEuroValue);
				frame.setTitle(fetchedData.btcEuroValue, fetchedData.variation, fetchedData.varSign);
				frame.calculate();
				
			}
		});
	}
}
