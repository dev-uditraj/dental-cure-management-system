package DCMSapp;

import java.awt.EventQueue;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
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
import java.awt.Image;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;





public class DashboardReception extends JFrame{
	static Connection con=null;
	static Statement st=null;
	static ResultSet rs=null;
	static DashboardReception frame;
	

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
	@SuppressWarnings("unused")
	private static Image img;
    private Image scaled;
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Image img = null;
					Image img2=null;
					img2 = ImageIO.read(getClass().getResource("/nw.png")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(800, 600, Image.SCALE_DEFAULT); //this is for resizing the image
					frame = new DashboardReception(img);
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
		
		
		DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
		LocalDateTime now = LocalDateTime.now();
		String currentsystemdatetime =dtf.format(now);
		
		
		int count=0;
		try {
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
		
	}
	public DashboardReception(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	
	@SuppressWarnings({ "static-access", "serial" })
	public DashboardReception(Image img) {
			setSize(new Dimension(800, 632));
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
		
		setMinimumSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel() {  
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
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(f);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(f);
		
		JButton btnRegistration = new JButton("New Registration");
		btnRegistration.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnRegistration.setForeground(new Color(255, 255, 255));
		btnRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isregwinopen==0)
				{
					Patientreg.main(null);
					isregwinopen++;
				}
				
			}
		});
		btnRegistration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistration.setBackground(new Color(139,189,65));
		btnRegistration.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRegistration.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(issearchwinopen==0)
				{
					SearchInfoReception.main(null);
					issearchwinopen++;
				}
				
			}
		});
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setBackground(new Color(242,171,42));
		btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSearch.setFont(f);
		
		JLabel lblTodaysAppointment = new JLabel("Today's Appointment(s)");
		lblTodaysAppointment.setFont(f);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(f);
		
		lblCurrent = new JLabel("Current");
		lblCurrent.setFont(f);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 215, 0)));
		btnEdit.setForeground(new Color(0, 0, 0));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				int column = 1;
				int row = table.getSelectedRow();
				if(row==-1)
				{
					JOptionPane.showMessageDialog(null, "There is no row selected or either there is no data present!");
				}
				else
				{
					AppointmentDashboardUpdate.main(null);
				}
				
			}
		});
		btnEdit.setBackground(new Color(255, 255, 255));
		
		JButton btnDone = new JButton("Done");
		btnDone.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(51, 204, 51)));
		btnDone.setForeground(new Color(0, 0, 0));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				int column = 1;
				int row = table.getSelectedRow();
				if(row==-1)
				{
					JOptionPane.showMessageDialog(null, "There is no row selected or either there is no data present!");
				}
				else
				{
				String value = table.getModel().getValueAt(row, column).toString();
				long getappointmentid = Integer.parseInt(value);
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you u want to remove the selected appointment from the list?");
				if(a==0)
				{
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
				try {
					
					con.createStatement().execute("update appointmenttable set isdeleted='t' where appointmentid="+getappointmentid+"");
				}
				catch(Exception e)
				{
				e.printStackTrace();	
				}
				
				model.setRowCount(0);
				updatetable();	
					
				}
				else
				{
					
				}
				}
			}
		});
		btnDone.setBackground(new Color(255, 255, 255));
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnLogout.setForeground(new Color(255, 255, 255));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				dbcon.main(null);
			}
		});
		btnLogout.setBackground(new Color(255, 0, 0));
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		lblTimenow = new JLabel();
		lblTimenow.setForeground(Color.DARK_GRAY);
		setdate();
		lblTimenow.setFont(new Font("Tahoma", Font.ITALIC, 18));
		
		
		lblDatenow.setFont(new Font("Tahoma", Font.ITALIC, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTodaysAppointment, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
					.addGap(346)
					.addComponent(lblTotal)
					.addGap(13)
					.addComponent(lblCurrent)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDone, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDate)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDatenow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTimenow, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 534, Short.MAX_VALUE)
					.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
					.addGap(18))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(215)
					.addComponent(btnRegistration, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
					.addGap(216))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTime)
								.addComponent(lblTimenow))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDate)
								.addComponent(lblDatenow)))
						.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRegistration, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTodaysAppointment)
						.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDone, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotal)
						.addComponent(lblCurrent))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
					.addGap(40))
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
		table.setBackground(Color.WHITE);
		table.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		table.setGridColor(SystemColor.activeCaption);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(new Color(164,180,189));
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
		int a=model.getRowCount();
		lblCurrent.setText(Integer.toString(a));
		
			
			
		}
	}
	

