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
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.border.MatteBorder;




public class PriceSet extends JFrame{
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
		             
		             img=img2.getScaledInstance(600, 401, Image.SCALE_DEFAULT); //this is for resizing the image
					PriceSet frame = new PriceSet(img);
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
			JOptionPane.showMessageDialog(null,"There are no entries! All the table data previosuly saved will be lost!");
		}
		for(int i=0; i<rows;i++)
		{
			Object val=(Object)table.getModel().getValueAt(i, 2);
			Object val2=(Object)table.getModel().getValueAt(i, 1);
			if(val==null||val==" "||val2==null||val2==" ")
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
			
		try {
			con.createStatement().execute("delete from chargestable");
			con.createStatement().execute("alter table chargestable alter column chargeid restart with 1");
			
			int row =model.getRowCount();
			float a;
			String b=null;
			for(int i=0; i<row;i++)
			{
				a=Float.parseFloat((table.getModel().getValueAt(i, 2).toString()));
				b=table.getModel().getValueAt(i, 1).toString();
				con.createStatement().execute("insert into chargestable (description,amount) values('"+b+"',"+a+")");
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		JOptionPane.showMessageDialog(null,"Successfully saved!");
		}	
		
	}

	
	
	/**
	 * Create the frame.
	 */
	public PriceSet(String img) {
        this(new ImageIcon(img).getImage());
    }
		/**
		 * @wbp.parser.constructor
		 */
	
	@SuppressWarnings({ "static-access", "serial" })
	public PriceSet(Image img)  {
        setMinimumSize(new Dimension(600, 500));
		
		this.img = img;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				
				if(st!=null)
				{
					st=null;
				}
				if(con!=null)
				{
					con=null;
				}
				try {
					DriverManager.getConnection(dbcon.JDBC_URLS);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					
				}
			}
		});
		
		setBackground(Color.WHITE);
		setTitle("Price and Content Set");
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
		
		JLabel lblSetContentAnd = new JLabel("Set Content and their Respective Price");
		lblSetContentAnd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnSave = new JButton("Save");
		btnSave.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(st!=null)
				{
					st=null;
				}
				if(con!=null)
				{
					con=null;
				}
				try {
					DriverManager.getConnection(dbcon.JDBC_URLS);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					
				}
				
				updatetable();
				
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
				
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you u want to **PERMANENTLY DELETE** the selected item from the list?");
				
				if(a==0)
				{
				model.removeRow(row);
				int countrow = model.getRowCount();
				int counting =0;
				for(int i=0;i<countrow;i++)
				{
					counting++;
					model.setValueAt(counting, i, 0);
				}
				updatetable();
				
				}
				}
			}
				
			}
		);
		btnDelete.setBackground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblSetContentAnd)
					.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddNew, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(246, Short.MAX_VALUE)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addGap(236))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSetContentAnd)
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAddNew, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
					.addGap(13)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
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
                    false,true,true
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
		model = new DefaultTableModel(new String[]{"Sno.", "Description", "Price(RUP)"}, 0);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setPreferredWidth(5000);
		table.getColumnModel().getColumn(2).setPreferredWidth(1000);
		
		
		
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
	    setSize(607,500);
	    
	    
		JTextField amt = new JTextField();
		amt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char keychar=ke.getKeyChar();
				if(!Character.isDigit(keychar))
				{
					ke.consume();
				}
			}
		});
		
	    
	    
	    amt.setDocument(new LimitedPlainDocument(7));
	    table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(amt));
	    setSize(300,300);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		int count=0;
		try {
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
			
			ResultSet getdata= st.executeQuery("select description,amount from chargestable");
			while(getdata.next())
			{
				count++;
				String desc = getdata.getString(1);
				Long amnt = getdata.getLong(2);
				model.addRow(new Object[]{count,desc,amnt});
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}

	

}
class LimitedPlainDocument extends javax.swing.text.PlainDocument {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxLen = -1;
	  /** Creates a new instance of LimitedPlainDocument */     
	  public LimitedPlainDocument() {}
	  public LimitedPlainDocument(int maxLen) { this.maxLen = maxLen; }
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
