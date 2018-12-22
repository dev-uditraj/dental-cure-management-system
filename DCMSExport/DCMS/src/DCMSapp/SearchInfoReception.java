package DCMSapp;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.derby.drda.NetworkServerControl;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
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
import java.sql.ResultSet;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;

public class SearchInfoReception extends JFrame {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextField txtSearch;
	static JRadioButton rdbtnRegistrationId;
	static JRadioButton rdbtnAppointmentId;
	static JRadioButton rdbtnContactNo;
	static long patient_id;
	static JTextArea textAreaDetails;
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
					img2 = ImageIO.read(getClass().getResource("/nw.png"));//this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(645, 610, Image.SCALE_DEFAULT); //this is for resizing the image
					SearchInfoReception frame = new SearchInfoReception(img);
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
	public SearchInfoReception(String img) {
        this(new ImageIcon(img).getImage());
    }
		/**
		 * @wbp.parser.constructor
		 */
	@SuppressWarnings({ "static-access", "serial" })
	public SearchInfoReception(Image img) {
         setMinimumSize(new Dimension(610, 635));
		
		this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				DashboardReception.model.setRowCount(0);
				DashboardReception.updatetable();
				int a= DashboardReception.model.getRowCount();
				DashboardReception.lblCurrent.setText(Integer.toString(a));
				DashboardReception.issearchwinopen=0;
				
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
		
		
       
       textAreaDetails = new JTextArea();
       textAreaDetails.setForeground(Color.DARK_GRAY);
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
		JButton btnSearch = new JButton("Search");
		btnSearch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSearch.setForeground(Color.BLACK);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 640);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuBar.setBackground(new Color(255, 250, 250));
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		setJMenuBar(menuBar);
		
		JMenuItem menuItem = new JMenuItem("");
		menuItem.setBackground(new Color(255, 250, 250));
		menuItem.setMaximumSize(new Dimension(10, 32767));
		menuItem.setPreferredSize(new Dimension(5, 24));
		menuBar.add(menuItem);
		menuItem.setEnabled(false); //for menu bar item disabling!!
		
