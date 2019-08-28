package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRM {
	
	private String requete;
	private Connection conn;
	private ResultSet rs;
	private String db_connect_string = "jdbc:sqlserver://seyccrmsqlsip1;databaseName=crm_MSCRM;integratedSecurity=true;";
	
	public void dbConnect()
	   {
	      try {
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	         conn = DriverManager.getConnection(db_connect_string);
	         

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	public ResultSet getRS() {
		return rs;
	}
	
	public void readCRM () {
		try {

	        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
	        requete();
	        rs = statement.executeQuery(requete);
	       
        } catch (Exception e) {
	         e.printStackTrace();
	      }
	
	}
	
	public void requete() {
		
		requete = "SELECT c.ticketnumber,               " + //\"Case #\",\r\n" +
				"		c.customeridname,               " + //\"Account Name\",\r\n" + 
				"       c.sfmig_ranking,                " + // \"Priority Score\",\r\n" +
				"       c.aldata_functionaldomainname, 	" + //\"Domain\",\r\n" + 
				"       c.statuscodename,               " + //\"Status\",\r\n" + 
				"       cq.aldata_clearquestbugnumber,  " + //\"CQ Card\",\r\n" + 
				"       c.productidname,                " + //\"Module\",\r\n" + 
				"       c.prioritycodename,		        " + //\"Priority\",\r\n" +
				"       c.sfmig_expecteddeliverydate,   " + //"Expected Delivery\",\r\n" + 
				"       c.owneridname,                  " + //\"Owner\",\r\n" + 
				"       c.aldata_assignedgroupname,     " + //\"Assigned Group\",\r\n" + 
				"       c.aldata_validatetargetpatch,   " + //\"Validate Target Patch\",\r\n" + 
				"       c.title,                        " + //\"Title\",\r\n" + 
				"       c.aldata_workstreamname,        " + //\"Workstream\",\r\n" + 
				"       c.createdonutc,                 " + //\"Created On (UTC)\",\r\n" + 
				"       c.sfmig_truelastmodifieddateutc," + //\"Last Modified (UTC)\",\r\n" + readcquest
				
				"       c.sfmig_srclientnumber,         " + //\"Customer's Case #\",\r\n" + 
				"       c.sfmig_prodenvtname,           " + //\"Production?\",\r\n" + 
				"       cq.aldata_assignedto,           " + //"CQ Assigned To\",\r\n" + 
				"       cq.aldata_label,                " + //\"CQ Label\",\r\n" + 
				"       cq.aldata_state,                " + //\"CQ State\",\r\n" + 
				"       c.aldata_assignedpersonname,    " + //\"Assigned Person\",\r\n" + 
				"       c.sfmig_rejectedsolutionscount, " + //\"# Rejection\",\r\n" + 
				"       c.aldata_expectedcorrectiondate," + //\"date_correction\",\r\n" + 
				"       c.sfmig_targetpatch,            " + //\"Target Patch\",\r\n" + 
				"		c.incidentid,			 		" + //\"IncID\",\r\n" + 
				"		c.aldata_versionidname, 		" + //\"Version\",\r\n" + 
				"		c.sfmig_srclientnumber          " + //\"Customer's Case #\"\r\n" + 
				"  FROM FilteredIncident c WITH (NOLOCK)\r\n" + 
				"  LEFT OUTER JOIN Filteredaldata_clearquestbug cq WITH (NOLOCK) ON c.incidentid = cq.aldata_case\r\n" + 
				" WHERE 1 = 1\r\n" + 
				"   and c.statuscode not in (5, 6, 129770007) /*5=closed;6=cancelled,129770007=livre*/\r\n" + 
				//"   and c.ticketnumber = 'CAS-126456-Y8J4'\r\n" +
				"   and c.aldata_assignedgroupname in\r\n" + 
				"       ('SupRetMaint',\r\n" + 
				"        'SupRetMaint2',\r\n" + 
				"        'SupRetMaint1',\r\n" + 
				"        'SupMaintMDMPIM',\r\n" + 
				"        'MDM','SupMaintTopase',\r\n" + 
				"        'Performance team')     order by 1";
		

	}
	
	public void connectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error CRM.connectionClose : "+e.toString());
			e.printStackTrace();
		}
	}
	
}
