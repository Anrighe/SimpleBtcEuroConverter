import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Gui 
{
	private JFrame f;
	private JLabel title;
	private JLabel labelBtc;
	private JLabel labelEuro;
	private JButton button;
	private JTextField textBtc;
	private JTextField textEuro;
	private JPanel centerPanel;
	private JPanel flowLayoutPanel;
	
	private JPanel btcPanel;
	private JPanel euroPanel;
	
	public JFrame getF()
	{
		return f;
	}
	
	public Gui(Double btcEuroValue)
	{
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
		
		flowLayoutPanel.add(btcPanel);
		flowLayoutPanel.add(euroPanel);
		flowLayoutPanel.add(button);
		
		title = new JLabel("<html><font color='blue'>1 BTC = " + btcEuroValue + " EURO</font></html>", SwingConstants.CENTER);
		title.setFont(new Font(null, 10, 30));
		
		
		f.add(title, BorderLayout.NORTH);
		centerPanel.add(flowLayoutPanel, BorderLayout.CENTER);
		
		
		f.add(centerPanel, BorderLayout.CENTER);
		
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(textBtc.getText().matches("[0-9]+(\\.[0-9]+){0,1}"))
				{
					textEuro.setForeground(Color.BLUE);
					textEuro.setText((Double.parseDouble(textBtc.getText()) * btcEuroValue) + " €");
				}
				else
				{
					textEuro.setForeground(Color.RED);
					textEuro.setText("Invalid format");
				}
			}
		});
			
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
