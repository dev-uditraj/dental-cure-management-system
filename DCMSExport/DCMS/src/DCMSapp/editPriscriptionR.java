package DCMSapp;
import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;




@SuppressWarnings("unused")
public class editPriscriptionR extends JFrame{
	static Connection con=null;
	static Statement st=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTable table;
	static DefaultTableModel model;
	int sno=0;
	static int preid=0;
	static editPriscriptionR frame;
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
					img2 = ImageIO.read(getClass().getResource("/nw.png")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(600, 401, Image.SCALE_DEFAULT); //this is for resizing the image
					frame = new editPriscriptionR(img);
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
	public editPriscriptionR(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	@SuppressWarnings({ "static-access", "serial" })
	public editPriscriptionR(Image img)  {
		 setMinimumSize(new Dimension(600, 700));
			
			this.img = img;
		setBackground(Color.WHITE);
		setTitle("Prescription");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 609, 500);
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
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnSave = new JButton("OK");
		btnSave.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame.dispose();
				
			}});
		btnSave.setBackground(new Color(139,189,65));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblPrescriptionId = new JLabel("Prescription ID:");
		lblPrescriptionId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblId = new JLabel("id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setForeground(Color.WHITE);
		btnPrint.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrint.setBackground(new Color(242,171,42));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblPrescriptionId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblId)
					.addContainerGap(352, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(173, Short.MAX_VALUE)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(162))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrescriptionId)
						.addComponent(lblId))
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnPrint, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addGap(21))
		);
		
		table = new JTable()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();

		    { // initializer block
		        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		    }

		    @Override
		    public TableCellRenderer getCellRenderer (int arg0, int arg1) {
		        return renderCenter;
		    }
		    //this is a boolean array that lets you set the specific column to be editable or not!!
		    boolean[] canEdit = new boolean[]{
                    false,true,true,true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }

		    
					
			
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(SystemColor.menu);
		table.setRowMargin(0);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setRowHeight(25);
		model = new DefaultTableModel(new String[]{"Sno.", "Medicine Name", "Frequency","Qty"}, 0);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setPreferredWidth(5000);
		table.getColumnModel().getColumn(2).setPreferredWidth(1000);
		table.getColumnModel().getColumn(3).setPreferredWidth(1000);
		
		
		
		JTextField disc = new JTextField();
		disc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isLetterOrDigit(keychar)&&keychar!=' ')
				{
					ke.consume();
				}
			}
		});
		
		disc.setDocument(new LimitedPlainDocument(200));
	    table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(disc));
	    setSize(300,300);
	    
	    
		JTextField freq = new JTextField();
		freq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isDigit(keychar))
				{
					ke.consume();
				}
			}
		});
		
	    
	    
	    freq.setDocument(new LimitedPlainDocument(2));
	    table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(freq));
	    setSize(300,300);
	    
	    JTextField qty = new JTextField();
		qty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isDigit(keychar))
				{
					ke.consume();
				}
			}
		});
		
	    
	    
	    qty.setDocument(new LimitedPlainDocument(7));
	    table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(qty));
	    setSize(300,300);
	    
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		
		
		try {
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
			int row= viewPrescriptionR.table.getSelectedRow();
			int appidcolumn= 2;
			int appidpresc=1;
			String appid=viewPrescriptionR.table.getModel().getValueAt(row, appidcolumn).toString();
			String presid=viewPrescriptionR.table.getModel().getValueAt(row, appidpresc).toString();
			long appointmentid= Long.parseLong(appid);
			lblId.setText(presid);
			
			int count=0;
			
			ResultSet getdata= st.executeQuery("select medicinename,frequency,quantity from prescriptionstoragetable where fk_appointmentid="+appointmentid+"");
			while(getdata.next())
			{
				count++;
				String medicinename = getdata.getString(1);
				int frequency = getdata.getInt(2);
				int quantity = getdata.getInt(3);
				model.addRow(new Object[]{count,medicinename,frequency,quantity});
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}

