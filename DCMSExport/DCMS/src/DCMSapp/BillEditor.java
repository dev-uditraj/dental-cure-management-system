package DCMSapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


import javax.swing.JOptionPane;

public class BillEditor {
	static Connection con=null;
	static Statement st=null;
	static long getpatientid=0;
	
	
	
	public static void main(String[] args) {
		
		try {
			
			Class.forName(dbcon.DRIVER);
			con=DriverManager.getConnection(dbcon.JDBC_URL);
			st=con.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		boolean d=SearchInfoReception.rdbtnRegistrationId.isSelected();
		boolean e=SearchInfoReception.rdbtnAppointmentId.isSelected();
		boolean f=SearchInfoReception.rdbtnContactNo.isSelected();
		
		 if(d==true)
	      {  
	    	  try
	          {
	    		 String abc=SearchInfoReception.txtSearch.getText();
	    		  long no=Long.parseLong(abc);
	    	  getpatientid=no;
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
	    		  getpatientid=patient_id;
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
	    			  getpatientid=patient_id;
	    		  }
	    		  }
	    	  }
	    	  catch(Exception e3) {}
	    	  
	    	  }
		
		try {
			ResultSet temp;
			temp=st.executeQuery("select appointmentid from appointmenttable where pk_patientid="+getpatientid+" order by datetime desc");
			temp.next();
			int getappointmentid = temp.getInt(1);
			ResultSet temp1= con.createStatement().executeQuery("select fk_patientid from billtable where fk_appointmentid="+getappointmentid+"");
			if(temp1.next())
			{
				int userchoice=JOptionPane.showConfirmDialog(null, "There is already a bill generated for the latest appointment of the patient!. Do you want to *OVERRIDE* the bill with a new bill insted??");
				if(userchoice==0)
				{
					Bill.main(null);
				}
			}
			else
			{
					Bill.main(null);
			}
		}catch(Exception a) {}
	}

}
