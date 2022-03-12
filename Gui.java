import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Gui 
{
	private Double btcEuroValue;
	private JFrame f;
	private JLabel title;
	private JLabel labelBtc;
	private JLabel labelEuro;
	private JButton button;
	private JButton refresher;
	private JTextField textBtc;
	private JTextField textEuro;
	private JPanel centerPanel;
	private JPanel flowLayoutPanel;
	
	private JPanel btcPanel;
	private JPanel euroPanel;
	
	DecimalFormat numberFormat;
	
	public JFrame getF()
	{
		return f;
	}
	public JButton getRefresher()
	{
		return refresher;
	}

	public void setBtcEuroValue(Double value)
	{
		System.out.println("Old btc value: " + btcEuroValue);
		btcEuroValue = value;
		System.out.println("New btc value: " + btcEuroValue);
	}
	public void setTitle(Double value, String var, boolean sign)
	{
		System.out.println("Setting Title");
		System.out.println("Sign: " + sign);
		if (sign == true)
		{
			title.setText("<html><font color='blue'>1 BTC = " + value + " EURO <font color='green'>(" + var + ")↑</font></html>");
		}
		else
		{
			title.setText("<html><font color='blue'>1 BTC = " + value + " EURO <font color='red'>(" + var + ")↓</font></html>");
		}
	}
	
	public void calculate()
	{
		if(textBtc.getText().matches("[0-9]+(\\.[0-9]+){0,1}"))
		{
			System.out.println("BTC TEXT MATCHES");
			textEuro.setForeground(Color.BLUE);
			textEuro.setText(numberFormat.format(Double.parseDouble(textBtc.getText()) * btcEuroValue) + " €");
		}
		else
		{
			if (textBtc.getText().isEmpty() == false)
			{
				textEuro.setForeground(Color.RED);
				textEuro.setText("Invalid format");
			}
		}
	}
	
	public Gui(Double value, String variation, boolean varSign)
	{
		btcEuroValue = value;
		numberFormat = new DecimalFormat("#.#");
		
		f = new JFrame("BTC - EURO Converter");
		f.setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		flowLayoutPanel = new JPanel();
		flowLayoutPanel.setLayout(new FlowLayout());
		
		btcPanel = new JPanel();
		btcPanel.setLayout(new BorderLayout());
		labelBtc = new JLabel("BTC");
		textBtc = new JTextField(10);
		btcPanel.add(labelBtc, BorderLayout.CENTER);
		btcPanel.add(textBtc, BorderLayout.SOUTH);
		
		euroPanel = new JPanel();
		euroPanel.setLayout(new BorderLayout());
		labelEuro = new JLabel("EURO");
		textEuro = new JTextField(10);
		textEuro.setEditable(false);
		euroPanel.add(labelEuro, BorderLayout.CENTER);
		euroPanel.add(textEuro, BorderLayout.SOUTH);		
		
		button = new JButton("Convert");
		
		refresher = new JButton("Refresh");
		
		flowLayoutPanel.add(refresher);
		flowLayoutPanel.add(btcPanel);
		flowLayoutPanel.add(euroPanel);
		flowLayoutPanel.add(button);
		
		if (varSign == true)
		{
			title = new JLabel("<html><font color='blue'>1 BTC = " + btcEuroValue + " EURO <font color='green'>(" + variation + ")↑</font></html>", SwingConstants.CENTER);
		}
		else
		{
			title = new JLabel("<html><font color='blue'>1 BTC = " + btcEuroValue + " EURO <font color='red'>(" + variation + ")↓</font></html>", SwingConstants.CENTER);
		}
		
		title.setFont(new Font(null, 10, 30));
		
		
		f.add(title, BorderLayout.NORTH);
		centerPanel.add(flowLayoutPanel, BorderLayout.CENTER);
		
		
		f.add(centerPanel, BorderLayout.CENTER);
		
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				calculate();
			}
		});
		

			
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
