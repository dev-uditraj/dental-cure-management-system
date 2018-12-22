package DCMSapp;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;

public class SearchInfoDentistt extends JFrame {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	static long patientid;
	@SuppressWarnings("unused")
	private static Image img;
    private Image scaled;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Image img = null;
					Image img2=null;
					img2 = ImageIO.read(getClass().getResource("/c.jpg")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(610, 635, Image.SCALE_DEFAULT); //this is for resizing the image
					SearchInfoDentistt frame = new SearchInfoDentistt(img);
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextField txtSearch;
	static JRadioButton rdbtnRegistrationId;
	static JRadioButton rdbtnAppointmentId;
	static JRadioButton rdbtnContactNo;
	 static JRadioButton rdbtnCurrent;
	static long patient_id;
	static JTextArea textAreaDetails;
	static String no;
	static int noo;
	static long no1;
	static long appointmentid;
	/**
	 * Create the frame.
	 */
	public SearchInfoDentistt(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial" })
	public SearchInfoDentistt(Image img) {
		 setMinimumSize(new Dimension(610, 635));
			
			this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				DashboardDentist.issearchwinopen=0;
			}
		});
       try {
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		    }	
		
       JButton btnSearch = new JButton("Search");
       btnSearch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
       rdbtnCurrent = new JRadioButton("Current Patient");
       rdbtnCurrent.setOpaque(false);
       JMenuBar menuBar = new JMenuBar();
       menuBar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
       
		rdbtnCurrent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnCurrent.setBackground(Color.WHITE);
       textAreaDetails = new JTextArea();
       textAreaDetails.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textAreaDetails.setLineWrap(true);
		
		textAreaDetails.setFont(new Font("Monospaced", Font.BOLD, 19));
		
		
		textAreaDetails.setBackground(Color.WHITE);
		textAreaDetails.setEditable(false);

