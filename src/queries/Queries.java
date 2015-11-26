package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries
{
	private static String dbURI = "jdbc:mysql://localhost:";
	private static String dbPORT = "3306";
	private static String dbNAME = "/odyssey";
	private static String dbOPS = "?allowMultiQueries=true";
	private static String dbUSER = "odysseus";
	private static String dbPASS = "penelope";
	
	public static boolean printAll()
	{
		boolean retVal = false;
		PreparedStatement pst = null;
        ResultSet rs = null;
		String dbURL = dbURI + dbPORT + dbNAME + dbOPS;
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
        	retVal = false;
        	System.out.println("Cannot find the driver in the classpath!");
            //throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
		
		try(Connection con = DriverManager.getConnection(dbURL, dbUSER, dbPASS))
		{
			String Query = "select * from `odyssey`.`tbl_ex`";
			pst = con.prepareStatement(Query);
			pst.execute();
			rs = pst.getResultSet();
			while(rs.next())
			{
				System.out.print("id: ");
				System.out.println(rs.getString(1));
				System.out.print("name: ");
				System.out.println(rs.getString(2));
			}
			retVal = true;
		}
		catch (SQLException ex)
		{
			System.out.println("SQLEXception");
			retVal = false;
		}
		finally
        {
        	try
            {
                if (rs != null)
                	rs.close();
                if (pst != null)
                	pst.close();
            }
        	catch (SQLException ex)
            {
        		//this here is unlikely to happen
            	System.out.println("SQLException_finally");
            }
        }
		
		return retVal;
	}
	public static void main(String[] args)
	{
		if(printAll())
		{
			System.out.println("it worked!");
		}
		else
		{
			System.out.println("Merde!");
		}
	}
}
