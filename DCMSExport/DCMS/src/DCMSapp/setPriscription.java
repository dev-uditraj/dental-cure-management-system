package DCMSapp;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;




public class setPriscription extends JFrame{
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
		             
		             img=img2.getScaledInstance(600, 700, Image.SCALE_DEFAULT); //this is for resizing the image
					setPriscription frame = new setPriscription(img);
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

	public void updatetable()
	{
		
		
		int rows =model.getRowCount();
		int count=0;
		if(rows==0)
		{
			JOptionPane.showMessageDialog(null,"There are no entries! Add Some data first!");
		}
		else
		{
		for(int i=0; i<rows;i++)
		{
			
			Object val=(Object)table.getModel().getValueAt(i, 2);
			Object val2=(Object)table.getModel().getValueAt(i, 1);
			Object val3=(Object)table.getModel().getValueAt(i, 3);
			
			if(val==null||val==" "||val2==null||val2==" "||val3==null||val3==" ")
			{
				count++;
			}
		}
		
		if(count!=0)
		{
		JOptionPane.showMessageDialog(null,"Unable to save as there are "+count+" column empty! please fill them and try again!");
		}
		else
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
			
			boolean a=SearchInfoDentistt.rdbtnRegistrationId.isSelected();
		      boolean b=SearchInfoDentistt.rdbtnAppointmentId.isSelected();
		      boolean c=SearchInfoDentistt.rdbtnContactNo.isSelected();
		      boolean d=SearchInfoDentistt.rdbtnCurrent.isSelected();
		      
		      if(a==true) //registration id 
		      {
		    	  try {			
			  			
			  			int row =model.getRowCount();
			  			String a1=null;
			  			int b1=0;
			  			int c1=0;
			  			
			  			ResultSet rs1=con.createStatement().executeQuery("select max(appointmentid) from appointmenttable where pk_patientid="+SearchInfoDentistt.no1+"");
			  			rs1.next();
			  			int appid=rs1.getInt(1);
			  			
			  			for(int i=0; i<row;i++)
			  			{
			  				a1=table.getModel().getValueAt(i, 1).toString();
			  				b1=Integer.parseInt(table.getModel().getValueAt(i, 2).toString());
			  				c1=Integer.parseInt(table.getModel().getValueAt(i, 3).toString());
			  				
			  				con.createStatement().execute("insert into prescriptionstoragetable (medicinename,frequency,quantity,fk_patientid,fk_appointmentid) values('"+a1+"',"+b1+","+c1+","+SearchInfoDentistt.no1+","+appid+")");
			  				
			  			}
			  			con.createStatement().execute("insert into prescriptiontable (fk_appointmentid,fk_patientid) values("+appid+","+SearchInfoDentistt.no1+")");
		  				JOptionPane.showMessageDialog(null,"Successfully saved!");
			  		}
			  		catch(Exception e)
			  		{e.printStackTrace();}
		      }
		      else if(b==true) //appointment id 
		      {
		    	  try {			
			  			
			  			int row =model.getRowCount();
			  			String a1=null;
			  			int b1=0;
			  			int c1=0;
			  			
			  			for(int i=0; i<row;i++)
			  			{
			  				a1=table.getModel().getValueAt(i, 1).toString();
			  				b1=Integer.parseInt(table.getModel().getValueAt(i, 2).toString());
			  				c1=Integer.parseInt(table.getModel().getValueAt(i, 3).toString());
			  				
			  				con.createStatement().execute("insert into prescriptionstoragetable (medicinename,frequency,quantity,fk_patientid,fk_appointmentid) values('"+a1+"',"+b1+","+c1+","+SearchInfoDentistt.patient_id+","+SearchInfoDentistt.appointmentid+")");
			  				
			  			}
			  			con.createStatement().execute("insert into prescriptiontable (fk_appointmentid,fk_patientid) values("+SearchInfoDentistt.appointmentid+","+SearchInfoDentistt.patient_id+")");
		  				JOptionPane.showMessageDialog(null,"Successfully saved!");
			  		}
			  		catch(Exception e)
			  		{e.printStackTrace();}
		      }
		      else if(c==true) //contact number
		      {
		    	  try {			
			  			
			  			int row =model.getRowCount();
			  			String a1=null;
			  			int b1=0;
			  			int c1=0;
			  			
			  			ResultSet rs1=con.createStatement().executeQuery("select max(appointmentid) from appointmenttable where pk_patientid="+SearchInfoDentistt.patient_id+"");
			  			rs1.next();
			  			int appid=rs1.getInt(1);
			  			
			  			for(int i=0; i<row;i++)
			  			{
			  				a1=table.getModel().getValueAt(i, 1).toString();
			  				b1=Integer.parseInt(table.getModel().getValueAt(i, 2).toString());
			  				c1=Integer.parseInt(table.getModel().getValueAt(i, 3).toString());
			  				con.createStatement().execute("insert into prescriptionstoragetable (medicinename,frequency,quantity,fk_patientid,fk_appointmentid) values('"+a1+"',"+b1+","+c1+","+SearchInfoDentistt.patient_id+","+appid+")");
			  			}
			  			con.createStatement().execute("insert into prescriptiontable (fk_appointmentid,fk_patientid) values("+appid+","+SearchInfoDentistt.patient_id+")");
		  				JOptionPane.showMessageDialog(null,"Successfully saved!");
			  		}
			  		catch(Exception e)
			  		{e.printStackTrace();}
		      }
		      else if(d==true) //current patient
		      {
		    	  try {			
		  			
		  			int row =model.getRowCount();
		  			String a1=null;
		  			int b1=0;
		  			int c1=0;
		  			
		  			ResultSet rs1=con.createStatement().executeQuery("select max(appointmentid) from appointmenttable where pk_patientid="+SearchInfoDentistt.noo+"");
		  			rs1.next();
		  			int appid=rs1.getInt(1);
		  			
		  			for(int i=0; i<row;i++)
		  			{
		  				a1=table.getModel().getValueAt(i, 1).toString();
		  				b1=Integer.parseInt(table.getModel().getValueAt(i, 2).toString());
		  				c1=Integer.parseInt(table.getModel().getValueAt(i, 3).toString());
		  				
		  				con.createStatement().execute("insert into prescriptionstoragetable (medicinename,frequency,quantity,fk_patientid,fk_appointmentid) values('"+a1+"',"+b1+","+c1+","+SearchInfoDentistt.no+","+appid+")");
		  				System.out.println("This worked!! withouto any exception");
		  			}
		  			con.createStatement().execute("insert into prescriptiontable (fk_appointmentid,fk_patientid) values("+appid+","+SearchInfoDentistt.no+")");
	  				JOptionPane.showMessageDialog(null,"Successfully saved!");
		  		}
		  		catch(Exception e)
		  		{e.printStackTrace();}
		      }
			
		
		
		}	
		}	
	}

	
	
	/**
	 * Create the frame.
	 */
	public setPriscription(String img) {
        this(new ImageIcon(img).getImage());
    }
	 
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial" })
	public setPriscription(Image img)  {
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


		//contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				updatetable();
				try {
					if(!st.isClosed())
					{
						st=null;
					}
				if(!con.isClosed())
				{
					con=null;
				}
				
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}});
		btnSave.setBackground(new Color(139,189,65));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.setForeground(Color.WHITE);
		btnAddNew.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAddNew.setBackground(new Color(139,189,65));
		
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sno= model.getRowCount();
				 sno++;
				model.addRow(new Object[] {sno});	//this is for adding a row in the table model!!
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnDelete.setForeground(Color.WHITE);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				int row = table.getSelectedRow();
				if(row==-1)
				{
					JOptionPane.showMessageDialog(null, "There is no row selected or either there is no data present!");
				}
				else
				{
				
					model.removeRow(row);
					int countrow = model.getRowCount();
					int counting =0;
					for(int i=0;i<countrow;i++)
					{                                       //this is for setting the counting variable.
						counting++;
						model.setValueAt(counting, i, 0);
					}
				
				}
			}
				
			}
		);
		btnDelete.setBackground(Color.RED);
		
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
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblPrescriptionId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddNew, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAddNew, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPrescriptionId)
							.addComponent(lblId)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
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
		table.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
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
	    setSize(600,700);
	    
	    
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
		
