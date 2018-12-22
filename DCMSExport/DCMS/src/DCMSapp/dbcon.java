package DCMSapp;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.derby.drda.NetworkServerControl;
import java.net.InetAddress;





public class dbcon {
	public static final String DRIVER= "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL="jdbc:derby://localhost:1527/dcmsdb;create=true";
	public static final String JDBC_URLS ="jdbc:derby://localhost:1527/dcmsdb;shutdown=true";
	
	static Connection con=null;
	static boolean hasTable=true;


	public static void main(String[] args) {
			
		try {
			
			NetworkServerControl server = new NetworkServerControl  //this is for starting the server
				    (InetAddress.getByName("localhost"),1527);
				server.start(null);
				
			Class.forName(DRIVER);
			con =DriverManager.getConnection(JDBC_URL);
			
					}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		hasTable=hasMemory();
		createTable();
	
	}
	
	
	
	public static boolean hasMemory()
	{
		boolean istrue=true;
		try
		{
		con.createStatement().executeQuery("select * from dentistlogintable");
	
		}
		catch(Exception e)
		{
			istrue=false;
		}
			return istrue;	
	}
	
	
	public static void createTable()
	{
		if(hasTable==false)
		{
			try {
				//Dentist login table
				con.createStatement().execute("create table dentistlogintable ("
						+ "userid int primary key GENERATED ALWAYS AS IDENTITY" + 
						" (START WITH 1000, INCREMENT BY 1),"
						+ "username varchar(50) unique,"
						+ "password varchar(30),"
						+ "name varchar(50),"
						+ "gender varchar(8),"
						+ "contact varchar(10),"
						+ "securityq varchar(150),"
						+ "securitya varchar(200))");
				
				//Reception login table
				con.createStatement().execute("create table receptionlogintable ("
						+ "userid int primary key GENERATED ALWAYS AS IDENTITY" + 
						" (START WITH 1, INCREMENT BY 1),"
						+ "username varchar(50) unique,"
						+ "password varchar(30),"
						+ "name varchar(50),"
						+ "gender varchar(8),"
						+ "contact varchar(10),"
						+ "securityq varchar(150),"
						+ "securitya varchar(200))");
				
				// code table
				con.createStatement().execute("create table code ("
						+ "code numeric primary key)");
				con.createStatement().execute("insert into code values(78308)");
				//Patient table 
				con.createStatement().execute("create table patienttable ("
						+ "patientid int primary key GENERATED ALWAYS AS IDENTITY" + 
						" (START WITH 1, INCREMENT BY 1),"
						+ "name varchar(50) ,"
						+ "gender varchar(10),"
						+ "address varchar(150),"
						+ "age numeric(3,0),"
						+ "contact varchar(10))");
				
				//Appointment table
				con.createStatement().execute("create table appointmenttable ("
						+ "appointmentid int primary key GENERATED ALWAYS AS IDENTITY" 
						+ " (START WITH 1, INCREMENT BY 1),"
						+ "datetime timestamp ,"
						+ "systemtime timestamp,"
						+ "isdeleted char default 'f',"
						+" pk_patientid int references patienttable(patientid))");
				
				//Prescription Table
				con.createStatement().execute("create table prescriptiontable ("
						+ "prescriptionid int primary key GENERATED ALWAYS AS IDENTITY"
						+ " (START WITH 1, INCREMENT BY 1),"
						+" fk_patientid int references patienttable(patientid),"
						+ "fk_appointmentid int references appointmenttable(appointmentid))");
				
				//PrescriptionStorage Table
				con.createStatement().execute("create table prescriptionstoragetable ("
						+ "medicinename varchar(50),"
						+ "frequency numeric(2,0),"
						+ "quantity numeric(2,0)," 
						+ "fk_appointmentid int references appointmenttable(appointmentid),"
						+ "fk_patientid int references patienttable(patientid))");
				
				//Charges Table
				con.createStatement().execute("create table chargestable ("
						+ "chargeid int primary key GENERATED ALWAYS AS IDENTITY"
						+ " (START WITH 1, INCREMENT BY 1),"
						+ "description varchar(200) default 'no description',"
						+ "amount decimal (7,2) default 0 ) ");
				
				//Bill Storage Table
				con.createStatement().execute("create table billstoragetable ("
						+ "description varchar(200) default 'no description',"
						+" amt varchar(20),"
						+" qty varchar(20),"
						+ "fk_appointmentid int references appointmenttable(appointmentid),"
						+ "fk_patientid int references patienttable(patientid))");
				
				//Bill table
				con.createStatement().execute("create table billtable ("
						+ "billid int primary key GENERATED ALWAYS AS IDENTITY"
						+ "(START WITH 1, INCREMENT BY 1),"
						+ "datetime timestamp,"
						+" fk_patientid int references patienttable(patientid),"
						+ "fk_appointmentid int references appointmenttable(appointmentid))");
				
				
			}
			catch(Exception e)
			{
				
				e.printStackTrace();
			}
			
			
			Login.main(null);
		}
			else
			{
				Login.main(null);
			}
	}

}
