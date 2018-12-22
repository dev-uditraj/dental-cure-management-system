package DCMSapp;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;

public class ViewBillD extends JFrame {
	static Connection con=null;
	static Statement st=null;
	static long getpatientid=0;
	static String getbillid=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	static JTable table;
	static DefaultTableModel model;
	static ResultSet loadcombobox;
	static long sum=0;
	private JTextField textFieldBill;
	static ViewBillD frame;
	@SuppressWarnings("unused")
	private static Image img;
    private Image scaled;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Image img = null;
					Image img2=null;
					img2 = ImageIO.read(getClass().getResource("/c.jpg")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(700, 650, Image.SCALE_DEFAULT); //this is for resizing the image
					frame = new ViewBillD(img);
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
	public  ViewBillD(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	@SuppressWarnings({ "static-access", "serial" })
	public ViewBillD(Image img) {
       setMinimumSize(new Dimension(700, 650));
		this.img = img;
	
		try {
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		setTitle("View Bill");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 699, 648);
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
		textFieldBill = new JTextField();
		textFieldBill.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textFieldBill.setBackground(SystemColor.menu);
		JLabel label = new JLabel("Details:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblBill = new JLabel("Bill:");
		lblBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnSave = new JButton("Ok");
		btnSave.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				frame.dispose();
			}
		});
		btnSave.setBackground(new Color(139,189,65));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnPrint.setBackground(new Color(242,171,42));
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		textField = new JTextField();
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textField.setBackground(SystemColor.menu);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setEditable(false);
		textField.setColumns(10);
		
		try {
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JLabel lblBillId = new JLabel("Bill ID:");
		lblBillId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		textFieldBill.setHorizontalAlignment(SwingConstants.CENTER);
		
		
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
			
			
			textFieldBill.setText(getbillid);
			
		}
		catch(Exception ex)
		{
		}
		
		
		
		textFieldBill.setEditable(false);
		textFieldBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldBill.setColumns(10);
		
		table = new JTable()
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
		       // return false;   //Disallow the editing of any cell
				return colIndex == 3; //this is for setting a specific column in the table only editable cheers :D!! 
		    }
				};
		table.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
				
		table.setRowHeight(25);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane_1.setViewportView(table);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		textArea.setBackground(Color.WHITE);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		model = new DefaultTableModel(new String[] {"S.NO","Description", "Price(RUP)","Quantity"}, 0);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setPreferredWidth(5000);
		table.getColumnModel().getColumn(2).setPreferredWidth(1000);
		table.getColumnModel().getColumn(3).setPreferredWidth(1000);
		
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
		
		table.getModel().addTableModelListener(new TableModelListener() //table model listener
				{
				
			public void tableChanged(TableModelEvent e) {
			int selectedrow=0;
				try {
					int getrowcount = model.getRowCount();
					sum=0;
					long finalqty=0;
					Object amt;
					String amtt;
					String qtyy;
					Object qty; 
					for(int j=0;j<getrowcount;j++)
					{	
						amt=model.getValueAt(j, 2);
						qty=model.getValueAt(j, 3);
						amtt=amt.toString();
						qtyy=qty.toString();
						finalqty = Long.parseLong(amtt) * Long.parseLong(qtyy);
						sum=sum+finalqty;
					}
					textField.setText(String.valueOf(sum));
					
				}catch(Exception t) {
					selectedrow= table.getSelectedRow();
					model.setValueAt("1", selectedrow, 3);
				}
			  }
				
				});
		
			
		
		qty.setDocument(new LimitedPlainDocument(3));
	    table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(qty));
		
		scrollPane.setViewportView(textArea);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(lblBill)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
					.addGap(1))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(457)
					.addComponent(lblTotal)
					.addGap(18)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(241)
					.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrint, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
					.addGap(231))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
							.addComponent(lblBillId)
							.addGap(4)
							.addComponent(textFieldBill, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(1))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(lblBillId))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldBill, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(15)
					.addComponent(lblBill)
					.addGap(7)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTotal))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnPrint, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
						.addComponent(btnSave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		
		//for checking and loading the patient information based upon the selection in the reception search info!
		
		try {
	    		  int row= EditBillD.table.getSelectedRow();
	    		  Object getappointmentid= EditBillD.model.getValueAt(row, 2);
	    		 String abc=getappointmentid.toString();
	    		  long no=Long.parseLong(abc);
	    		  ResultSet rs1=st.executeQuery("Select pk_patientid from appointmenttable where appointmentid="+no);
//	    		  while(rs1.next())
//	    		  {
//	    			  System.out.println(rs1.getInt(1));
//	    		  }
	    		  rs1.next();
	    		  long getpatientid = rs1.getInt(1);
	    	  ResultSet rs=st.executeQuery("Select * from patienttable where patientid="+getpatientid);
              if(rs.next())
              {
	    	  String n=rs.getString(2);
	    	  String g=rs.getString(3);
	    	  int ag=rs.getInt(5);
	    	  String ad=rs.getString(4);
	    	  String cont=rs.getString(6);
	    	  textArea.setText("Patient id:= "+String.valueOf(getpatientid)+"\nAppointmentid:= "+abc+"\n"
	    		    		+ "Patient Name:= "+n+"\n"
   		    		+ "Gender:= "+g+"\n"
   		    		+ "Age:= "+String.valueOf(ag)+"\n"
   		    		+ "Contact No:= "+cont+"\n"
	    		    		+ "Address:= "+ad+"\n");
	    	  ResultSet temp =st.executeQuery("select * from billstoragetable where fk_appointmentid="+no+"");
	    	  int count=0;
	    	  while(temp.next())
	    	  {
	    		  count++;
	    		  String desc = temp.getString(1);
	    		  String amt= temp.getString(2);
	    		  String qtyy= temp.getString(3);
	    		  model.addRow(new Object[]{count,desc,amt,qtyy});
	    	  }
	    	  }
		
		 } 
		catch(Exception e) {e.printStackTrace();}
	}
}



