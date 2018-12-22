package DCMSapp;
import java.awt.EventQueue;
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
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

@SuppressWarnings("unused")
public class Patientreg extends JFrame {
	static Connection con=null;
	static Statement st=null;
	static JTextArea textArea;
	ResultSet rs=null;
	String appointmentdate=null;
	String appointmenthour=null;
	String appointmentmin=null;
	String ampm=null;
	public static String transferdate;
	public static String transfertime;
	public static String transferappointmentid;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_pr_id;
	private JTextField txt_pr_fname;
	private JTextField txt_pr_age;
	private JTextField txt_pr_contact;
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
		             
		             img=img2.getScaledInstance(600, 588, Image.SCALE_DEFAULT); //this is for resizing the image
					Patientreg frame = new Patientreg(img);
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
	
	public Patientreg(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial", "unchecked" })
	public Patientreg(Image img) {
			setResizable(false);
        setMinimumSize(new Dimension(600, 635));
		
		this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				DashboardReception.isregwinopen=0;
				DashboardReception.model.setRowCount(0);
				DashboardReception.updatetable();
				int a= DashboardReception.model.getRowCount();
				DashboardReception.lblCurrent.setText(Integer.toString(a));
				
				if(st!=null)
				{
					st=null;
				}
				if(con!=null)
				{
					con=null;
				}
				
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
		setTitle("Pateint Registration");
		setBounds(100, 100, 600, 634);
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
		setContentPane(contentPane);
		
		JLabel lbl_pr_id = new JLabel("Pateint Id");
		lbl_pr_id.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		
		JLabel lbl_pr_fname = new JLabel("Pateint Name");
		lbl_pr_fname.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_gender = new JLabel("Gender");
		lbl_pr_gender.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_contact = new JLabel("Contact No.");
		lbl_pr_contact.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_age = new JLabel("Age");
		lbl_pr_age.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_pr_address = new JLabel("Address");
		lbl_pr_address.setFont(new Font("Tahoma", Font.BOLD, 18));
		@SuppressWarnings("rawtypes")
		JComboBox cbx_pr_gender = new JComboBox();
		cbx_pr_gender.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_pr_id = new JTextField();
		txt_pr_id.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
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
			
			rs=st.executeQuery("select max (patientid) from patienttable");
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
			String p_id=String.valueOf(id);
			txt_pr_id.setText(p_id);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
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
				String appflag="Appointment not set yet!";
				String appointmentflag=textArea.getText();
				if(!age.equals(nullref)) {
					ag=Integer.parseInt(txt_pr_age.getText());
				}
				
				if(area.equals(nullref)||fname.equals(nullref)||contact.equals(nullref)||address.equals(nullref)||age.equals(nullref))
				{
					JOptionPane.showMessageDialog(null,"All fields are mandtory!!");
					
				}
				else if(txt_pr_contact.getText().length()<=9)
				{
					new JOptionPane();
					JOptionPane.showMessageDialog(null, "number should be 10 digits long");
				}
				else if(age.equals(agezero)||age.equals(agezerod))
				{
					JOptionPane.showMessageDialog(null,"age cant be 0!");
				}
				else if(appflag.equals(appointmentflag))
				{
					JOptionPane.showMessageDialog(null,"Appointment not set yet. Please set appointment!!");
					Appointment.main(null);
				}
				else
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
					String finaldate=transferdate+" "+transfertime;
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					String finalsystemdate=(dtf.format(now));
					
				
				con.createStatement().execute("insert into patienttable(name,gender,address,age,contact) values('"+txt_pr_fname.getText()+"','"+gen+"','"+area+"',"+ag+",'"+txt_pr_contact.getText()+"')");
				rs=st.executeQuery("select * from patienttable");
				while(rs.next())
				{			
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getString(6)+"");
				}
				JOptionPane.showMessageDialog(null, "successfully registered! Patient id is"+txt_pr_id.getText()+"");
				int id= Integer.parseInt(txt_pr_id.getText());
				con.createStatement().execute("insert into appointmenttable (datetime,systemtime,isdeleted,pk_patientid) values ('"+finaldate+"','"+finalsystemdate+"','f',"+Integer.parseInt(txt_pr_id.getText())+")");
				ResultSet rs2=st.executeQuery("select * from appointmenttable");
				while(rs2.next())
				{			
				System.out.println(rs2.getInt(1)+" "+rs2.getString(2)+" "+rs2.getString(3)+" "+rs2.getInt(5)+"");
				}
				id++;
				txt_pr_id.setText(String.valueOf(id));
				txt_pr_fname.setText("");
				txt_pr_age.setText("");
				txt_pr_contact.setText("");
				cbx_pr_gender.setSelectedIndex(0);
				txtarea_pr_address.setText("");
				
				
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				finally{
					if(st!=null)
					{
						st=null;
					}
					if(con!=null)
					{
						con=null;
					}
					try {
						DriverManager.getConnection(dbcon.JDBC_URLS);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						
					}
					
					
				}
			
				}	
			}
		});
		btn_pr_save.setForeground(new Color(255, 255, 255));
		btn_pr_save.setBackground(new Color(139,189,65));
		btn_pr_save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btn_pr_clear = new JButton("Clear");
		btn_pr_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_pr_fname.setText("");
				txt_pr_age.setText("");
				txt_pr_contact.setText("");
				txtarea_pr_address.setText("");
				cbx_pr_gender.setSelectedIndex(0);
				textArea.setText("Appointment not set yet!");
				transferdate=null;
				transfertime=null;
				transferappointmentid=null;
				
			}
		});
		btn_pr_clear.setForeground(Color.WHITE);
		btn_pr_clear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_pr_clear.setBackground(Color.RED);
		
		
		cbx_pr_gender.setBackground(Color.WHITE);
		cbx_pr_gender.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbx_pr_gender.addItem("Male");
		cbx_pr_gender.addItem("Female"); 
		
		JButton btn_pr_set = new JButton("Set Appointment");
		btn_pr_set.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_set.setForeground(new Color(255, 255, 255));
		btn_pr_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Appointment.main(null);
			}
		});
		btn_pr_set.setBackground(new Color(242,171,42));
		btn_pr_set.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblAppointmentStatus = new JLabel("Appointment Status:");
		lblAppointmentStatus.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		textArea = new JTextArea();
		textArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textArea.setFont(new Font("Monospaced", Font.BOLD, 19));
		
			textArea.setText("Appointment not set yet!");
		
		
		
		textArea.setEditable(false);
		textArea.setBackground(UIManager.getColor("text"));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_pr_id, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_pr_fname, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_pr_gender, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(cbx_pr_gender, 0, 182, Short.MAX_VALUE)
									.addGap(30)
									.addComponent(lbl_pr_age, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txt_pr_age, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
									.addGap(13))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(txt_pr_id, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
										.addComponent(txt_pr_fname, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
									.addGap(72))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_pr_contact, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_pr_contact, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)))
					.addGap(2))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbl_pr_address, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(422, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAppointmentStatus)
							.addGap(375))
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(60)
					.addComponent(btn_pr_save, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_pr_set, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_pr_clear, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addGap(49))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_id, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_id, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_fname, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_fname, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lbl_pr_gender, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(cbx_pr_gender, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lbl_pr_age, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(txt_pr_age, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_pr_contact, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_pr_contact, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbl_pr_address, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAppointmentStatus)
					.addGap(6)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_pr_clear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_pr_save, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_pr_set, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(1))
		);
		
		scrollPane.setViewportView(txtarea_pr_address);
		
		
		txtarea_pr_address.setFont(new Font("Monospaced", Font.BOLD, 19));
		txtarea_pr_address.setBackground(Color.WHITE);
		contentPane.setLayout(gl_contentPane);
		
		
		
		
	
	}
	
	
}