//		int count=0;
//		try {
//			
//			Class.forName(dbcon.DRIVER);
//			con=DriverManager.getConnection(dbcon.JDBC_URL);
//			st=con.createStatement();
//			
//			ResultSet getdata= st.executeQuery("select medicinename,frequency,quantity from prescriptiontable where ");
//			while(getdata.next())
//			{
//				count++;
//				String desc = getdata.getString(1);
//				Long amnt = getdata.getLong(2);
//				model.addRow(new Object[]{count,desc,amnt});
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		try {
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
			ResultSet rs=st.executeQuery("select max(prescriptionid) from prescriptiontable");
			
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
			lblId.setText(b_id);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
		
		
	}

class LimitedPlainDocument1 extends javax.swing.text.PlainDocument {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxLen = -1;
	  /** Creates a new instance of LimitedPlainDocument */     
	  public LimitedPlainDocument1() {}
	  public LimitedPlainDocument1(int maxLen) { this.maxLen = maxLen; }
	  public void insertString(int param, String str, 
	                           javax.swing.text.AttributeSet attributeSet) 
	                      throws javax.swing.text.BadLocationException {
	    if (str != null && maxLen > 0 && this.getLength() + str.length() > maxLen) {
	      java.awt.Toolkit.getDefaultToolkit().beep();
	      return;
	    }
	    super.insertString(param, str, attributeSet);
	  }
	}
