package DCMSapp;

import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;



public class ResetPassword extends JFrame {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;   

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtusername;
	private JTextField txtsecurityquestion;
	private JPasswordField txtanswer;
	private JPasswordField pfnewpass;
	private JPasswordField pfconformpass;
	private JPasswordField pfcode;
	@SuppressWarnings("unused")
	private static Image img;
    private Image scaled;
    private JButton btnCheckusername;

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Image img = null;
					Image img2=null;
					img2 = ImageIO.read(getClass().getResource("/t.jpg")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(428, 417, Image.SCALE_DEFAULT); //this is for resizing the image
					ResetPassword frame = new ResetPassword(img);
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
			st = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void closeconnection()
	{
				
			try {
				DriverManager.getConnection(dbcon.JDBC_URLS);
			} catch (SQLException e1) {}
	}
	
	public ResetPassword(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial" })
	public ResetPassword(Image img){
			setResizable(false);
		 setMinimumSize(new Dimension(814, 520));
			
			this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Login.isresetopen=0;
			}
		});
		
		
		setTitle("Reset Password");
		  //step 2;
		
		
		JRadioButton rdbtnReceptionist = new JRadioButton("Receptionist");
		rdbtnReceptionist.setOpaque(false);
		JRadioButton rdbtnDentist = new JRadioButton("Dentist");
		rdbtnDentist.setOpaque(false);
		pfconformpass = new JPasswordField();
		pfconformpass.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		pfconformpass.setEditable(false);

		
		setPreferredSize(new Dimension(428, 417));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 814, 520);
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
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txtusername = new JTextField();
		txtusername.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		txtusername.setBackground(new Color(255, 250, 250));
		txtusername.addKeyListener(new KeyAdapter() { //event handling for username field.
			@Override
			public void keyTyped(KeyEvent arg0) {
				char keychar= arg0.getKeyChar();
				if(!Character.isAlphabetic(keychar)&&!Character.isDigit(keychar)&&keychar!='@'&&keychar!='_'&&keychar!='.')
				{
					arg0.consume();
				}
				if(txtusername.getText().length()>49) //starting from 0 and then setting the limit of the text field.
				{
					arg0.consume();
				}
			}
		});
		txtusername.setToolTipText("Enter your username");
		txtusername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtusername.setColumns(10);
		
		btnCheckusername = new JButton("Check");
		btnCheckusername.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnCheckusername.setForeground(new Color(51, 204, 51));
		btnCheckusername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openconnection();
				
				boolean a= rdbtnDentist.isSelected();
				boolean b=rdbtnReceptionist.isSelected();
				
				
				if(a==true)
				{
					try
					{
						rs=st.executeQuery("select * from dentistlogintable where username='"+txtusername.getText()+"'");
						if(!rs.next())
						{
							JOptionPane.showMessageDialog(null, "The username dosen't exist! Please try again!");
							txtusername.setText("");
						}
						else
						{	txtusername.setEditable(false);
						    rdbtnDentist.setEnabled(false);
						    rdbtnReceptionist.setEnabled(false);
							txtsecurityquestion.setText(rs.getString(7));
							pfcode.setEditable(true);
							txtanswer.setEditable(true);
						}
					}catch(Exception e1) {}
					 
				}
				
				if(b==true)
				{
					try
					{
						rs=st.executeQuery("select * from receptionlogintable where username='"+txtusername.getText()+"'");
						if(!rs.next())
						{
							JOptionPane.showMessageDialog(null, "The username dosen't exist! Please try again!");
							txtusername.setText("");
						}
						else
						{
							txtusername.setEditable(false);
							rdbtnDentist.setEnabled(false);
						    rdbtnReceptionist.setEnabled(false);
							txtsecurityquestion.setText(rs.getString(7));
							pfcode.setEditable(true);
							txtanswer.setEditable(true);
						}
					}catch(Exception e2) {}	
				}	
			}
		}
			);
		
		btnCheckusername.setBackground(new Color(255, 250, 250));
		btnCheckusername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblEnterCode = new JLabel("Enter Code");
		lblEnterCode.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblSecurityQuestion = new JLabel("Security Question");
		lblSecurityQuestion.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txtsecurityquestion = new JTextField();
		txtsecurityquestion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtsecurityquestion.setBackground(UIManager.getColor("Button.background"));
		txtsecurityquestion.setEditable(false);
		txtsecurityquestion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtsecurityquestion.setColumns(10);
		
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		txtanswer = new JPasswordField();
		txtanswer.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtanswer.setEditable(false);
		txtanswer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar = e.getKeyChar();
				if(!Character.isAlphabetic(keychar)&&!Character.isDigit(keychar))
				{
					e.consume();
				}
			}
		});
		txtanswer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtanswer.setColumns(10);
		
		JButton btnCheckanswer = new JButton("Check");
		btnCheckanswer.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnCheckanswer.setForeground(new Color(51, 204, 51));
		btnCheckanswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openconnection();
				boolean a= rdbtnDentist.isSelected();
				boolean b=rdbtnReceptionist.isSelected();
				String z = "";
				char c[]= pfcode.getPassword();
				String pwd = String.valueOf(c);//change from this
				int code=0;
				if(pwd.equals(z))
				{
					code=0;
				}
				else
				{
					code= Integer.parseInt(pwd);
				}
				int havecode = 0;
				int exception=0;
				try {
					ResultSet rs1=st.executeQuery("select * from code where code ="+code+"");
					if(!rs1.next())
					{
						JOptionPane.showMessageDialog(null, "Entered code is incorrect! Please enter again.");
						pfcode.setText("");
						exception=1;
						
					}
					else
					{
						havecode=rs1.getInt(1);
						z= String.valueOf(havecode);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				char ob[]=txtanswer.getPassword();
                String p=String.valueOf(ob);
				
					if(a==true)
					{
						try {
				   ResultSet rs1= st.executeQuery("select securityq,securitya from dentistlogintable where securityq='"+txtsecurityquestion.getText()+"' and securitya='"+p+"'");
				    if(!rs1.next())
				    {
				    	JOptionPane.showMessageDialog(null, "Entered answer is incorrect! Please try again");
				    	txtanswer.setText("");
				    	pfnewpass.setEditable(false);
				    	pfconformpass.setEditable(false);
				    }
				    else
				    {
				    	if(exception!=1)
				    	{
				    	pfnewpass.setEditable(true);
				    	pfconformpass.setEditable(true);
				    	}else
				    	{
				    		pfnewpass.setEditable(false);
					    	pfconformpass.setEditable(false);
				    	}
				    	
				    }
						}catch(Exception e) {}
					
					}
					
					if(b==true)
					{
						try {
							   ResultSet rs1= st.executeQuery("select securityq,securitya from receptionlogintable where securityq='"+txtsecurityquestion.getText()+"' and securitya='"+p+"'");
							    if(!rs1.next())
							    {
							    	JOptionPane.showMessageDialog(null, "Entered answer is incorrect! Please try again");
							    	txtanswer.setText("");
							    	pfnewpass.setEditable(false);
							    	pfconformpass.setEditable(false);
							    }
							    else
							    {
							    	if(exception!=1)
							    	{
							    	pfnewpass.setEditable(true);
							    	pfconformpass.setEditable(true);
							    	}
							    	else
							    	{
							    		pfnewpass.setEditable(false);
								    	pfconformpass.setEditable(false);
							    	}
							    }
									}catch(Exception e) {}
						
	      					
					}
					
				
					
				
			}
				
				});
		
		btnCheckanswer.setBackground(new Color(255, 250, 250));
		btnCheckanswer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		pfnewpass = new JPasswordField();
		pfnewpass.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		pfnewpass.setEditable(false);
		pfnewpass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar = e.getKeyChar();
				char s[]= pfnewpass.getPassword();
				if(!Character.isAlphabetic(keychar)&&!Character.isDigit(keychar)&&keychar!='@'&&keychar!='_'||s.length>14)
				{
					e.consume();
				}
			}
		});
		pfnewpass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblConformPassword = new JLabel("Confirm Password");
		lblConformPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		pfconformpass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar = e.getKeyChar();
				char s[]= pfconformpass.getPassword();
				if(!Character.isAlphabetic(keychar)&&!Character.isDigit(keychar)&&keychar!='@'&&keychar!='_'||s.length>14)
				{
					e.consume();
				}
			}
			});
		pfconformpass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnChange = new JButton("Change");
		btnChange.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnChange.setForeground(new Color(255, 255, 255));
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openconnection();
				
				boolean a= rdbtnDentist.isSelected();
				boolean b=rdbtnReceptionist.isSelected();
				char c[]= pfconformpass.getPassword();
				String cpwd = String.valueOf(c);
				char d[]= pfconformpass.getPassword();
				String pwd = String.valueOf(d);
				
				if(pwd.equals(cpwd))
				{
				  if(a==true)
				   {
					try {
						con.createStatement().execute("update dentistlogintable set password='"+cpwd+"' where username='"+txtusername.getText()+"'");
						txtusername.setText("");
						pfcode.setText("");
						pfcode.setEditable(false);
						txtanswer.setText("");
						txtanswer.setEditable(false);
						pfnewpass.setText("");
						pfnewpass.setEditable(false);
						pfconformpass.setText("");
						pfconformpass.setEditable(false);
						txtusername.setEditable(true);
						rdbtnReceptionist.setEnabled(true);
						rdbtnDentist.setEnabled(true);
						
						JOptionPane.showMessageDialog(null,"password successfully changed!!");
						
					}catch(Exception e1) {}
					}
				   
					
					if(b==true)
					{
						try {
							con.createStatement().execute("update receptionlogintable set password='"+pwd+"' where username='"+txtusername.getText()+"'");
							txtusername.setText("");
							pfcode.setText("");
							pfcode.setEditable(false);
							txtanswer.setText("");
							txtanswer.setEditable(false);
							pfnewpass.setText("");
							pfnewpass.setEditable(false);
							pfconformpass.setText("");
							pfconformpass.setEditable(false);
							txtusername.setEditable(true);
							rdbtnReceptionist.setEnabled(true);
							rdbtnDentist.setEnabled(true);
							
							JOptionPane.showMessageDialog(null,"password successfully changed!!");
							
						}catch(Exception e1) {}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Passwords do not match! please try again!");
					pfnewpass.setText("");
					pfconformpass.setText("");
					
				} 
				closeconnection();
			}
		});
		
		btnChange.setBackground(new Color(0, 204, 102));
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnReset.setForeground(new Color(255, 255, 255));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnDentist.setEnabled(true);
			    rdbtnReceptionist.setEnabled(true);
			    rdbtnDentist.setSelected(false);
			    rdbtnReceptionist.setSelected(false);
				txtusername.setText("");
				pfcode.setText("");
				pfcode.setEditable(false);
				txtanswer.setText("");
				txtanswer.setEditable(false);
				pfnewpass.setText("");
				pfnewpass.setEditable(false);
				pfconformpass.setText(""); //cleared all the statements of reset password.
				pfconformpass.setEditable(false);
				txtsecurityquestion.setText("");
				
			}
		});
		btnReset.setBackground(Color.RED);
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		pfcode = new JPasswordField();
		pfcode.setEditable(false);
		pfcode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pfcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keychar = e.getKeyChar();
				char s[] = pfcode.getPassword(); //character s for storing the password.
				if(!Character.isDigit(keychar) || s.length>5 )
				{
					e.consume();
				}
			}
		});
				rdbtnDentist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean a = rdbtnReceptionist.isSelected();
				boolean b = rdbtnDentist.isSelected();
				if(b==false)
				{
					txtusername.setEditable(false);
				}
				else
				{
					if(a==true)
					{
						rdbtnReceptionist.setSelected(false);
					}
					txtusername.setEditable(true);
				}
				
				
			}
		});
		rdbtnDentist.setSelected(true);
		rdbtnDentist.setBackground(Color.WHITE);
		rdbtnDentist.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		rdbtnReceptionist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a = rdbtnDentist.isSelected();
				boolean b = rdbtnReceptionist.isSelected();
				if(b==false)
				{
					txtusername.setEditable(false);
				}
				else
				{
					if(a==true)
					{
						rdbtnDentist.setSelected(false);
					}
					txtusername.setEditable(true);
				}
				
			}
		});
		rdbtnReceptionist.setBackground(Color.WHITE);
		rdbtnReceptionist.setFont(new Font("Tahoma", Font.BOLD, 18));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnChange, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addGap(239))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(rdbtnDentist)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnReceptionist)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 707, Short.MAX_VALUE)
							.addComponent(btnCheckanswer, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addGap(12))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblConformPassword)
								.addComponent(lblNewPassword))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(pfnewpass, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
								.addComponent(pfconformpass, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSecurityQuestion)
								.addComponent(lblEnterCode)
								.addComponent(lblUsername)
								.addComponent(lblAnswer))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(pfcode, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
								.addComponent(txtsecurityquestion, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
								.addComponent(txtanswer, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtusername, GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnCheckusername, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
							.addGap(12))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnDentist)
						.addComponent(rdbtnReceptionist))
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(txtusername, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCheckusername, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterCode)
						.addComponent(pfcode, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSecurityQuestion)
						.addComponent(txtsecurityquestion, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAnswer)
						.addComponent(txtanswer, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addComponent(btnCheckanswer, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewPassword)
						.addComponent(pfnewpass, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConformPassword)
						.addComponent(pfconformpass, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addGap(49)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChange, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
