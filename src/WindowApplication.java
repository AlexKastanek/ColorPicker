import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

class ColorSampler extends JComponent
{

	public ColorSampler (Color c)
	{
		sample = c;
	}
	
	public void paint (Graphics g)
	{
		
		Dimension d = getSize();
		
		g.setColor(sample);
		g.fillRect(1, 1, d.width-2, d.height-2);
		
		g.setPaintMode();
		
	}
	
	public void setColor (Color c)
	{
		sample = c;
		repaint();
	}
	
	private Color sample;

}

class MyColor
{
	
	public MyColor(String sourceName, int sourceR, int sourceG, int sourceB)
	{
		name = sourceName;
		r = sourceR;
		g = sourceG;
		b = sourceB;
	}
	
	public Color getColor()
	{
		return new Color(r, g, b);
	}
	
	public String getColorName()
	{
		return name;
	}
	
	public int getRValue()
	{
		return r;
	}
	
	public int getGValue()
	{
		return g;
	}
	
	public int getBValue()
	{
		return b;
	}
	
	public void setColor(int sourceR, int sourceG, int sourceB)
	{
		r = sourceR;
		g = sourceG;
		b = sourceB;
	}
	
	private String name;
	private int r;
	private int g;
	private int b;
	
}

public class WindowApplication extends JFrame
{
	protected static WindowApplication application;
	
	protected JButton buttonDone;
	protected JButton buttonCancel;
	protected JTextField tfFirstName;
	protected JTextField tfLastName;
	
	protected ColorSampler colorTest;
	//protected Graphics g;
	protected JLabel labelRed;
	protected JLabel labelGreen;
	protected JLabel labelBlue;
	protected JButton buttonSave;
	protected JButton buttonReset;
	protected JButton redDecrement, redIncrement;
	protected JButton greenDecrement, greenIncrement;
	protected JButton blueDecrement, blueIncrement;
	protected JTextField redVal, greenVal, blueVal;
	protected JList listColors;
	
	public static MyColor fileColors [];
	public static MyColor newColors [];
	public static int totalColors = 0;
	
	public static void main(String argv []) throws IOException
	{
		FileInputStream stream = new FileInputStream("input.txt");  
		InputStreamReader reader = new InputStreamReader(stream); 
		StreamTokenizer tokens = new StreamTokenizer(reader);
		
		//int totalColors = 0;
		while(tokens.nextToken() != tokens.TT_EOF)
		{
			tokens.nextToken(); 
			tokens.nextToken(); 
			tokens.nextToken();
			totalColors++;
		}
		stream.close();
		
		stream = new FileInputStream("input.txt");  
		reader = new InputStreamReader(stream); 
		tokens = new StreamTokenizer(reader);
		
		String	s;
		int	r, g, b;
		int colorCount = 0;
		//MyColor fileColors [];
		fileColors = new MyColor [totalColors];
		newColors = new MyColor [totalColors];
		while (tokens.nextToken() != tokens.TT_EOF) 
		{  
			s = (String) tokens.sval; 
			tokens.nextToken(); 
			r = (int) tokens.nval; 
			tokens.nextToken(); 
			g = (int) tokens.nval;
			tokens.nextToken();
			b = (int) tokens.nval;
			//tokens.nextToken();
			
			fileColors[colorCount] = new MyColor(s,r,g,b);
			newColors[colorCount] = new MyColor(s,r,g,b);
			colorCount++;
		}
		
		stream.close();
		
		application = new WindowApplication("Color Sampler");
	}
	