		setTitle("Search Information");
		 rdbtnRegistrationId = new JRadioButton("Registration ID");
		 rdbtnRegistrationId.setForeground(new Color(33,50,66));
		 rdbtnRegistrationId.setOpaque(false);
		 rdbtnAppointmentId = new JRadioButton("Appointment ID");
		 rdbtnAppointmentId.setForeground(new Color(77,107,20));
		 rdbtnAppointmentId.setOpaque(false);
		 rdbtnContactNo = new JRadioButton("Contact No.");
		 rdbtnContactNo.setForeground(new Color(107,29,13));
		 rdbtnContactNo.setOpaque(false);
		
		
		setMinimumSize(new Dimension(640, 515));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 641);
		
		
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		setJMenuBar(menuBar);
		
		JMenuItem menuItem = new JMenuItem("");
		menuItem.setMaximumSize(new Dimension(10, 32767));
		menuItem.setPreferredSize(new Dimension(5, 24));
		menuBar.add(menuItem);
		menuItem.setEnabled(false); //for menu bar item disabling!!
		
		JMenu mnAppointment = new JMenu("Appointment");
		mnAppointment.setHorizontalTextPosition(SwingConstants.CENTER);
		mnAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		mnAppointment.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnAppointment);
		mnAppointment.setEnabled(false);
		
		JMenuItem mntmEdit_1 = new JMenuItem("View Appointment");
		mntmEdit_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmEdit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean d= rdbtnCurrent.isSelected();
				if(d==true)
				{
				DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
	     		LocalDateTime now = LocalDateTime.now();
	     		String currentsystemdatetime =dtf.format(now);
				try {
				rs=st.executeQuery("select appointmenttable.appointmentid,patienttable.patientid,patienttable.name,patienttable.gender,patienttable.age,patienttable.contact,patienttable.address,appointmenttable.datetime from patienttable join appointmenttable on patienttable.patientid = appointmenttable.pk_patientid AND appointmenttable.isdeleted='f' AND appointmenttable.datetime >'"+currentsystemdatetime+"' order by appointmenttable.datetime asc");
		    	 if(!rs.next())
		    	 {
		    		JOptionPane.showMessageDialog(null, "There is no current patient right now!");
		    	 }
		    	 else
		    	 {
		    		 ViewAppointmentD.main(null); 
		    	 }
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				}
				else
				{
					ViewAppointmentD.main(null); 
				}
			}
		});
		mntmEdit_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAppointment.add(mntmEdit_1);
		
		JMenu mnBill = new JMenu("Bill");
		mnBill.setHorizontalTextPosition(SwingConstants.CENTER);
		mnBill.setHorizontalAlignment(SwingConstants.CENTER);
		mnBill.setAlignmentX(Component.LEFT_ALIGNMENT);
		mnBill.setPreferredSize(new Dimension(35, 24));
		mnBill.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnBill);
		mnBill.setEnabled(false);
		
		JMenuItem mntmEdit_2 = new JMenuItem("View Bills");
		mntmEdit_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmEdit_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean d= rdbtnCurrent.isSelected();
				if(d==true)
				{
				DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
	     		LocalDateTime now = LocalDateTime.now();
	     		String currentsystemdatetime =dtf.format(now);
				try {
				rs=st.executeQuery("select appointmenttable.appointmentid,patienttable.patientid,patienttable.name,patienttable.gender,patienttable.age,patienttable.contact,patienttable.address,appointmenttable.datetime from patienttable join appointmenttable on patienttable.patientid = appointmenttable.pk_patientid AND appointmenttable.isdeleted='f' AND appointmenttable.datetime >'"+currentsystemdatetime+"' order by appointmenttable.datetime asc");
		    	 if(!rs.next())
		    	 {
		    		JOptionPane.showMessageDialog(null, "There is no current patient right now!");
		    	 }
		    	 else
		    	 {
		    		 EditBillD.main(null);
		    	 }
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				}else
				{
					EditBillD.main(null);
				}
			}
		});
		mntmEdit_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnBill.add(mntmEdit_2);
		
		JMenu mnPrescription = new JMenu("Prescription");
		mnPrescription.setHorizontalTextPosition(SwingConstants.CENTER);
		mnPrescription.setHorizontalAlignment(SwingConstants.CENTER);
		mnPrescription.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnPrescription);
		mnPrescription.setEnabled(false); //prescription disabled!!
		
		JMenuItem mntmView_2 = new JMenuItem("Recent Presc.");
		mntmView_2.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmView_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean d= rdbtnCurrent.isSelected();
				if(d==true)
				{
				DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
	     		LocalDateTime now = LocalDateTime.now();
	     		String currentsystemdatetime =dtf.format(now);
				try {
				rs=st.executeQuery("select appointmenttable.appointmentid,patienttable.patientid,patienttable.name,patienttable.gender,patienttable.age,patienttable.contact,patienttable.address,appointmenttable.datetime from patienttable join appointmenttable on patienttable.patientid = appointmenttable.pk_patientid AND appointmenttable.isdeleted='f' AND appointmenttable.datetime >'"+currentsystemdatetime+"' order by appointmenttable.datetime asc");
		    	 if(!rs.next())
		    	 {
		    		JOptionPane.showMessageDialog(null, "There is no current patient right now!");
		    	 }
		    	 else
		    	 {
		    		 viewPrescription.main(null);
		    	 }
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				}else
				{
					viewPrescription.main(null);
				}
			}
		});
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Add Prescription");
		mntmNewMenuItem.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean d= rdbtnCurrent.isSelected();
				if(d==true)
				{
				DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
	     		LocalDateTime now = LocalDateTime.now();
	     		String currentsystemdatetime =dtf.format(now);
				try {
				rs=st.executeQuery("select appointmenttable.appointmentid,patienttable.patientid,patienttable.name,patienttable.gender,patienttable.age,patienttable.contact,patienttable.address,appointmenttable.datetime from patienttable join appointmenttable on patienttable.patientid = appointmenttable.pk_patientid AND appointmenttable.isdeleted='f' AND appointmenttable.datetime >'"+currentsystemdatetime+"' order by appointmenttable.datetime asc");
		    	 if(!rs.next())
		    	 {
		    		JOptionPane.showMessageDialog(null, "There is no current patient right now!");
		    	 }
		    	 else
		    	 {
		    		 setPriscription.main(null);
		    	 }
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				}else
				{
					setPriscription.main(null);
				}
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnPrescription.add(mntmNewMenuItem);
		mntmView_2.setPreferredSize(new Dimension(95, 24));
		mntmView_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnPrescription.add(mntmView_2);
		
		JMenu mnReport = new JMenu("Report");
		mnReport.setHorizontalTextPosition(SwingConstants.CENTER);
		mnReport.setHorizontalAlignment(SwingConstants.CENTER);
		mnReport.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnReport);
		mnReport.setEnabled(true);
		
		JMenuItem mntmView_3 = new JMenuItem("Clinic Report");
		mntmView_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmView_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowReport.main(null);
			}
		});
		mntmView_3.setHorizontalTextPosition(SwingConstants.CENTER);
		mntmView_3.setHorizontalAlignment(SwingConstants.CENTER);
		mntmView_3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnReport.add(mntmView_3);
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
		
		rdbtnCurrent.addActionListener(new ActionListener() {
	       	public void actionPerformed(ActionEvent e) {
	       		
	       		boolean d=rdbtnCurrent.isSelected();
				
				
				if(d==true)
				{
					txtSearch.setEditable(false);
					textAreaDetails.setText(null);
					btnSearch.setEnabled(true);
					rdbtnAppointmentId.setSelected(false);
					rdbtnContactNo.setSelected(false);
					rdbtnRegistrationId.setSelected(false);
					menuItem.setEnabled(true);
		         	   mnAppointment.setEnabled(true);
		         	   mnBill.setEnabled(true);
		         	   mnPrescription.setEnabled(true);
		         	  
		         	   mnReport.setEnabled(true);
		         	  btnSearch.setEnabled(false);
		         	  
		         	  //edit this later 
		         	 DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
		     		LocalDateTime now = LocalDateTime.now();
		     		String currentsystemdatetime =dtf.format(now);
		         	 try
		         	 {  
			    	  rs=st.executeQuery("select appointmenttable.appointmentid,patienttable.patientid,patienttable.name,patienttable.gender,patienttable.age,patienttable.contact,patienttable.address,appointmenttable.datetime from patienttable join appointmenttable on patienttable.patientid = appointmenttable.pk_patientid AND appointmenttable.isdeleted='f' AND appointmenttable.datetime >'"+currentsystemdatetime+"' order by appointmenttable.datetime asc");
			    	 if(!rs.next())
			    	 {
			    		 JOptionPane.showMessageDialog(null,"No appointments left today! Have a cup of coffee and enjoy the rest of the day sir/mam!");
			    		 textAreaDetails.setText("No appointments!");
			    		 
			    	 }
			    	 else 
			    	 {
			    		
			    	  no= String.valueOf(rs.getInt(2));
			    	  noo=rs.getInt(2);
                   	   menuItem.setEnabled(true);
                   	   mnAppointment.setEnabled(true);
                   	   mnBill.setEnabled(true);
                   	   mnPrescription.setEnabled(true);
                   	  
                   	   mnReport.setEnabled(true);
			    	  String n=rs.getString(3);
			    	  String g=rs.getString(4);
			    	  int ag=rs.getInt(5);
			    	  String ad=rs.getString(7);
			    	  String cont=rs.getString(6);
			    		  textAreaDetails.setText("Patient id:= "+String.valueOf(no)+"\n"
			    		    		+ "Patient Name:= "+n+"\n"
		    		    		+ "Gender:= "+g+"\n"
		    		    		+ "Age:= "+String.valueOf(ag)+"\n"
		    		    		+ "Contact No:= "+cont+"\n"
			    		    		+ "Address:= "+ad+"\n");
			    	 }
			    	 }catch(Exception e1)
		         	 {
			        	 e1.printStackTrace();
		         	 }  
				}
				else
				{
					txtSearch.setEditable(true);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		         		
		         	   mnReport.setEnabled(true);
		         	  textAreaDetails.setText(null);
				}
				
	       		
	       	}
	       });
		
		rdbtnAppointmentId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				boolean b=rdbtnAppointmentId.isSelected();
				
				
				if(b==true)
				{
					txtSearch.setEditable(true);
					textAreaDetails.setText(null);
					btnSearch.setEnabled(true);
					rdbtnRegistrationId.setSelected(false);
					rdbtnContactNo.setSelected(false);
					rdbtnCurrent.setSelected(false);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		         		
		         	   mnReport.setEnabled(true);
				}
				else
				{
					btnSearch.setEnabled(false);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		         		
		         	   mnReport.setEnabled(true);
				}

					
			}
		});
		
		rdbtnContactNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean c=rdbtnContactNo.isSelected();
				
				if(c==true)
				{
					txtSearch.setEditable(true);
					textAreaDetails.setText(null);
					btnSearch.setEnabled(true);
					rdbtnRegistrationId.setSelected(false);
					rdbtnAppointmentId.setSelected(false);
					rdbtnCurrent.setSelected(false);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		         		
		         	   mnReport.setEnabled(true);
				}
				else
				{
					btnSearch.setEnabled(false);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		         	
		         	   mnReport.setEnabled(true);
				}

					
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			      boolean a=rdbtnRegistrationId.isSelected();
			      boolean b=rdbtnAppointmentId.isSelected();
			      boolean c=rdbtnContactNo.isSelected();
			      
			      if(a==true)
			      {  
			    	  String isfieldempty= txtSearch.getText();
			    	  String nullref= "";
			    	  if(isfieldempty.equals(nullref))
			    	  {
			    		  JOptionPane.showMessageDialog(null,"Please fill in some data in the text field first!!");
			    	  }
			    	  else
			    	  {
			    	  
			    	  
			    	  try
			          {
			    		 String abc=txtSearch.getText();
			    		  no1=Long.parseLong(abc);
			    	  rs=st.executeQuery("Select * from patienttable where patientid="+no1);
                       if(rs.next())
                       {
                    	   menuItem.setEnabled(true);
                    	   mnAppointment.setEnabled(true);
                    	   mnBill.setEnabled(true);
                    	   mnPrescription.setEnabled(true);
                    	
                    	   mnReport.setEnabled(true);
			    	  String n=rs.getString(2);
			    	  String g=rs.getString(3);
			    	  int ag=rs.getInt(5);
			    	  String ad=rs.getString(4);
			    	  String cont=rs.getString(6);
			    		  textAreaDetails.setText("Patient id:= "+String.valueOf(no1)+"\n"
			    		    		+ "Patient Name:= "+n+"\n"
		    		    		+ "Gender:= "+g+"\n"
		    		    		+ "Age:= "+String.valueOf(ag)+"\n"
		    		    		+ "Contact No:= "+cont+"\n"
			    		    		+ "Address:= "+ad+"\n");
                       }
                       else
                       {
                    	   JOptionPane.showMessageDialog(null,"No record found!!");
                    	   textAreaDetails.setText("");
                    	   
                       }
			    	  
			            }
			     
			            catch(Exception e1)
			            {
			    	        e1.printStackTrace();
			               }
			    	  }
			    	  } 
			      else if(c==true)
			      {
			    	  String isfieldempty= txtSearch.getText();
			    	  String nullref= "";
			    	  if(isfieldempty.equals(nullref))
			    	  {
			    		  JOptionPane.showMessageDialog(null,"Please fill in some data in the text field first!!");
			    	  }
			    	  else
			    	  {
			    	  try
			    	  {
			    		  String abc= txtSearch.getText();
			    		  ResultSet rs1=st.executeQuery("Select * from patienttable where contact='"+abc+"'");
			    		  if(rs1.next())
			    		  {
			    			  menuItem.setEnabled(true);
	                    	   mnAppointment.setEnabled(true);
	                    	   mnBill.setEnabled(true);
	                    	   mnPrescription.setEnabled(true);
	                    	
	                    	   mnReport.setEnabled(true);
			    		  patient_id=rs1.getInt(1);
			    		  int age=rs1.getInt(5);
			    		  textAreaDetails.setText("Patient id:= "+String.valueOf(patient_id)+"\n"
			    		    		+ "Patient Name:= "+rs1.getString(2)+"\n"
		    		    		+ "Gender:= "+rs1.getString(3)+"\n"
		    		    		+ "Age:= "+String.valueOf(age)+"\n"
		    		    		+ "Contact No:= "+rs1.getString(6)+"\n"
			    		    		+ "Address:= "+rs1.getString(4)+"\n");
			    		  }
			    		  else
			    		  {
			    			  JOptionPane.showMessageDialog(null,"No record found!!");
			    			  textAreaDetails.setText("");
			    		  }
			    	  }
			    	  catch(Exception e3)
			    	  {
			    		  e3.printStackTrace();
			    	  }
			    	  }
			    	  }
			      else if(b==true)
			      {
			    	  String isfieldempty= txtSearch.getText();
			    	  String nullref= "";
			    	  if(isfieldempty.equals(nullref))
			    	  {
			    		  JOptionPane.showMessageDialog(null,"Please fill in some data in the text field first!!");
			    	  }
			    	  else
			    	  {
			    	  
			    	  try
			    	  {
			    		  String abc= txtSearch.getText();
			    		  appointmentid= Long.parseLong(abc);
			    		  ResultSet rs1=st.executeQuery("select pk_patientid from appointmenttable where appointmentid="+appointmentid);
			    		  ResultSet rs2=null;
			    		  if(rs1.next())
			    		  {
			    		  patient_id=rs1.getInt(1);
			    		  try {
			    			  rs2= st.executeQuery("select * from patienttable where patientid="+patient_id);
			    		  }
			    		  catch(Exception e2) {}
			    		  if(rs2.next())
			    		  {
			    			  menuItem.setEnabled(true);
	                    	   mnAppointment.setEnabled(true);
	                    	   mnBill.setEnabled(true);
	                    	   mnPrescription.setEnabled(true);
	                    	 
	                    	   mnReport.setEnabled(true);
			    			  int age=rs2.getInt(5);
				    		  textAreaDetails.setText("Patient id:= "+String.valueOf(patient_id)+"\n"
				    		    		+ "Patient Name:= "+rs2.getString(2)+"\n"
			    		    		+ "Gender:= "+rs2.getString(3)+"\n"
			    		    		+ "Age:= "+String.valueOf(age)+"\n"
			    		    		+ "Contact No:= "+rs2.getString(6)+"\n"
				    		    		+ "Address:= "+rs2.getString(4)+"\n");
			    		  }
			    		  else
			    		  {
			    			  JOptionPane.showMessageDialog(null,"No record found!!");
			    			  textAreaDetails.setText("");
			    			  
			    		  }
			    		 
			    		  }
			    		  else
			    		  {
			    			  JOptionPane.showMessageDialog(null,"No record found!!");
			    			  textAreaDetails.setText("");
			    		  }
			    	  }
			    	  catch(Exception e3)
			    	  {
			    		  e3.printStackTrace();
			    	  }
			    	  }}
		    
			      
			}
			});
		btnSearch.setEnabled(false);
		
		
		rdbtnRegistrationId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean a=rdbtnRegistrationId.isSelected();
				
				
				if(a==true)
				{
					txtSearch.setEditable(true);
					textAreaDetails.setText(null);
					btnSearch.setEnabled(true);
					rdbtnAppointmentId.setSelected(false);
					rdbtnContactNo.setSelected(false);
					rdbtnCurrent.setSelected(false);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		         	
		         	   mnReport.setEnabled(true);
					
				}
				else
				{
					btnSearch.setEnabled(false);
					menuItem.setEnabled(false);
		         	   mnAppointment.setEnabled(false);
		         	   mnBill.setEnabled(false);
		         	   mnPrescription.setEnabled(false);
		     
		         	   mnReport.setEnabled(true);
				}

					
					
					
					
			}
		});
		
		btnSearch.setEnabled(false);
		
		
		rdbtnContactNo.setBackground(Color.WHITE);
		rdbtnContactNo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		
		rdbtnRegistrationId.setBackground(Color.WHITE);
		rdbtnRegistrationId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
	
		rdbtnAppointmentId.setBackground(Color.WHITE);
		rdbtnAppointmentId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnReset = new JButton("Reset");
		btnReset.setForeground(Color.WHITE);
		btnReset.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem.setEnabled(false);
				mnAppointment.setEnabled(false);
				mnBill.setEnabled(false);
				mnPrescription.setEnabled(false);
				
				mnReport.setEnabled(true);
				txtSearch.setText("");
				rdbtnRegistrationId.setSelected(false);
				rdbtnAppointmentId.setSelected(false);
				rdbtnContactNo.setSelected(false);
				rdbtnCurrent.setSelected(false);
			     textAreaDetails.setText("");
			}
			
		});
		btnReset.setBackground(Color.RED);
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 18));
//	    textArea.setText("Patient id:="
//	    		+ "Patient Name:="
//	    		+ "Gender:="
//	    		+ "Age:="
//	    		+ "Contact No:="
//	    		+ "Address:=");
		
		JLabel lblDetails = new JLabel("Details:");
		lblDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtSearch = new JTextField();
		txtSearch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearch.setText("");
				
			}
		});
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isDigit(keychar)||txtSearch.getText().length()>9)
				{
					ke.consume();
				}
			}
		});
		txtSearch.setColumns(10);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnClear.setForeground(Color.WHITE);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtSearch.setText(null);
				textAreaDetails.setText(null);
			}
		});
		btnClear.setBackground(Color.RED);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(rdbtnCurrent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnRegistrationId, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnAppointmentId, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnContactNo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(21))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(74)
					.addComponent(txtSearch, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(65))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDetails)
					.addGap(138)
					.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(191))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
					.addGap(7))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnContactNo)
						.addComponent(rdbtnAppointmentId)
						.addComponent(rdbtnRegistrationId)
						.addComponent(rdbtnCurrent, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(31)
							.addComponent(lblDetails))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addContainerGap())
		);
		scrollPane.setViewportView(textAreaDetails);
				contentPane.setLayout(gl_contentPane);
	}
}
