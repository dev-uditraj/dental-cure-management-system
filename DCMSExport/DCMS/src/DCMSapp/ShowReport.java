package DCMSapp;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;

public class ShowReport extends JFrame {
	static Connection con=null;
	static Statement st=null;
public static JTextArea txtrDateAndTime;
	ResultSet rs=null;
	String appointmentdate=null;
	String appointmenthour=null;
	String appointmentmin=null;
	String ampm=null;
	static ShowReport frame;
	public static String transferdate;
	public static String transfertime;
	public static String transferappointmentid;
	public static JTextArea txtarea_pr_address;
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
		             
		             img=img2.getScaledInstance(600, 401, Image.SCALE_DEFAULT); //this is for resizing the image
					frame = new ShowReport(img);
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
	public ShowReport(String img) {
        this(new ImageIcon(img).getImage());
    }
		/**
		 * @wbp.parser.constructor
		 */
	@SuppressWarnings({ "static-access", "serial" })
	public ShowReport(Image img) {
		 setMinimumSize(new Dimension(649, 650));
			
			this.img = img;
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				
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
		txtarea_pr_address = new JTextArea();
		txtarea_pr_address.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtarea_pr_address.setEditable(false);
		txtarea_pr_address.setLineWrap(true);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setTitle("Clinic Report");
		setBounds(100, 100, 649, 650);
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
	//	contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_pr_address = new JLabel("Details:");
		lbl_pr_address.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		
		
		JButton btn_pr_save = new JButton("Print");
		btn_pr_save.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btn_pr_save.setForeground(Color.WHITE);
		btn_pr_save.setBackground(new Color(242,171,42));
		btn_pr_save.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btn_pr_clear = new JButton("Clear");
		btn_pr_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtrDateAndTime.setText("Date not selected yet!");
				txtarea_pr_address.setText("");
				
			}
		});
		btn_pr_clear.setForeground(Color.WHITE);
		btn_pr_clear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_pr_clear.setBackground(Color.RED);
		
		JButton btn_pr_set = new JButton("Choose Date");
		btn_pr_set.setForeground(Color.WHITE);
		btn_pr_set.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_pr_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReportDate.main(null);
			}
		});
		btn_pr_set.setBackground(new Color(139,189,65));
		btn_pr_set.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblAppointmentStatus = new JLabel("Select Date and Time:");
		lblAppointmentStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtrDateAndTime = new JTextArea();
		txtrDateAndTime.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtrDateAndTime.setFont(new Font("Monospaced", Font.BOLD, 14));
		
			txtrDateAndTime.setText("Date not selected yet!");
		
		
		
		txtrDateAndTime.setEditable(false);
		txtrDateAndTime.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton button = new JButton("Ok");
		button.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame.dispose();
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setBackground(new Color(139,189,65));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_pr_address, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAppointmentStatus)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(19)
											.addComponent(btn_pr_set, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addComponent(txtrDateAndTime, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)))
							.addGap(2))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_pr_save, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_pr_clear, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(129))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtrDateAndTime, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAppointmentStatus)
							.addGap(6)
							.addComponent(btn_pr_set, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
							.addComponent(lbl_pr_address, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_pr_save, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_pr_clear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(26))
		);
		
		scrollPane.setViewportView(txtarea_pr_address);
		
		
		txtarea_pr_address.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtarea_pr_address.setBackground(Color.WHITE);
		contentPane.setLayout(gl_contentPane);
		
		
		
		
	
	}
}