	public WindowApplication(String title)
	{
		super(title);
		setBounds(100, 100, 300, 320);
		addWindowListener(new WindowDestroyer());                 
		
		colorTest = new ColorSampler(Color.white);
		labelRed = new JLabel("Red");
		labelGreen = new JLabel("Green");
		labelBlue = new JLabel("Blue");
		buttonSave = new JButton("Save");
		buttonReset = new JButton("Reset");
		redDecrement = new JButton("-");
		redIncrement = new JButton("+");
		greenDecrement = new JButton("-");
		greenIncrement = new JButton("+");
		blueDecrement = new JButton("-");
		blueIncrement = new JButton("+");
		redVal = new JTextField("255");
		greenVal = new JTextField("255");
		blueVal = new JTextField("255");
		listColors = new JList();
		
		buttonSave.addActionListener(new ActionHandler());
		buttonReset.addActionListener(new ActionHandler());
		redDecrement.addActionListener(new ActionHandler());
		redIncrement.addActionListener(new ActionHandler());
		redVal.getDocument().addDocumentListener(new DocumentHandler());
		greenDecrement.addActionListener(new ActionHandler());
		greenIncrement.addActionListener(new ActionHandler());
		greenVal.getDocument().addDocumentListener(new DocumentHandler());
		blueDecrement.addActionListener(new ActionHandler());
		blueIncrement.addActionListener(new ActionHandler());
		blueVal.getDocument().addDocumentListener(new DocumentHandler());
		listColors.addListSelectionListener(new ListHandler());
		
		getContentPane().setLayout(null);
		
		getContentPane().add(colorTest);
		getContentPane().add(labelRed);
		getContentPane().add(labelGreen);
		getContentPane().add(labelBlue);
		getContentPane().add(buttonSave);
		getContentPane().add(buttonReset);
		getContentPane().add(redDecrement);
		getContentPane().add(redIncrement);
		getContentPane().add(greenDecrement);
		getContentPane().add(greenIncrement);
		getContentPane().add(blueDecrement);
		getContentPane().add(blueIncrement);
		getContentPane().add(redVal);
		getContentPane().add(greenVal);
		getContentPane().add(blueVal);
		getContentPane().add(listColors);
		
		colorTest.setBounds(10, 10, 170, 100);
		labelRed.setBounds(10, 130, 60, 30);
		labelGreen.setBounds(5, 170, 60, 30);
		labelBlue.setBounds(10, 210, 60, 30);
		buttonSave.setBounds(25, 260, 60, 20);
		buttonReset.setBounds(105, 260, 60, 20);
		redDecrement.setBounds(80, 130, 60, 30);
		redIncrement.setBounds(130, 130, 60, 30);
		greenDecrement.setBounds(80, 170, 60, 30);
		greenIncrement.setBounds(130, 170, 60, 30);
		blueDecrement.setBounds(80, 210, 60, 30);
		blueIncrement.setBounds(130, 210, 60, 30);
		redVal.setBounds(40, 130, 45, 30);
		greenVal.setBounds(40, 170, 45, 30);
		blueVal.setBounds(40, 210, 45, 30);
		listColors.setBounds(190, 10, 100, 280);
		
		String colorNames[] = new String [totalColors];
		for (int i = 0; i < totalColors; i++)
		{
			colorNames[i] = newColors[i].getColorName();
		}
		listColors.setListData(colorNames);
		
		setVisible(true);
		
	}
	
	private class DocumentHandler implements DocumentListener
	{
		
		public void changedUpdate(DocumentEvent e)
		{
			updateData();
		}
		public void removeUpdate(DocumentEvent e)
		{
			
			if (e.getDocument().equals(redVal.getDocument()))
			{
				if (redVal.getText().length() > 0)
				{
					//System.out.print(redText);
					updateData();
				}
				
			}
			if (e.getDocument().equals(greenVal.getDocument()))
			{
				if (greenVal.getText().length() > 0)
				{
					updateData();
				}
				
			}
			if (e.getDocument().equals(blueVal.getDocument()))
			{
				if (blueVal.getText().length() > 0)
				{
					updateData();
				}
				
			}
		}
		public void insertUpdate(DocumentEvent e)
		{
			if (e.getDocument().equals(redVal.getDocument()))
			{
				if (redVal.getText().length() < 4)
				{
					//System.out.print(redText);
					updateData();
				}
				
			}
			if (e.getDocument().equals(greenVal.getDocument()))
			{
				if (greenVal.getText().length() < 4)
				{
					updateData();
				}
				
			}
			if (e.getDocument().equals(blueVal.getDocument()))
			{
				if (blueVal.getText().length() < 4)
				{
					updateData();
				}
				
			}
		}
		
		public void updateData()
		{
			
			fixData();
			
			int r = Integer.parseInt(redVal.getText());
			int g = Integer.parseInt(greenVal.getText());
			int b = Integer.parseInt(blueVal.getText());
			
			colorTest.setColor(new Color(r,g,b));
			
		}
		
		void fixData()
		{ 
			
			if (redVal.getText().isEmpty())
			{
				redVal.setText("0");
			}
			if (greenVal.getText().isEmpty())
			{
				greenVal.setText("0");
			}
			if (blueVal.getText().isEmpty())
			{
				blueVal.setText("0");
			}
			if (redVal.getText().length() >= 4)
			{
				redVal.setText("255");
			}
			if (greenVal.getText().length() >= 4)
			{
				greenVal.setText("255");
			}
			if (blueVal.getText().length() >= 4)
			{
				blueVal.setText("255");
			}
			
		}
	}
	
