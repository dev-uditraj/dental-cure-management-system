package DCMSapp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;

public class AppointmentDashboardUpdate extends JFrame {
	JComboBox<Object> comboBox = new JComboBox<Object>();
	JComboBox<Object> comboBox_1 = new JComboBox<Object>();
	JComboBox<Object> comboBox_2 = new JComboBox<Object>();
	static Connection con = null;
	static Statement st = null;
	ResultSet rs = null;
	static AppointmentDashboardUpdate frame;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextField textField;
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
					Image img2 = null;
					img2 = ImageIO.read(getClass().getResource("/nw.png")); // this is the another image object which is to be
																	// resized

					img = img2.getScaledInstance(600, 400, Image.SCALE_DEFAULT); // this is for resizing the image

					frame = new AppointmentDashboardUpdate(img);
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
	public AppointmentDashboardUpdate(String img) {
		this(new ImageIcon(img).getImage());
	}

	/**
	 * @wbp.parser.constructor
	 */

	@SuppressWarnings({ "static-access", "serial" })
	public AppointmentDashboardUpdate(Image img) {
		setMinimumSize(new Dimension(600, 400));

		this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				if (st != null) {
					st = null;
				}
				if (con != null) {
					con = null;
				}
				try {
					DriverManager.getConnection(dbcon.JDBC_URLS);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block

				}

			}
		});

		Date one = new Date(System.currentTimeMillis()); // setting current date as default input in the date picker

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setMinimumSize(new Dimension(600, 400));
		JRadioButton rdbtnToday = new JRadioButton("Today");
		rdbtnToday.setOpaque(false);
		rdbtnToday.setBounds(178, 14, 77, 31);
		JRadioButton rdbtnTommorow = new JRadioButton("Tommorow");
		rdbtnTommorow.setOpaque(false);
		rdbtnTommorow.setBounds(259, 14, 119, 31);

		JRadioButton rdbtnSpecifyManually = new JRadioButton("Specify manually");
		rdbtnSpecifyManually.setOpaque(false);
		rdbtnSpecifyManually.setBounds(378, 14, 161, 31);

		setResizable(false);
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dateChooser.setBounds(222, 81, 188, 31);
		dateChooser.setEnabled(false);
		dateChooser.setDate(one);
		setTitle("Appointment");
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
		contentPane.setBackground(Color.WHITE);
		// contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel label = new JLabel("Select Date:");
		label.setBounds(66, 18, 104, 22);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel label_1 = new JLabel("Select Time:");
		label_1.setBounds(66, 149, 97, 22);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton btn_attach = new JButton("Attach");
		btn_attach.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_attach.setEnabled(false);
		btn_attach.setBounds(154, 301, 141, 31);
		btn_attach.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				String finaldateandtime = null;
				String getdate = null;
				String custom = null;
				String nextDate = null;
				String todaydate = null;

				boolean a = rdbtnToday.isSelected();
				boolean b = rdbtnTommorow.isSelected();
				boolean c = rdbtnSpecifyManually.isSelected();
				int exception = 0;
				if (a == true) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					todaydate = (dtf.format(now));
					getdate = todaydate;

				}

				else if (b == true) {
					try {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDateTime now = LocalDateTime.now().plusDays(1);
						nextDate = (dtf.format(now));
						getdate = nextDate;
						System.out.println(nextDate);
					} catch (Exception e) {
					}

				}

				else if (c == true) {

					String date = Integer.toString(dateChooser.getDate().getDate());
					int plusmonth;
					plusmonth = dateChooser.getDate().getMonth();
					plusmonth++;
					String month = Integer.toString(plusmonth);
					String test = dateChooser.getDate().toString();
					char[] finaldate = new char[4];
					test.getChars(24, 28, finaldate, 0);
					String year = String.valueOf(finaldate);
					custom = year + "-" + month + "-" + date;

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					todaydate = (dtf.format(now));

					try {
						Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(custom);
						Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(todaydate);
						int isdatevalid = date1.compareTo(date2);
						if (isdatevalid < 0) {
							JOptionPane.showMessageDialog(null,
									"the date selected is before the current date, please select other date!");
							exception = 1;
						} else if (isdatevalid == 0) {
							JOptionPane.showMessageDialog(null,
									"The date selected is the current date, please select the today's date radio button instead!");
							exception = 1;
						} else {
							exception = 0;
							getdate = custom;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				if (exception == 0) {

					String combobox = (String) comboBox.getSelectedItem();
					String combobox_1 = (String) comboBox_1.getSelectedItem();
					String combobox_2 = (String) comboBox_2.getSelectedItem();

					if (combobox_2.equals("PM")) {
						int hour = (int) (comboBox.getSelectedIndex());
						hour = 13 + hour;
						combobox = Integer.toString(hour);
					}

					String wholetime = combobox + ":" + combobox_1 + ":" + "00";
					finaldateandtime = getdate + " " + wholetime;

					int column = 1;
					int row = DashboardReception.table.getSelectedRow();
					String value = DashboardReception.table.getModel().getValueAt(row, column).toString();
					long getappointmentid = Integer.parseInt(value);

					try {
						con.createStatement().execute("update appointmenttable set datetime='" + finaldateandtime
								+ "' where appointmentid=" + getappointmentid + "");
					} catch (Exception e) {
						e.printStackTrace();
					}

					DashboardReception.model.setRowCount(0);
					DashboardReception.updatetable();

					if (st != null) {
						st = null;
					}
					if (con != null) {
						con = null;
					}
					try {
						DriverManager.getConnection(dbcon.JDBC_URLS);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block

					}

					frame.dispose();
				}
			}
		});

		btn_attach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_attach.setBackground(Color.WHITE);

		JButton btn_clear = new JButton("Clear");
		btn_clear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btn_clear.setForeground(Color.WHITE);
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnToday.setSelected(false);
				rdbtnTommorow.setSelected(false);
				rdbtnSpecifyManually.setSelected(false);
				dateChooser.setEnabled(false);
				comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				comboBox_2.setSelectedIndex(0);
			}
		});
		btn_clear.setBounds(313, 301, 141, 31);
		btn_clear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_clear.setBackground(Color.RED);
		comboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		comboBox.setBounds(222, 146, 83, 31);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		comboBox_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		comboBox_1.setBounds(317, 146, 83, 31);
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_1.setBackground(Color.WHITE);
		comboBox_1.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
		comboBox_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_2.setForeground(Color.RED);

		comboBox_2.setBounds(412, 146, 79, 31);
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_2.setBackground(Color.WHITE);
		comboBox_2.setModel(new DefaultComboBoxModel<Object>(new String[] { "AM", "PM" }));

		rdbtnSpecifyManually.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a = rdbtnSpecifyManually.isSelected();
				if (a == true) {
					rdbtnToday.setSelected(false);
					rdbtnTommorow.setSelected(false);
					dateChooser.setEnabled(true);
					btn_attach.setEnabled(true);

				} else {
					dateChooser.setEnabled(false);

					btn_attach.setEnabled(false);

				}
			}
		});

		rdbtnTommorow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a = rdbtnTommorow.isSelected();
				if (a == true) {
					rdbtnToday.setSelected(false);
					rdbtnSpecifyManually.setSelected(false);
					dateChooser.setEnabled(false);
					btn_attach.setEnabled(true);
				} else {
					btn_attach.setEnabled(false);
				}
			}
		});

		rdbtnToday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a = rdbtnToday.isSelected();
				if (a == true) {
					rdbtnTommorow.setSelected(false);
					rdbtnSpecifyManually.setSelected(false);
					dateChooser.setEnabled(false);
					btn_attach.setEnabled(true);
				} else {
					btn_attach.setEnabled(false);
				}
			}
		});
		rdbtnToday.setBackground(Color.WHITE);
		rdbtnToday.setForeground(Color.BLACK);
		rdbtnToday.setFont(new Font("Tahoma", Font.PLAIN, 18));

		rdbtnTommorow.setBackground(Color.WHITE);
		rdbtnTommorow.setFont(new Font("Tahoma", Font.PLAIN, 18));

		rdbtnSpecifyManually.setBackground(Color.WHITE);
		rdbtnSpecifyManually.setFont(new Font("Tahoma", Font.PLAIN, 18));

		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooser.setBackground(Color.WHITE);
		dateChooser.setDateFormatString("dd-MM-yyyy");

		JLabel lblAppointmentId = new JLabel("Appointment ID:");
		lblAppointmentId.setBounds(66, 209, 132, 22);
		lblAppointmentId.setFont(new Font("Tahoma", Font.PLAIN, 18));

		contentPane.setLayout(null);
		contentPane.add(dateChooser);
		contentPane.add(label_1);
		contentPane.add(comboBox);
		contentPane.add(comboBox_1);
		contentPane.add(comboBox_2);
		contentPane.add(btn_attach);
		contentPane.add(btn_clear);
		contentPane.add(lblAppointmentId);
		contentPane.add(label);
		contentPane.add(rdbtnToday);
		contentPane.add(rdbtnTommorow);
		contentPane.add(rdbtnSpecifyManually);

		textField = new JTextField();
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textField.setBackground(SystemColor.menu);
		try {
			try {
				Class.forName(dbcon.DRIVER);
				con = DriverManager.getConnection(dbcon.JDBC_URL);
				st = con.createStatement();
			} catch (Exception e) {
				e.printStackTrace();
			}

			rs = st.executeQuery("select max (appointmentid) from appointmenttable");
			rs.next();
			int id = rs.getInt(1);
			if (id > 0) {
				id = id + 1;
			} else {
				id = 1;
			}
			String a_id = String.valueOf(id);
			textField.setText(a_id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setEditable(false);
		textField.setBounds(222, 211, 116, 31);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
