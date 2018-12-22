package DCMSapp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JRadioButton;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Login extends JFrame {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	static Login frame;
	static int isformopen=0;
	static int isresetopen=0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pf_log_pass;
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
						img2 = ImageIO.read(getClass().getResource("/pe.jpg"));
						
					
		              //this is the another image object which is to be resized 
		       
		             img=img2.getScaledInstance(650, 400, Image.SCALE_DEFAULT); //this is for resizing the image 

					 frame = new Login(img);
					 frame.setIconImage(new ImageIcon(getClass().getResource("/logo1.jpg")).getImage());
					 frame.pack();
					 frame.setLocationRelativeTo(null); //this is for the frame to open always on the center!!
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
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e){}
		
	}
	
	public void closeconnection()
	{
				st=null;
				con=null;
			try {
				DriverManager.getConnection(dbcon.JDBC_URLS);
			} catch (SQLException e2) {}			
	}
	
	/**
	 * Create the frame.
	 */
	public Login(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "serial", "static-access" })
	public Login(Image img) {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent arg0) {
					
					closeconnection();
					
				}
			});
         openconnection();
		setMinimumSize(new Dimension(650, 400));
		
		this.img = img;
		
		setResizable(false);
		setTitle("Login Window");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 360);
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
		
		JLabel lblDentalCureManagement = new JLabel("  Dental Cure Management System");
		lblDentalCureManagement.setForeground(Color.DARK_GRAY);
		lblDentalCureManagement.setBorder(null);
		lblDentalCureManagement.setFont(new Font("HP Simplified Light", Font.BOLD, 38));
		
		JLabel lbl_log_user = new JLabel("User Name*");
		lbl_log_user.setForeground(Color.DARK_GRAY);
		lbl_log_user.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JTextField txt_log_user = new JTextField();
		txt_log_user.setBackground(new Color(255, 250, 250));
		txt_log_user.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		txt_log_user.setToolTipText("Max length 15,alphanumeric,special characters(@,_ )");
		
		txt_log_user.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isLetterOrDigit(keychar)&&keychar!='@'&&keychar!='_'&&keychar!='.')
				{
					ke.consume();
				}
				if(txt_log_user.getText().length()>49)
				{
					ke.consume();
				}
			}
		});
		
		txt_log_user.setPreferredSize(new Dimension(52, 14));
		txt_log_user.setMinimumSize(new Dimension(52, 14));
		txt_log_user.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_log_user.setColumns(10);
		
		JLabel lbl_log_pass = new JLabel("Password*");
		lbl_log_pass.setForeground(Color.DARK_GRAY);
		lbl_log_pass.setPreferredSize(new Dimension(52, 14));
		lbl_log_pass.setMinimumSize(new Dimension(52, 14));
		lbl_log_pass.setMaximumSize(new Dimension(52, 14));
		lbl_log_pass.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		pf_log_pass = new JPasswordField();
		pf_log_pass.setBackground(new Color(255, 250, 250));
		pf_log_pass.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		pf_log_pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke1) {
				char ch=ke1.getKeyChar();
				if(!Character.isLetterOrDigit(ch)&&ch!='@'&&ch!='_')
				{
					ke1.consume();
				}
				if(pf_log_pass.getPassword().length>14)
				{
					ke1.consume();
				}
			}
		});
		pf_log_pass.setPreferredSize(new Dimension(52, 14));
		pf_log_pass.setMinimumSize(new Dimension(52, 14));
		pf_log_pass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel label_3 = new JLabel("Did you forget your password?");
		label_3.setBackground(new Color(255, 255, 240));
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton btn_log_reset = new JButton("Reset Now");
		btn_log_reset.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_log_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isresetopen==0)
				{
				ResetPassword.main(null);
				isresetopen++;
				}
				
			}
		});
		
		btn_log_reset.setForeground(Color.RED);
		btn_log_reset.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_log_reset.setBackground(new Color(255, 250, 250));
		
		JButton btn_log_login = new JButton("Login");
		btn_log_login.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		JRadioButton rbtn_log_dentist = new JRadioButton("Dentist");
		rbtn_log_dentist.setForeground(Color.DARK_GRAY);
		rbtn_log_dentist.setOpaque(false);
		JRadioButton rbtn_log_reception = new JRadioButton("Receptionist");
		rbtn_log_reception.setForeground(Color.DARK_GRAY);
		rbtn_log_reception.setOpaque(false);
		
		btn_log_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openconnection();

                     boolean state=rbtn_log_dentist.isSelected();
                     boolean state1=rbtn_log_reception.isSelected();
                     String u=txt_log_user.getText();
                     char ob[]=pf_log_pass.getPassword();
                     String p=String.valueOf(ob);
                     
                     if(u.equals("")||p.equals(""))
                     {
                    	JOptionPane.showMessageDialog(null, "All fields are manadtory!! please try again!!"); 
                    	
                     }
                     else
                     {
                     if(state==true)
                     {  
                     
                    	 try
                       {
                    	 rs=st.executeQuery("select userid,username,password from dentistlogintable where username='"+u+"' and password='"+p+"'");
                    	 if(!rs.next())
                    	 {
                    		 JOptionPane.showMessageDialog(null, "Invalid user name or password!!Try again!!");
                    	 }
                    	 else
                    	 {
                    		 DashboardDentist.main(null);
                    		 txt_log_user.setText("");
                   	    	 pf_log_pass.setText("");
                   	    	 frame.dispose();
                    	 }
                    	 
                       }	 
                     catch(Exception ex)
                     {
                    	 ex.printStackTrace();
                     }
                     }
                     
                     else if(state1==true)
                     {  
                    	 try
                     {
                  	 rs=st.executeQuery("select username,password from receptionlogintable where username='"+u+"' and password='"+p+"'");
                  	  if(!rs.next())
                	 {
               		    JOptionPane.showMessageDialog(null, "Invalid user name or password!!Try again!!");
                 	 }
                 	 else
               	    {
               	    	 DashboardReception.main(null);
               	    	 txt_log_user.setText("");
               	    	 pf_log_pass.setText("");
               	    	 frame.dispose();
               	    	 
               	    }
                     }
                   catch(Exception ex)
                   {
                  	 ex.printStackTrace();
                   } 
                    	 
                    	 }
                     }
                     closeconnection();
                     }
			}
		);
		
		
		btn_log_login.setEnabled(false);
		btn_log_login.setForeground(Color.BLACK);
		btn_log_login.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_log_login.setBackground(new Color(255, 250, 250));
		
		JButton btn_log_clear = new JButton("Clear");
		btn_log_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_log_clear.setForeground(new Color(255, 255, 255));
		btn_log_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_log_user.setText("");
				pf_log_pass.setText("");
				rbtn_log_dentist.setSelected(false);
				rbtn_log_reception.setSelected(false);
				btn_log_login.setEnabled(false);
			}
		});
		btn_log_clear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_log_clear.setBackground(new Color(255, 51, 51));
		
		JButton btn_log_new = new JButton("New User");
		btn_log_new.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_log_new.setForeground(new Color(255, 255, 255));
		btn_log_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(isformopen==0)
				{
					NewUser.main(null);
					isformopen++;
				}
				
				}
		});
		btn_log_new.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_log_new.setBackground(new Color(0, 204, 102));
		
	
		rbtn_log_reception.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(rbtn_log_reception.isSelected()==true)
                 {
                	 btn_log_login.setEnabled(true);
                	 rbtn_log_dentist.setSelected(false);
                }
                 else
                 {
                	 btn_log_login.setEnabled(false);
                 }
			}
		});
	     rbtn_log_dentist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                     if(rbtn_log_dentist.isSelected()==true)
                     {
                    	 btn_log_login.setEnabled(true);
                    	 rbtn_log_reception.setSelected(false);
                    }
                     else
                     {
                    	 btn_log_login.setEnabled(false);
                     }
                     
			
			}
		});
		
		rbtn_log_dentist.setBackground(Color.WHITE);
		rbtn_log_dentist.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		rbtn_log_reception.setBackground(Color.WHITE);
		rbtn_log_reception.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lbl_log_user, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
									.addComponent(lbl_log_pass, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(txt_log_user, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
									.addComponent(pf_log_pass, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(btn_log_reset, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btn_log_login, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btn_log_clear, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btn_log_new, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(rbtn_log_dentist)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtn_log_reception)))
					.addGap(258))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(17)
					.addComponent(lblDentalCureManagement, GroupLayout.PREFERRED_SIZE, 604, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDentalCureManagement)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtn_log_dentist)
						.addComponent(rbtn_log_reception))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_log_user, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lbl_log_pass, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_log_reset, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(1)
									.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txt_log_user, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pf_log_pass, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addGap(45)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_log_login, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_log_clear, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_log_new, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
