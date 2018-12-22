package DCMSapp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.ResultSet;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import com.toedter.calendar.JDateChooser;
import java.awt.Cursor;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;


public class ReportDate extends JFrame {
	static Connection con=null;
	static Statement st=null;
	ResultSet rs=null;
	static ReportDate frame;
	static String today;
	static String yesterday;
	static String manual;
	static String full3rd;
	static String full3rd_1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private static Image img;
    private Image scaled;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Image img = null;
					Image img2=null;
					img2 = ImageIO.read(getClass().getResource("/fi.png")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(600, 290, Image.SCALE_DEFAULT); //this is for resizing the image
					frame = new ReportDate(img);
					 frame.setIconImage(new ImageIcon(getClass().getResource("/logo1.jpg")).getImage());
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReportDate(String img) {
        this(new ImageIcon(img).getImage());
    }
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial" })
	public ReportDate(Image img) {
		 setMinimumSize(new Dimension(600, 290));
			
			this.img = img;
		
		
		
		Date one= new Date(System.currentTimeMillis()); //setting current date as default input in the date picker
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JRadioButton rdbtnToday = new JRadioButton("Today");
		rdbtnToday.setOpaque(false);
		rdbtnToday.setBounds(178, 14, 77, 31);
		JRadioButton rdbtnTommorow = new JRadioButton("Yesterday");
		rdbtnTommorow.setOpaque(false);
		rdbtnTommorow.setBounds(259, 14, 119, 31);
		JRadioButton rdbtnSpecifyManually = new JRadioButton("Specify manually");
		rdbtnSpecifyManually.setOpaque(false);
		rdbtnSpecifyManually.setBounds(378, 14, 161, 31);
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setResizable(false);
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dateChooser.setBounds(66, 102, 188, 31);
		dateChooser.setEnabled(false);
		dateChooser.setDate(one);
		setTitle("Report");
		contentPane = new JPanel()
		{
			
			 @Override
		        public void invalidate() {
		            super.invalidate();
		            int width = getWidth();
		            int height = getHeight();

		            if (width > 0 && height > 0) {
		                scaled = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		            }
		        }

		        @Override
		        public Dimension getPreferredSize() {
		            return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(this), img.getHeight(this));
		        }

		        @Override
		        public void paintComponent(Graphics g) {
		            super.paintComponent(g);
		            g.drawImage(scaled, 0, 0, null);
		        }
		};
		//contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("Select Date:");
		label.setBounds(66, 18, 104, 22);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btn_attach = new JButton("Ok");
		btn_attach.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_attach.setEnabled(false);
		btn_attach.setBounds(157, 175, 139, 31);
		btn_attach.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				
				String custom=null;
				String custom_1=null;
				String nextDate=null;
				String todaydate=null;
				boolean a= rdbtnToday.isSelected();
				boolean b= rdbtnTommorow.isSelected();
				boolean c= rdbtnSpecifyManually.isSelected();
				int count1=0,count2=0,count3=0;
				boolean isone=true;
				String half1=null;
				String half2=null;
				int exception=0;
				if(a==true)
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
					LocalDateTime now = LocalDateTime.now();
					todaydate=(dtf.format(now));
					today= todaydate;
					half1=today;
					isone=true;
					try {
						
						Class.forName(dbcon.DRIVER);
						con=DriverManager.getConnection(dbcon.JDBC_URL);
						st=con.createStatement();
						
						rs=st.executeQuery("select count(*) from patienttable");
						rs.next();
						count1=rs.getInt(1);
						rs=null;
						
						rs=st.executeQuery("select count(appointmentid) from appointmenttable where systemtime < '"+today+"'");
						rs.next();
						count2=rs.getInt(1);
						rs=null;
						
						rs=st.executeQuery("select count(*) from billtable where datetime <'"+today+"'");
						rs.next();
						count3=rs.getInt(1);
						rs=null;
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					
					
					
				}
				else if(b==true)
				{
					
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
						LocalDateTime now = LocalDateTime.now().minusDays(1);
						nextDate=(dtf.format(now));
						
						yesterday=nextDate;
						half1=yesterday;
						isone=true;
						
						try {
							
							Class.forName(dbcon.DRIVER);
							con=DriverManager.getConnection(dbcon.JDBC_URL);
							st=con.createStatement();
							
							rs=st.executeQuery("select count(*) from patienttable");
							rs.next();
							count1=rs.getInt(1);
							rs=null;
							
							rs=st.executeQuery("select count(*) from appointmenttable where systemtime <'"+yesterday+"'");
							rs.next();
							count2=rs.getInt(1);
							rs=null;
							
							rs=st.executeQuery("select count(*) from billtable where datetime <'"+yesterday+"'");
							rs.next();
							count3=rs.getInt(1);
							rs=null;
						
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    
					
				}
				
				else if(c==true)
				{
					
					String date= Integer.toString(dateChooser.getDate().getDate());
					String date_1= Integer.toString(dateChooser_1.getDate().getDate());
					int plusmonth;
					int plusmonth_1;
					plusmonth=dateChooser.getDate().getMonth();
					plusmonth_1=dateChooser_1.getDate().getMonth();
					plusmonth++;
					plusmonth_1++;
					String month= Integer.toString(plusmonth);
					String month_1= Integer.toString(plusmonth_1);
					String test = dateChooser.getDate().toString();
					String test_1 = dateChooser_1.getDate().toString();
					char[] finaldate = new char[4];
					char[] finaldate_1 = new char[4];
					test.getChars(24, 28, finaldate, 0);
					test_1.getChars(24, 28, finaldate_1, 0);
					String year=String.valueOf(finaldate);
					String year_1=String.valueOf(finaldate_1);
					custom = year+"-"+month+"-"+date+" 00:00:00";
					custom_1 = year_1+"-"+month_1+"-"+date_1+" 00:00:00";
					//taken both the date picker data!
					half1=custom;
					half2=custom_1;
					isone=false;
					
					try {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
						LocalDateTime now = LocalDateTime.now();
						String nowdate=(dtf.format(now));
						
						Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(custom); //1
						
						Date date1_1 = new SimpleDateFormat("yyyy-MM-dd").parse(custom_1); //2
						
						Date datenow = new SimpleDateFormat("yyyy-MM-dd").parse(nowdate); 
						
						
						
						
						Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(custom_1); //1
						
						Date date2_1 = new SimpleDateFormat("yyyy-MM-dd").parse(custom); //2
						
						
								int isdatevalid=date1.compareTo(date2);
								int isdatevalid_1=date1_1.compareTo(date2_1);
								
								int isdatevalid_2=date1_1.compareTo(datenow);
								int isdatevalid_3=date1.compareTo(datenow);
								
								
								if(isdatevalid<0&&isdatevalid_1>0||isdatevalid==0&&isdatevalid_1==0)
								{
									try {
										
										Class.forName(dbcon.DRIVER);
										con=DriverManager.getConnection(dbcon.JDBC_URL);
										st=con.createStatement();
										
										rs=st.executeQuery("select count(*) from patienttable");
										rs.next();
										count1=rs.getInt(1);
										rs=null;
										
										rs=st.executeQuery("select count(*) from appointmenttable where systemtime >'"+custom+ "' AND systemtime <'"+custom_1+"'");
										rs.next();
										count2=rs.getInt(1);
										rs=null;
										
										rs=st.executeQuery("select count(*) from billtable where datetime >'"+custom+ "' AND datetime < '"+custom_1+"'");
										rs.next();
										count3=rs.getInt(1);
										rs=null;
									
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
									
									
								
								}
								else if(isdatevalid_2>0||isdatevalid_3>0)
								{
									JOptionPane.showMessageDialog(null,"Date should not exceed the current date!");
									ShowReport.txtarea_pr_address.setText("");
									ShowReport.txtrDateAndTime.setText("Invalid range of date selected!!");
									exception=1;
								}
								else 
								{
									JOptionPane.showMessageDialog(null,"Please select appropriate date range!");
									ShowReport.txtarea_pr_address.setText("");
									ShowReport.txtrDateAndTime.setText("Invalid range of date selected!!");
									exception=1;
								
								}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					
					
					
				}	
				if(exception==0)
				{
					ShowReport.txtarea_pr_address.setText("");
					ShowReport.txtarea_pr_address.setText("-------------------------------------------------------\n"
							+ "TOTAL NUMBER OF PATIENTS REGISTERED TILL NOW : "+count1+""
							+ "\n"
							+ "\n"
							+ "TOTAL NO OF APPOINTMENTS TILL DATE: "+count2+""
									+ "\n"
									+ "\n"
									+ "TOTAL NO OF BILL GENERATED TILL DATE: "+count3+""
											+ "\n-------------------------------------------------------");
					if(isone==true)
					{
						
						ShowReport.txtrDateAndTime.setText("");
						ShowReport.txtrDateAndTime.setText(" SELECTED DATE(s): "+half1);
					}
					else if(isone==false)
					{
						
						ShowReport.txtrDateAndTime.setText("");
						ShowReport.txtrDateAndTime.setText(" SELECTED DATE(s): Starting: "+half1+ "\n Ending: "+half2);
					}
					
					frame.dispose();
				}
				
				}
			
		});
		btn_attach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_attach.setBackground(Color.WHITE);
		
		JButton btn_clear = new JButton("Clear");
		btn_clear.setForeground(Color.WHITE);
		btn_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnToday.setSelected(false);
				rdbtnTommorow.setSelected(false);
				rdbtnSpecifyManually.setSelected(false);
				dateChooser.setEnabled(false);
				dateChooser_1.setEnabled(false);
			}
		});
		btn_clear.setBounds(314, 175, 139, 31);
		btn_clear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_clear.setBackground(Color.RED);
		
		rdbtnSpecifyManually.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a= rdbtnSpecifyManually.isSelected();
				if(a==true)
				{
					rdbtnToday.setSelected(false);
					rdbtnTommorow.setSelected(false);
					dateChooser.setEnabled(true);
					dateChooser_1.setEnabled(true);
					btn_attach.setEnabled(true);
					
				}
				else
				{
					dateChooser.setEnabled(false);
					dateChooser_1.setEnabled(false);
						btn_attach.setEnabled(false);
					
				}
			}
		});
		
		rdbtnTommorow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a= rdbtnTommorow.isSelected();
				if(a==true)
				{
					rdbtnToday.setSelected(false);
					rdbtnSpecifyManually.setSelected(false);
					dateChooser.setEnabled(false);
					dateChooser_1.setEnabled(false);
					btn_attach.setEnabled(true);
				}
				else
				{
					btn_attach.setEnabled(false);
				}
			}
		});
		
		rdbtnToday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a= rdbtnToday.isSelected();
				if(a==true)
				{
					rdbtnTommorow.setSelected(false);
					rdbtnSpecifyManually.setSelected(false);
					dateChooser.setEnabled(false);
					dateChooser_1.setEnabled(false);
					btn_attach.setEnabled(true);
				}
				else
				{
					btn_attach.setEnabled(false);
				}
			}
		});
		rdbtnToday.setBackground(Color.WHITE);
		rdbtnToday.setForeground(Color.BLACK);
		rdbtnToday.setFont(new Font("Tahoma", Font.PLAIN, 18));
				
		rdbtnTommorow.setBackground(Color.WHITE);
		rdbtnTommorow.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		rdbtnSpecifyManually.setBackground(Color.WHITE);
		rdbtnSpecifyManually.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooser.setBackground(Color.WHITE);
		dateChooser.setDateFormatString("dd-MM-yyyy");
		
		contentPane.setLayout(null);
		contentPane.add(dateChooser);
		contentPane.add(btn_attach);
		contentPane.add(btn_clear);
		contentPane.add(label);
		contentPane.add(rdbtnToday);
		contentPane.add(rdbtnTommorow);
		contentPane.add(rdbtnSpecifyManually);
		try
		{
			try {
				Class.forName(dbcon.DRIVER);
				con=DriverManager.getConnection(dbcon.JDBC_URL);
				st=con.createStatement();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			rs=st.executeQuery("select max (appointmentid) from appointmenttable");
			rs.next();
			int id=rs.getInt(1);
			if(id>0)
			{
				id=id+1;
			}
			else 
			{
				id=1;
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		dateChooser_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooser_1.setEnabled(false);
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setBackground(Color.WHITE);
		dateChooser_1.setDate(one);
		dateChooser_1.setEnabled(false);
		dateChooser_1.setBounds(351, 102, 188, 31);
		
		contentPane.add(dateChooser_1);
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFrom.setBounds(66, 67, 104, 22);
		contentPane.add(lblFrom);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTo.setBounds(351, 67, 104, 22);
		contentPane.add(lblTo);
	}
}
