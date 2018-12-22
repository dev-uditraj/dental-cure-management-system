package DCMSapp;
import java.awt.EventQueue;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.derby.drda.NetworkServerControl;
import java.net.InetAddress;
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;



public class DashboardDentist extends JFrame {
	static Connection con=null;
	static Statement st=null;
	static ResultSet rs=null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JLabel lblTimenow;
	static int isregwinopen=0;
	static int issearchwinopen=0;
	static JTable table;
	static DefaultTableModel model;
	static JLabel lblCurrent;
	static int a;
	static DashboardDentist frame;
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
					img2 = ImageIO.read(getClass().getResource("/c.jpg")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(800, 632, Image.SCALE_DEFAULT); //this is for resizing the image
					frame = new DashboardDentist(img);
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
	 * @return 
	 * @wbp.parser.constructor
	 */
	public DashboardDentist(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	public void setdate()
	{
		ActionListener actiondate = new ActionListener()
				{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			DateTimeFormatter dtf= DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			lblTimenow.setText(dtf.format(now));
				
			}
				};
				new javax.swing.Timer(1000, actiondate).start();
		
	}
	public static void updatetable()
	
	{
		
		
		
		DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
		LocalDateTime now = LocalDateTime.now();
		String currentsystemdatetime =dtf.format(now);
		
		
		
		ActionListener showrow = new ActionListener()
		{
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			NetworkServerControl server = new NetworkServerControl  //this is for starting the server
				    (InetAddress.getByName("localhost"),1527);
				server.start(null);
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		int count=0;
		try {
			model.setRowCount(0);
		rs=st.executeQuery("select appointmenttable.appointmentid,patienttable.patientid,patienttable.name,patienttable.gender,patienttable.contact,appointmenttable.datetime from patienttable join appointmenttable on patienttable.patientid = appointmenttable.pk_patientid AND appointmenttable.isdeleted='f' AND appointmenttable.datetime >'"+currentsystemdatetime+"' order by appointmenttable.datetime asc");
		while(rs.next())
		{
			count++;
			int appointmentid= rs.getInt(1);
			int patientid=rs.getInt(2);
			String patientname=rs.getString(3);
			String gender=rs.getString(4);
			String contact=rs.getString(5);
			Date date= rs.getDate(6);
			Time time= rs.getTime(6);
		    model.addRow(new Object[]{count,appointmentid,patientid,patientname,gender,contact,date,time});
		    
		  }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		a=model.getRowCount();
		lblCurrent.setText(Integer.toString(a));
	}
		};
		new javax.swing.Timer(1000, showrow).start();
		
		
		
	}
	
	@SuppressWarnings({ "static-access", "serial" })
	public DashboardDentist(Image img) {
        setMinimumSize(new Dimension(800, 632));
		
		this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				JOptionPane.showMessageDialog(null,"Please logout the account before closing the software!");
			}
		});

		
		//>>>>>this is for the current date! 
		JLabel lblDatenow = new JLabel("datenow");
		lblDatenow.setForeground(Color.DARK_GRAY);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime now = LocalDateTime.now();
			lblDatenow.setText(dtf.format(now));
		
		
		
		
		Font f=new Font("Tahoma", Font.PLAIN, 18); //Font
		setTitle("Dashboard");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 896, 632);
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
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(f);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(f);
		
		
		JButton btnSearch = new JButton("Search Information");
		btnSearch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(issearchwinopen==0)
				{
					SearchInfoDentistt.main(null);
					issearchwinopen++;
				}
				
			}
		});
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setBackground(new Color(242,171,42));
		btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSearch.setFont(f);
		
		JLabel lblTodaysAppointment = new JLabel("Today's Appointment(s):");
		lblTodaysAppointment.setFont(f);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(f);
		
		lblCurrent = new JLabel("Current");
		lblCurrent.setFont(f);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				dbcon.main(null);
			}
		});
		btnLogout.setBackground(Color.RED);
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		lblTimenow = new JLabel();
		lblTimenow.setForeground(Color.DARK_GRAY);
		setdate();
		lblTimenow.setFont(new Font("Tahoma", Font.ITALIC, 18));
		
		
		lblDatenow.setFont(new Font("Tahoma", Font.ITALIC, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblDate)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblDatenow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblTime)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblTimenow, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 617, Short.MAX_VALUE)
							.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTodaysAppointment, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
							.addGap(55)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
							.addGap(205)
							.addComponent(lblTotal)
							.addGap(13)
							.addComponent(lblCurrent)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTime)
										.addComponent(lblTimenow))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblDate)
										.addComponent(lblDatenow)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
							.addGap(39)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTodaysAppointment)
								.addComponent(lblTotal)
								.addComponent(lblCurrent))
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(34)))
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		
		
		table = new JTable()  //this set the selectable and editable text to null!!
				{
			DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();

		    { // initializer block
		        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		    }

		    @Override
		    public TableCellRenderer getCellRenderer (int arg0, int arg1) {
		        return renderCenter;
		    }
					private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
		        return false;   //Disallow the editing of any cell
		    }
				};
		table.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(SystemColor.menu);
		table.setRowMargin(0);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setRowHeight(25);
		model = new DefaultTableModel(new String[]{"Sno.", "App_ID", "Pat_ID", "Patient Name","Gender", "Contact No.", "Date", "Time"}, 0);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(500);
		table.getColumnModel().getColumn(3).setPreferredWidth(2000);
		table.getColumnModel().getColumn(4).setPreferredWidth(500);
		table.getColumnModel().getColumn(5).setPreferredWidth(1000);
		table.getColumnModel().getColumn(6).setPreferredWidth(1000);
		table.getColumnModel().getColumn(7).setPreferredWidth(1000);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		updatetable();
		lblCurrent.setText(Integer.toString(a));
		
			
			
		}
		
		
	}
	

