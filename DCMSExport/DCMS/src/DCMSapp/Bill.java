package DCMSapp;
import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.MatteBorder;

public class Bill extends JFrame {
	static Connection con=null;
	static Statement st=null;
	static long getpatientid=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	static JTable table;
	static DefaultTableModel model;
	static ResultSet loadcombobox;
	@SuppressWarnings("rawtypes")
	static JComboBox comboBox;
	static long sum=0;
	private JTextField textFieldBill;
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
					img2 = ImageIO.read(getClass().getResource("/nw.png")); //this is the another image object which is to be resized 
		             
		             img=img2.getScaledInstance(700, 580, Image.SCALE_DEFAULT); //this is for resizing the image

					Bill frame = new Bill(img);
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
	public Bill(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access", "serial" })
	public Bill(Image img) {
		 setMinimumSize(new Dimension(670, 580));
			
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
		
		
		
		
		setMinimumSize(new Dimension(570, 550));
		setTitle("Bill");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 605);
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
		setContentPane(contentPane);
		textFieldBill = new JTextField();
		textFieldBill.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textFieldBill.setBackground(SystemColor.menu);
		JLabel label = new JLabel("Details:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblBill = new JLabel("Bill:");
		lblBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.WHITE);
		btnSave.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				String currentsystemdatetime =dtf.format(now);
				try
				{
					ResultSet temp;
					temp=st.executeQuery("select appointmentid from appointmenttable where pk_patientid="+getpatientid+" order by datetime desc");
					temp.next();
					int getappointmentid = temp.getInt(1);
					con.createStatement().execute("delete from billtable where fk_appointmentid="+getappointmentid+"");
					con.createStatement().execute("insert into billtable (datetime,fk_patientid,fk_appointmentid) values ('"+currentsystemdatetime+"',"+getpatientid+","+getappointmentid+")");
					
					int rowcount =model.getRowCount();
					rowcount--;
					con.createStatement().execute("delete from billstoragetable where fk_appointmentid="+getappointmentid+"");
					int count=-1;
					for(int i=0;i<=rowcount;i++)
					{
						count++;
						con.createStatement().execute("insert into billstoragetable (description,amt,qty,fk_appointmentid,fk_patientid) values ('"+model.getValueAt(count, 1).toString()+"','"+model.getValueAt(count, 2).toString()+"','"+model.getValueAt(count, 3).toString()+"',"+getappointmentid+","+getpatientid+") ");
					}
					JOptionPane.showMessageDialog(null,"Successfully saved!");
				}catch(Exception e){e.printStackTrace();}	
			}
		});
		btnSave.setBackground(new Color(139,189,65));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnClear = new JButton("Clear");
		btnClear.setForeground(Color.WHITE);
		btnClear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowcount = model.getRowCount();
				rowcount--;
				for(int i=rowcount;i>=0;i--)
				{
					model.removeRow(i);    //this is for removing the rows in the table model!!!
				}
				comboBox.setSelectedIndex(0);
				textField.setText("0");
			}
		});
		btnClear.setBackground(Color.RED);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setForeground(Color.WHITE);
		btnPrint.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnPrint.setBackground(new Color(242,171,42));
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnChargesTable = new JButton("View Charges Table");
		btnChargesTable.setForeground(Color.WHITE);
		btnChargesTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnChargesTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PriceSet.main(null);
			}
		});
		btnChargesTable.setBackground(new Color(242,171,42));
		btnChargesTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		textField = new JTextField();
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textField.setBackground(Color.WHITE);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setEditable(false);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox.setBackground(Color.WHITE);
		
		comboBox.removeAllItems(); 
		comboBox.addItem("--select--");
			
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					Class.forName(dbcon.DRIVER);
					con=DriverManager.getConnection(dbcon.JDBC_URL);
					st=con.createStatement();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				try
				{
					 ResultSet temp = st.executeQuery("select * from chargestable");
					 comboBox.removeAllItems();
					 comboBox.addItem("--select--");
					
					 while(temp.next())
					{
						comboBox.addItem(temp.getString(2));
					}
					
				}catch(Exception e) {}
				
			}
			@Override
			public void mouseExited(MouseEvent e) {

				try {
					
					Class.forName(dbcon.DRIVER);
					con=DriverManager.getConnection(dbcon.JDBC_URL);
					st=con.createStatement();
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				try
				{
					 ResultSet temp = st.executeQuery("select * from chargestable");
					 comboBox.removeAllItems();
					 comboBox.addItem("--select--");
					
					 while(temp.next())
					{
						comboBox.addItem(temp.getString(2));
					}
					
				}catch(Exception e2) {}
				
			
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		try {
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			 loadcombobox = st.executeQuery("select * from chargestable");
			 
			while(loadcombobox.next())
			{
				comboBox.addItem(loadcombobox.getString(2));
			}
			
		}catch(Exception e) {}
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int whatselected =comboBox.getSelectedIndex();
				String desc = (String) comboBox.getSelectedItem();
				if(whatselected==0)
				{
					JOptionPane.showMessageDialog(null,"please select something from the drop down list first!");
				}
				else
				{	
					try {
						 ResultSet temp = st.executeQuery("select * from chargestable");
						 int count = model.getRowCount();
						 count++;
						 String one ="1";
						 for(int i=0;i<whatselected;i++)
						 {
							 temp.next();
						 }
						 long amt = temp.getLong(3);
						 model.addRow(new Object[] {count++,desc,amt,one});
					}
					catch(Exception tmp) {tmp.printStackTrace();}
					
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
						
					}
					textField.setText(String.valueOf(sum));
					
			}
			});
		btnAdd.setBackground(new Color(139,189,65));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setForeground(Color.WHITE);
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemove.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int row = table.getSelectedRow();
				if(row==-1)
				{
					JOptionPane.showMessageDialog(null, "There is no row selected or either there is no data present!");
				}
				else
				{
				
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you u want to delete the selected item from the list?");
				
				if(a==0)
				{
				model.removeRow(row);
				int countrow = model.getRowCount();
				int counting =0;
				for(int i=0;i<countrow;i++)
				{                                       //this is for setting the counting variable.
					counting++;
					model.setValueAt(counting, i, 0);
				}
				
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
					
				}
				textField.setText(String.valueOf(sum));
				
			}
		}
				}
		);
		btnRemove.setBackground(Color.RED);
		
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
			
			ResultSet rs=st.executeQuery("select max (billid) from billtable");
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
			String b_id=String.valueOf(id);
			textFieldBill.setText(b_id);
		}
		catch(Exception ex)
		{
		}
		
		
		
		textFieldBill.setEditable(false);
		textFieldBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldBill.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
							.addComponent(lblBillId)
							.addGap(4)
							.addComponent(textFieldBill, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnChargesTable, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
							.addComponent(lblTotal)
							.addGap(18)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblBill, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(comboBox, 0, 465, Short.MAX_VALUE)
							.addGap(33)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(183)
							.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClear, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPrint, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
							.addGap(172))
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBillId)
						.addComponent(textFieldBill, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBill)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotal)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChargesTable, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClear)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
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
		table.setSelectionBackground(new Color(160,180,189));
		table.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
				
		table.setRowHeight(25);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane_1.setViewportView(table);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 17));
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
		contentPane.setLayout(gl_contentPane);
		
		
		//for checking and loading the patient information based upon the selection in the reception search info!
		
		
		boolean d=SearchInfoReception.rdbtnRegistrationId.isSelected();
		boolean e=SearchInfoReception.rdbtnAppointmentId.isSelected();
		boolean f=SearchInfoReception.rdbtnContactNo.isSelected();
		
		 if(d==true)
	      {  
	    	  try
	          {
	    		 String abc=SearchInfoReception.txtSearch.getText();
	    		  long no=Long.parseLong(abc);
	    	  ResultSet rs=st.executeQuery("Select * from patienttable where patientid="+no);
              if(rs.next())
              {
	    	  String n=rs.getString(2);
	    	  String g=rs.getString(3);
	    	  int ag=rs.getInt(5);
	    	  String ad=rs.getString(4);
	    	  String cont=rs.getString(6);
	    	  getpatientid=no;
	    	  textArea.setText("Patient id:= "+String.valueOf(no)+"\n"
	    		    		+ "Patient Name:= "+n+"\n"
   		    		+ "Gender:= "+g+"\n"
   		    		+ "Age:= "+String.valueOf(ag)+"\n"
   		    		+ "Contact No:= "+cont+"\n"
	    		    		+ "Address:= "+ad+"\n");
              }
	            }
	     
	            catch(Exception e1){}
	    	  }
		 
	      else if(f==true)
	      {
	    	  
	    	  try
	    	  {
	    		  String abc= SearchInfoReception.txtSearch.getText();
	    		  ResultSet rs1=st.executeQuery("Select * from patienttable where contact='"+abc+"'");
	    		  if(rs1.next())
	    		  {
	    		  long patient_id=rs1.getLong(1);
	    		  int age=rs1.getInt(5);
	    		  getpatientid=patient_id;
	    		  textArea.setText("Patient id:= "+String.valueOf(patient_id)+"\n"
	    		    		+ "Patient Name:= "+rs1.getString(2)+"\n"
   		    		+ "Gender:= "+rs1.getString(3)+"\n"
   		    		+ "Age:= "+String.valueOf(age)+"\n"
   		    		+ "Contact No:= "+rs1.getString(6)+"\n"
	    		    		+ "Address:= "+rs1.getString(4)+"\n");
	    		  }
	    	  }
	    	  catch(Exception e3) {}
	    	  }
	      else if(e==true)
	      {
	    	  try
	    	  {
	    		  String abc= SearchInfoReception.txtSearch.getText();
	    		  long appointment= Long.parseLong(abc);
	    		  ResultSet rs1=st.executeQuery("select pk_patientid from appointmenttable where appointmentid="+appointment);
	    		  ResultSet rs2=null;
	    		  if(rs1.next())
	    		  {
	    		  long patient_id=rs1.getInt(1);
	    		  try {
	    			  rs2= st.executeQuery("select * from patienttable where patientid="+patient_id);
	    		  }
	    		  catch(Exception e2) {}
	    		  if(rs2.next())
	    		  {
	    			  int age=rs2.getInt(5);
	    			  getpatientid=patient_id;
		    		  textArea.setText("Patient id:= "+String.valueOf(patient_id)+"\n"
		    		    		+ "Patient Name:= "+rs2.getString(2)+"\n"
	    		    		+ "Gender:= "+rs2.getString(3)+"\n"
	    		    		+ "Age:= "+String.valueOf(age)+"\n"
	    		    		+ "Contact No:= "+rs2.getString(6)+"\n"
		    		    		+ "Address:= "+rs2.getString(4)+"\n");
	    		  }
	    		 
	    		  }
	    		  
	    	  }
	    	  catch(Exception e3) {}
	    	  
	    	  }
		 }
	}