	// Define action listener                                       
	private class ActionHandler implements ActionListener 
	{      
		public void actionPerformed(ActionEvent e)
		{
			
			if (e.getSource() == buttonSave)
			{
				int i = listColors.getSelectedIndex();
				newColors[i].setColor(Integer.parseInt(redVal.getText()), Integer.parseInt(greenVal.getText()), Integer.parseInt(blueVal.getText()));
				
				changeTitle('S');
			}
			else if (e.getSource() == buttonReset)
			{
				for (int i = 0; i < totalColors; i++)
				{
					newColors[i].setColor(fileColors[i].getRValue(), fileColors[i].getGValue(), fileColors[i].getBValue());
				}
				int j = listColors.getSelectedIndex();
				redVal.setText(Integer.toString(fileColors[j].getRValue()));
				greenVal.setText(Integer.toString(fileColors[j].getGValue()));
				blueVal.setText(Integer.toString(fileColors[j].getBValue()));
				
				changeTitle('S');
				updateData();
			}
			else if (e.getSource() == redDecrement && Integer.parseInt(redVal.getText()) > 4)
			{
				int red = Integer.parseInt(redVal.getText());
				red = red - 5;
				redVal.setText(Integer.toString(red));
				changeTitle('U');
				updateData();
			}
			else if (e.getSource() == redIncrement && Integer.parseInt(redVal.getText()) < 251)
			{
				int red = Integer.parseInt(redVal.getText());
				red = red + 5;
				redVal.setText(Integer.toString(red));
				changeTitle('U');
				updateData();
			}
			else if (e.getSource() == greenDecrement && Integer.parseInt(greenVal.getText()) > 4)
			{
				int green = Integer.parseInt(greenVal.getText());
				green = green - 5;
				greenVal.setText(Integer.toString(green));
				changeTitle('U');
				updateData();
			}
			else if (e.getSource() == greenIncrement && Integer.parseInt(greenVal.getText()) < 251)
			{
				int green = Integer.parseInt(greenVal.getText());
				green = green + 5;
				greenVal.setText(Integer.toString(green));
				changeTitle('U');
				updateData();
			}
			else if (e.getSource() == blueDecrement && Integer.parseInt(blueVal.getText()) > 4)
			{
				int blue = Integer.parseInt(blueVal.getText());
				blue = blue - 5;
				blueVal.setText(Integer.toString(blue));
				changeTitle('U');
				updateData();
			}
			else if (e.getSource() == blueIncrement && Integer.parseInt(blueVal.getText()) < 251)
			{
				int blue = Integer.parseInt(blueVal.getText());
				blue = blue + 5;
				blueVal.setText(Integer.toString(blue));
				changeTitle('U');
				updateData();
			}
			
		}
		
		void changeTitle(char titleType)
		{
			JTextField newTitle = new JTextField();
			
			if (titleType == 'U')
			{
				newTitle.setText("Color Sampler*");
			}
			else if (titleType == 'S')
			{
				newTitle.setText("Color Sampler");
			}
			
			application.setTitle(newTitle.getText());
		}
		
		void updateData()
		{
			int r = Integer.parseInt(redVal.getText());
			int g = Integer.parseInt(greenVal.getText());
			int b = Integer.parseInt(blueVal.getText());
			
			colorTest.setColor(new Color(r,g,b));
			
		}
	}
	
	private class ListHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if (e.getSource() == listColors)
			{
				if (!e.getValueIsAdjusting())
				{			
					JTextField newTitle = new JTextField();
					newTitle.setText("Color Sampler");
					application.setTitle(newTitle.getText());
					
					int i = listColors.getSelectedIndex();
					
					redVal.setText(Integer.toString(newColors[i].getRValue()));
					greenVal.setText(Integer.toString(newColors[i].getGValue()));
					blueVal.setText(Integer.toString(newColors[i].getBValue()));
				}
			}
		}
	}
	
	// Define window adapter                                       
	private class WindowDestroyer extends WindowAdapter
	{   
		public void outputToFile() throws IOException
		{
			FileOutputStream ostream = new FileOutputStream("input.txt");
			PrintWriter writer = new PrintWriter(ostream);
			
			for (int i = 0; i < totalColors; i++)
			{
				writer.println(newColors[i].getColorName() + " " + newColors[i].getRValue() + " " + newColors[i].getGValue() + " " + newColors[i].getBValue());
			}
			
			writer.flush();
			ostream.close(); 
			System.out.println("File written.");
		}
		
		public void windowClosing(WindowEvent e)
		{   
			try {
				outputToFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);  
		}                                                             
	}                                                              
}
		
		