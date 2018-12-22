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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;


public class NewUser extends JFrame {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	
      
	 
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pf_reg_code;
	private JTextField txt_reg_fname;
	private JTextField txt_reg_contact;
	private JTextField txt_reg_user;
	private JPasswordField pf_reg_pass;
	private JPasswordField txt_reg_ans;
	private JPasswordField pf_reg_cpass;
	protected Object cbx_reg_gender;
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
					img2 = ImageIO.read(getClass().getResource("/t.jpg")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(600, 401, Image.SCALE_DEFAULT); //this is for resizing the image
					NewUser frame = new NewUser(img);
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
	
	public void openconnection()
	{
		try {
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void closeconnection()
	{
			st=null;
			con=null;
			try {
					DriverManager.getConnection(dbcon.JDBC_URLS);
				} catch (SQLException e1) {}
					
				
	}

	/**
	 * Create the frame.
	 */

	public NewUser(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	@SuppressWarnings({ "static-access", "serial", "unchecked", "rawtypes" })
	public NewUser(Image img) {
			setResizable(false);
        setMinimumSize(new Dimension(814, 540));
		
		this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Login.isformopen=0;
			}
		});
		
		
		
		setForeground(Color.BLACK);
		setTitle("New User Window");
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 714, 499);
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
		JComboBox cbx_reg_gender = new JComboBox();
		cbx_reg_gender.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cbx_reg_gender.setBackground(Color.WHITE);
		cbx_reg_gender.setOpaque(false);
		JComboBox cbx_reg_type = new JComboBox();
		cbx_reg_type.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cbx_reg_type.setBackground(Color.WHITE);
		cbx_reg_type.setOpaque(false);
		cbx_reg_type.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lbl_reg_code = new JLabel("Enter Code*");
		lbl_reg_code.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		pf_reg_code = new JPasswordField();
		pf_reg_code.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.RED));
		pf_reg_code.setToolTipText("Max length 6 digit");
		pf_reg_code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isDigit(keychar))
				{
					ke.consume();
				}
				if(pf_reg_code.getPassword().length>5)
				{
					ke.consume();
				}
			}
		});
		pf_reg_code.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lbl_reg_name = new JLabel("Enter your name");
		lbl_reg_name.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txt_reg_fname = new JTextField();
		txt_reg_fname.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLACK));
		txt_reg_fname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar=e.getKeyChar();
				if(!Character.isAlphabetic(keychar)&&keychar!=' ')
				{
					e.consume();
				}
				
			}
		});
		txt_reg_fname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_reg_fname.setColumns(10);
		
		JLabel lbl_reg_contact = new JLabel("Contact No");
		lbl_reg_contact.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txt_reg_contact = new JTextField();
		txt_reg_contact.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_reg_contact.setToolTipText("Excluding country code");
		txt_reg_contact.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar=e.getKeyChar();
				if(!Character.isDigit(keychar))
				{
					e.consume();
				}
				if(txt_reg_contact.getText().length()>9) {
					e.consume();
				}
			}
		});
		txt_reg_contact.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_reg_contact.setColumns(10);
		
		JLabel lbl_reg_user = new JLabel("User Name");
		lbl_reg_user.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txt_reg_user = new JTextField();
		txt_reg_user.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txt_reg_user.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar=e.getKeyChar();
				if(!Character.isLetterOrDigit(keychar)&&keychar!='@'&&keychar!='_'&&keychar!='.')
				{
				
					e.consume();
				}
			}
		});
		txt_reg_user.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_reg_user.setColumns(10);
		
		JLabel lbl_reg_pass = new JLabel("Password*");
		lbl_reg_pass.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		pf_reg_pass = new JPasswordField();
		pf_reg_pass.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		pf_reg_pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!Character.isLetterOrDigit(ch)&&ch!='@'&&ch!='_')
				{
					e.consume();
				}
				if(pf_reg_pass.getPassword().length>14)
				{
					e.consume();
				}
			}
		});
		pf_reg_pass.setToolTipText("Max length 15 alphanumeric letters or  and _ are allowed");
		pf_reg_pass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JLabel lbl_reg_repass = new JLabel("Confirm Password*");
		lbl_reg_repass.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lbl_reg_ques = new JLabel("Security Question");
		lbl_reg_ques.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		JComboBox cbx_reg_ques = new JComboBox();
		cbx_reg_ques.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cbx_reg_ques.setOpaque(false);
		cbx_reg_ques.setBackground(Color.WHITE);
		cbx_reg_ques.addItem("What is the name of your favorite childhood friend?");
		cbx_reg_ques.addItem("What is your favorite team?");
		cbx_reg_ques.addItem("What was the name of the hospital where you were born?");
		cbx_reg_ques.addItem("What school did you attend for sixth grade?");
		cbx_reg_ques.addItem("What was your favorite food as a child?");
		
		cbx_reg_ques.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lbl_reg_ans = new JLabel("Security Answer*");
		lbl_reg_ans.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txt_reg_ans = new JPasswordField();
		txt_reg_ans.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		txt_reg_ans.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar=e.getKeyChar();
				if(!Character.isAlphabetic(keychar))
				{
					e.consume();
				}
			}
		});
		txt_reg_ans.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_reg_ans.setColumns(10);
		
		JButton btn_reg_submit = new JButton("Submit");
		btn_reg_submit.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_reg_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				openconnection();
				
				String fname = txt_reg_fname.getText();
				String regcode =txt_reg_contact.getText();
				String user= txt_reg_user.getText();
				char ob[]=txt_reg_ans.getPassword();
				String regans= String.valueOf(ob);				
				
				char a[]=pf_reg_code.getPassword(); //this is to take the code to the variable...
				String code = String.valueOf(a);
				String g = (String) cbx_reg_gender.getSelectedItem();
				String type = (String) cbx_reg_type.getSelectedItem();
				String ques=(String) cbx_reg_ques.getSelectedItem();
				char b[]=pf_reg_pass.getPassword();
				String pwd=String.valueOf(b);
				char c[]=pf_reg_cpass.getPassword();
				String cpwd=String.valueOf(c);
				String nullref= "";
				
				if(fname.equals(nullref)||regcode.equals(nullref)||user.equals(nullref)||regans.equals(nullref)||pwd.equals(nullref)||cpwd.equals(nullref))
				{
					JOptionPane.showMessageDialog(null,"All fields are mandtory!!");
				}
				else if(txt_reg_contact.getText().length()<=9)
					{
						new JOptionPane();
						JOptionPane.showMessageDialog(null, "number should be 10 digits long");
					}
				else if(txt_reg_contact.getText().length()<=9)
				{
					new JOptionPane();
					JOptionPane.showMessageDialog(null, "number should be 10 digits long");
				}
				else if(!pwd.equals(cpwd))
				{
					JOptionPane.showMessageDialog(null,"Entered password do not match. Please enter again!");
					pf_reg_pass.setText("");
					pf_reg_cpass.setText("");
				}
				
					
					else
					{
						try
						{
						rs=st.executeQuery("select * from code where cast( code as numeric(10,0))="+code+"");
						
						if(!rs.next())
						{
							JOptionPane.showMessageDialog(null, "The code you have entered is incorrect. Please contact the admin or try again.!");
							pf_reg_code.setText("");
						}
						else
						{
							if(type.equals("Dentist"))
							{
									rs=st.executeQuery("select * from dentistlogintable where username='"+txt_reg_user.getText()+"'");
									
									if(rs.next())
									{
										JOptionPane.showMessageDialog(null, "Username already taken! Please set a different one");
										txt_reg_user.setText("");
									}
									else
									{
										con.createStatement().execute("insert into dentistlogintable(username,password,name,gender,contact,securityq,securitya) values"
												+ "('"+txt_reg_user.getText()+"','"+pwd+"','"+txt_reg_fname.getText()+"','"+g+"','"+txt_reg_contact.getText()+"','"+ques+"','"+regans+"')");	
										
										JOptionPane.showMessageDialog(null, "Successfully registered!");
										pf_reg_code.setText("");
										txt_reg_fname.setText("");
										txt_reg_contact.setText("");
										txt_reg_user.setText("");
										cbx_reg_gender.setSelectedIndex(0);
										pf_reg_pass.setText("");
										pf_reg_cpass.setText("");
										cbx_reg_ques.setSelectedIndex(0);
										txt_reg_ans.setText("");
										cbx_reg_type.setSelectedIndex(0);
									}
							}
							else if(type.equals("Receptionist"))
							{  
								
									rs=st.executeQuery("select * from receptionlogintable where username='"+txt_reg_user.getText()+"'");
									
									if(rs.next())
									{
										JOptionPane.showMessageDialog(null, "Username already taken! Please set a different one");
										txt_reg_user.setText("");
									}
									else
									{
								
								
								con.createStatement().execute("insert into receptionlogintable(username,password,name,gender,contact,securityq,securitya) values"
										+ "('"+txt_reg_user.getText()+"','"+pwd+"','"+txt_reg_fname.getText()+"','"+g+"','"+txt_reg_contact.getText()+"','"+ques+"','"+regans+"')");	
								JOptionPane.showMessageDialog(null, "Successfully registered!");
								pf_reg_code.setText("");
								txt_reg_fname.setText("");
								txt_reg_contact.setText("");
								txt_reg_user.setText("");
								cbx_reg_gender.setSelectedIndex(0);
								pf_reg_pass.setText("");
								pf_reg_cpass.setText("");
								cbx_reg_ques.setSelectedIndex(0);
								txt_reg_ans.setText("");
								cbx_reg_type.setSelectedIndex(0);
							}
							}
						}
						}catch(Exception e) {}
					}
				}
			}
		
	);					

		btn_reg_submit.setForeground(new Color(255, 255, 255));
		btn_reg_submit.setBackground(new Color(0, 204, 0));
		btn_reg_submit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btn_reg_clear = new JButton("Clear");
		btn_reg_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_reg_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pf_reg_code.setText("");
				txt_reg_fname.setText("");
				txt_reg_contact.setText("");
				txt_reg_user.setText("");
				cbx_reg_gender.setSelectedIndex(0);
				pf_reg_pass.setText("");
				pf_reg_cpass.setText("");
				cbx_reg_ques.setSelectedIndex(0);
				cbx_reg_type.setSelectedIndex(0);
				txt_reg_ans.setText("");
			}
		});
		btn_reg_clear.setForeground(new Color(255, 255, 255));
		btn_reg_clear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_reg_clear.setBackground(Color.RED);
		
		JLabel lbl_reg_gender = new JLabel("Gender");
		lbl_reg_gender.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		cbx_reg_gender.addItem("Male");
		cbx_reg_gender.addItem("Female");
		cbx_reg_gender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		pf_reg_cpass = new JPasswordField();
		pf_reg_cpass.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		pf_reg_cpass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lbl_reg_type = new JLabel("User Type");
		lbl_reg_type.setFont(new Font("Tahoma", Font.BOLD, 18));
		
	
		cbx_reg_type.addItem("Receptionist");
		cbx_reg_type.addItem("Dentist");
		
	
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_reg_user, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_repass, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_pass, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_gender, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_code, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_contact, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_name, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(pf_reg_cpass, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
										.addComponent(pf_reg_pass, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
										.addComponent(txt_reg_user, Alignment.LEADING, 267, 377, Short.MAX_VALUE)
										.addComponent(txt_reg_fname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
										.addComponent(pf_reg_code, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
										.addComponent(cbx_reg_gender, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
									.addGap(188))
								.addComponent(txt_reg_contact, 267, 267, 267)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_reg_type, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_ques, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_reg_ans, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
							.addGap(36)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(txt_reg_ans, GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
										.addComponent(cbx_reg_ques, 0, 537, Short.MAX_VALUE))
									.addGap(28))
								.addComponent(cbx_reg_type, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))))
					.addGap(5))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(275, Short.MAX_VALUE)
					.addComponent(btn_reg_submit, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btn_reg_clear, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(265))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_code, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(pf_reg_code, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txt_reg_fname, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_reg_name, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_contact, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_reg_contact, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_user, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_reg_user, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_gender, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbx_reg_gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbl_reg_pass, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(pf_reg_pass, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_reg_repass, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(pf_reg_cpass, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_ques, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbx_reg_ques, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_ans, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_reg_ans, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_reg_type)
						.addComponent(cbx_reg_type, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_reg_submit, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_reg_clear, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
					.addGap(8))
		);
	
		contentPane.setLayout(gl_contentPane);
		
		
	}
}
