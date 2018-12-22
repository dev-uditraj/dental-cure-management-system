package DCMSapp;

import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

public class ModifyRegistration extends JFrame {
	static Connection con=null;
	static Statement st=null;
	ResultSet rs=null;
	String appointmentdate=null;
	String appointmenthour=null;
	String appointmentmin=null;
	String ampm=null;
	public static String transferdate;
	public static String transfertime;
	public static String transferappointmentid;
	static long ihaveid=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_pr_id;
	private JTextField txt_pr_fname;
	private JTextField txt_pr_age;
	private JTextField txt_pr_contact;
	@SuppressWarnings("unused")
	private static Image img;
    private Image scaled;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Image img = null;
					Image img2=null;
					img2 = ImageIO.read(getClass().getResource("/nw.png")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(600, 440, Image.SCALE_DEFAULT); //this is for resizing the image
					ModifyRegistration frame = new ModifyRegistration(img);
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
	
	public ModifyRegistration(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial", "unchecked" })
	public ModifyRegistration(Image img) {
		 setMinimumSize(new Dimension(600, 440));
			
			this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		JTextArea txtarea_pr_address = new JTextArea();
		txtarea_pr_address.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtarea_pr_address.setLineWrap(true);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setTitle("Patient Registration");
		setBounds(100, 100, 600, 440);
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

		contentPane.setPreferredSize(new Dimension(650, 650));
		contentPane.setMinimumSize(new Dimension(650, 650));
		//contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_pr_id = new JLabel("Patient Id");
		lbl_pr_id.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		
		JLabel lbl_pr_fname = new JLabel("Patient Name");
		lbl_pr_fname.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_gender = new JLabel("Gender");
		lbl_pr_gender.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_contact = new JLabel("Contact No.");
		lbl_pr_contact.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_age = new JLabel("Age");
		lbl_pr_age.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lbl_pr_address = new JLabel("Address");
		lbl_pr_address.setFont(new Font("Tahoma", Font.BOLD, 18));
		@SuppressWarnings("rawtypes")
		JComboBox cbx_pr_gender = new JComboBox();
		cbx_pr_gender.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cbx_pr_gender.addItem("Male");
		cbx_pr_gender.addItem("Female");
		txt_pr_id = new JTextField();
		txt_pr_id.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_pr_id.setBackground(SystemColor.menu);
		txt_pr_id.setEditable(false);
		txt_pr_id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!Character.isDigit(ch))
				{
					e.consume();
				}
				if(txt_pr_id.getText().length()>4)
				{
					e.consume();
				}
			}
		});
		txt_pr_id.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_pr_id.setColumns(10);
		
		
		txt_pr_fname = new JTextField();
		txt_pr_fname.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_pr_fname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar=e.getKeyChar();
				if(!Character.isAlphabetic(keychar)&&keychar!=' ')
				{
					e.consume();
				}
			}
		});
		txt_pr_fname.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_pr_fname.setColumns(10);
		
		txt_pr_age = new JTextField();
		txt_pr_age.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_pr_age.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!Character.isDigit(ch))
				{
					e.consume();
				}
				if(txt_pr_age.getText().length()>1)
				{
					e.consume();
				}
			}
		});
		txt_pr_age.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_pr_age.setColumns(10);
		
		txt_pr_contact = new JTextField();
		txt_pr_contact.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_pr_contact.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar=e.getKeyChar();
				if(!Character.isDigit(keychar))
				{
					e.consume();
				}
				if(txt_pr_contact.getText().length()>9)
				{
					e.consume();
				}
			}
		});
		txt_pr_contact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_pr_contact.setColumns(10);
		
		boolean a= SearchInfoReception.rdbtnRegistrationId.isSelected();
		boolean b= SearchInfoReception.rdbtnAppointmentId.isSelected();
		boolean c= SearchInfoReception.rdbtnContactNo.isSelected();
		String getdata= SearchInfoReception.txtSearch.getText();
		long getids= Long.parseLong(getdata);
		if(a==true)
		{
			try {
				
				
				ResultSet table= st.executeQuery("select * from patienttable where patientid="+getids);
				table.next();
				ihaveid=table.getInt(1);
				String ids =String.valueOf(ihaveid);
				txt_pr_id.setText(ids);
				txt_pr_fname.setText(table.getString(2));
				String gender =table.getString(3);
				if(gender.equals("Male"))
				{
					cbx_pr_gender.setSelectedItem("Male");
				}
				else if(gender.equals("Female"))
				{
					cbx_pr_gender.setSelectedItem("Female");
				}
				int age=table.getInt(5);
				String convertage=Integer.toString(age);
				txt_pr_age.setText(convertage);
				txtarea_pr_address.setText(table.getString(4));
				txt_pr_contact.setText(table.getString(6));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(b==true)
		{
			try {
				
				ResultSet table1= st.executeQuery("Select pk_patientid from appointmenttable where appointmentid="+getids);
				table1.next();
				int getpatientid= table1.getInt(1);
				ResultSet table= st.executeQuery("select * from patienttable where patientid="+getpatientid);
				table.next();
				ihaveid=table.getInt(1);
				String ids =String.valueOf(ihaveid);
				txt_pr_id.setText(ids);
				txt_pr_fname.setText(table.getString(2));
				String gender =table.getString(3);
				if(gender.equals("Male"))
				{
					cbx_pr_gender.setSelectedItem("Male");
				}
				else if(gender.equals("Female"))
				{
					cbx_pr_gender.setSelectedItem("Female");
				}
				int age=table.getInt(5);
				String convertage=Integer.toString(age);
				txt_pr_age.setText(convertage);
				txtarea_pr_address.setText(table.getString(4));
				txt_pr_contact.setText(table.getString(6));
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(c==true)
		{
			try {
				
				
				ResultSet table= st.executeQuery("select * from patienttable where contact='"+getdata+"'");
				table.next();
				ihaveid=table.getInt(1);
				String ids =String.valueOf(ihaveid);
				txt_pr_id.setText(ids);
				txt_pr_fname.setText(table.getString(2));
				String gender =table.getString(3);
				if(gender.equals("Male"))
				{
					cbx_pr_gender.setSelectedItem("Male");
				}
				else if(gender.equals("Female"))
				{
					cbx_pr_gender.setSelectedItem("Female");
				}
				int age=table.getInt(5);
				String convertage=Integer.toString(age);
				txt_pr_age.setText(convertage);
				txtarea_pr_address.setText(table.getString(4));
				txt_pr_contact.setText(table.getString(6));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		JButton btn_pr_save = new JButton("Save");
		btn_pr_save.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ag=0;
				String gen=(String) cbx_pr_gender.getSelectedItem();
				String area=txtarea_pr_address.getText();
				String age = txt_pr_age.getText();
				String fname= txt_pr_fname.getText();
				String contact=txt_pr_contact.getText();
				String address=txtarea_pr_address.getText();
				String nullref="";
				String agezero="0";
				String agezerod="00";
				if(!age.equals(nullref)) {
					ag=Integer.parseInt(txt_pr_age.getText());
				}
				if(area.equals(nullref)||fname.equals(nullref)||contact.equals(nullref)||address.equals(nullref)||age.equals(nullref))
				{
					JOptionPane.showMessageDialog(null,"All fields are mandtory!!");
				}
				else if(txt_pr_contact.getText().length()<=9)
				{
					JOptionPane.showMessageDialog(null, "number should be 10 digits long");
				}
				else if(age.equals(agezero)||age.equals(agezerod))
				{
					JOptionPane.showMessageDialog(null,"age cant be 0!");
				}
				else 
				{
				 int continueit = JOptionPane.showConfirmDialog(null, "Are you sure you want to make changes to data?");
				 if(continueit==0)
				 {
					 try
					 { 
					try {
						Class.forName(dbcon.DRIVER);
						con=DriverManager.getConnection(dbcon.JDBC_URL);
						st=con.createStatement();
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
					}
					try {
						st.execute("update patienttable SET name='"+txt_pr_fname.getText()+"',gender='"+gen+"', address='"+area+"', age="+ag+", contact='"+txt_pr_contact.getText()+"' where patientid="+ihaveid);
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				
				
				JOptionPane.showMessageDialog(null, "successfully changed!");
				
				int id= Integer.parseInt(txt_pr_id.getText());
				
				txt_pr_id.setText(String.valueOf(id));
				txt_pr_fname.setText("");
				txt_pr_age.setText("");
				txt_pr_contact.setText("");
				cbx_pr_gender.setSelectedIndex(0);
				txtarea_pr_address.setText("");
				
				SearchInfoReception.textAreaDetails.setText("");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				 }
				 
				}	
			}
		});
		btn_pr_save.setForeground(Color.WHITE);
		btn_pr_save.setBackground(new Color(139,189,65));
		btn_pr_save.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btn_pr_clear = new JButton("Reset");
		btn_pr_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_pr_fname.setText("");
				txt_pr_age.setText("");
				txt_pr_contact.setText("");
				txtarea_pr_address.setText("");
				cbx_pr_gender.setSelectedIndex(0);
				transferdate=null;
				transfertime=null;
				transferappointmentid=null;
				
			}
		});
		btn_pr_clear.setForeground(Color.WHITE);
		btn_pr_clear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_pr_clear.setBackground(Color.RED);
		
		
		cbx_pr_gender.setBackground(Color.WHITE);
		cbx_pr_gender.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_pr_id, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_pr_contact, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(lbl_pr_address, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
										.addComponent(txt_pr_contact, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
									.addGap(76))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_pr_fname, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(lbl_pr_gender, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(cbx_pr_gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lbl_pr_age, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txt_pr_age, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(txt_pr_id, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
												.addComponent(txt_pr_fname, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
											.addGap(72)))))
							.addGap(2))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btn_pr_save, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btn_pr_clear, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
							.addGap(156))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_id, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_id, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_fname, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_fname, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_gender, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbx_pr_gender, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_pr_age, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_age, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_contact, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_contact, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_address, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_pr_save, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_pr_clear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		
		scrollPane.setViewportView(txtarea_pr_address);
		
		
		txtarea_pr_address.setFont(new Font("Monospaced", Font.BOLD, 19));
		txtarea_pr_address.setBackground(Color.WHITE);
		contentPane.setLayout(gl_contentPane);
		
		
		
		
	
	}
	
	
}