		JMenu mnAppointment = new JMenu("Appointment");
		mnAppointment.setHorizontalTextPosition(SwingConstants.CENTER);
		mnAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		mnAppointment.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnAppointment);
		mnAppointment.setEnabled(false); // for appointment submenu disable 
		
		JMenuItem mntmNew_1 = new JMenuItem("Set New Appointment");
		mntmNew_1.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		mntmNew_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppointmentMenubar.main(null);
			}
		});
		mntmNew_1.setPreferredSize(new Dimension(110, 24));
		mntmNew_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAppointment.add(mntmNew_1);
		
		JMenuItem mntmEdit_1 = new JMenuItem("Manage Appointments");
		mntmEdit_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmEdit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EditAppointment.main(null);
				
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
		mnBill.setEnabled(false); //bill submenu disabled!!
		
		JMenuItem mntmNew_2 = new JMenuItem("Generate Bill");
		mntmNew_2.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		mntmNew_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillEditor.main(null);
			}
		});
		mntmNew_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnBill.add(mntmNew_2);
		
		JMenuItem mntmEdit_2 = new JMenuItem("Modify Existing Bills");
		mntmEdit_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmEdit_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditBill.main(null);
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
		
		JMenuItem mntmView_2 = new JMenuItem("View ");
		mntmView_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmView_2.setHorizontalTextPosition(SwingConstants.CENTER);
		mntmView_2.setHorizontalAlignment(SwingConstants.CENTER);
		mntmView_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPrescriptionR.main(null);
			}
		});
		mntmView_2.setPreferredSize(new Dimension(95, 24));
		mntmView_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnPrescription.add(mntmView_2);
		
		JMenu mnRegistration = new JMenu("Registration");
		mnRegistration.setHorizontalTextPosition(SwingConstants.CENTER);
		mnRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		mnRegistration.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnRegistration);
		mnRegistration.setEnabled(false);
		
		JMenuItem mntmEdit = new JMenuItem("Edit Registration");
		mntmEdit.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mntmEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModifyRegistration.main(null);
			}
		});
		mntmEdit.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnRegistration.add(mntmEdit);
		
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
		setContentPane(contentPane);
		
		
		rdbtnAppointmentId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				boolean b=rdbtnAppointmentId.isSelected();
				
				
				if(b==true)
				{
					btnSearch.setEnabled(true);
					rdbtnRegistrationId.setSelected(false);
					rdbtnContactNo.setSelected(false);
				}
				else
				{
					btnSearch.setEnabled(false);
				}

					
			}
		});
		
		rdbtnContactNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean c=rdbtnContactNo.isSelected();
				
				if(c==true)
				{
					btnSearch.setEnabled(true);
					rdbtnRegistrationId.setSelected(false);
					rdbtnAppointmentId.setSelected(false);
				}
				else
				{
					btnSearch.setEnabled(false);
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
			    		  try {
			    				NetworkServerControl server = new NetworkServerControl  //this is for starting the server
			    					    (InetAddress.getByName("localhost"),1527);
			    					server.start(null);
			    				Class.forName(dbcon.DRIVER);
			    				con=DriverManager.getConnection(dbcon.JDBC_URL);
			    				st=con.createStatement();
			    			}
			    		  
			    			catch(Exception e1)
			    			{
			    				e1.printStackTrace();
			    			}
			    	  
			    	  try
			          {
			    		 String abc=txtSearch.getText();
			    		  long no=Long.parseLong(abc);
			    	  rs=st.executeQuery("Select * from patienttable where patientid="+no);
                       if(rs.next())
                       {
                    	   menuItem.setEnabled(true);
                    	   mnAppointment.setEnabled(true);
                    	   mnBill.setEnabled(true);
                    	   mnPrescription.setEnabled(true);
                    	   mnRegistration.setEnabled(true);
                    	   mnReport.setEnabled(true);
			    	  String n=rs.getString(2);
			    	  String g=rs.getString(3);
			    	  int ag=rs.getInt(5);
			    	  String ad=rs.getString(4);
			    	  String cont=rs.getString(6);
			    		  textAreaDetails.setText("Patient id:= "+String.valueOf(no)+"\n"
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
	                    	   mnRegistration.setEnabled(true);
	                    	   mnReport.setEnabled(true);
			    		  int patient_id=rs1.getInt(1);
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
			    		  long appointment= Long.parseLong(abc);
			    		  ResultSet rs1=st.executeQuery("select pk_patientid from appointmenttable where appointmentid="+appointment);
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
	                    	   mnRegistration.setEnabled(true);
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
					btnSearch.setEnabled(true);
					rdbtnAppointmentId.setSelected(false);
					rdbtnContactNo.setSelected(false);
				}
				else
				{
					btnSearch.setEnabled(false);
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
		btnReset.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLACK));
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem.setEnabled(false);
				mnAppointment.setEnabled(false);
				mnBill.setEnabled(false);
				mnPrescription.setEnabled(false);
				mnRegistration.setEnabled(false);
				mnReport.setEnabled(true);
				txtSearch.setText("");
				rdbtnRegistrationId.setSelected(false);
				rdbtnAppointmentId.setSelected(false);
				rdbtnContactNo.setSelected(false);
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
		btnClear.setForeground(new Color(255, 255, 255));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtSearch.setText("");
			}
		});
		btnClear.setBackground(new Color(255, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDetails)
					.addContainerGap(592, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(110)
					.addComponent(txtSearch, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(72))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(73)
					.addComponent(rdbtnRegistrationId, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
					.addGap(35)
					.addComponent(rdbtnAppointmentId, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
					.addGap(41)
					.addComponent(rdbtnContactNo, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addGap(62))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(221)
					.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
					.addGap(210))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnRegistrationId)
						.addComponent(rdbtnAppointmentId)
						.addComponent(rdbtnContactNo))
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(lblDetails)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addContainerGap())
		);
		scrollPane.setViewportView(textAreaDetails);
				contentPane.setLayout(gl_contentPane);
	}
}
