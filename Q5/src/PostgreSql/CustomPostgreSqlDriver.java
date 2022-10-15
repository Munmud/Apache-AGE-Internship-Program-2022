package PostgreSql;

import java.sql.*;
import java.util.*;

public class CustomPostgreSqlDriver {

	public static Connection connect_to_db(String dbname, String user, String password) {
		Connection connection = null;
		String connectionURL = "jdbc:postgresql://localhost:5432/" + dbname;
		try {

			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(connectionURL, user, password);
			System.out.println("Postgresql Database Connected");

		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	public static void CreateTableIfNotExist(Connection connection) {
		// create table
		String query = "CREATE TABLE IF NOT EXISTS public.user_table" + "( " + "user_id numeric(10,0) NOT NULL, "
				+ "name character varying(50) COLLATE pg_catalog.\"default\" NOT NULL, " + "age numeric(3,0) NOT NULL, "
				+ "phone character varying(20) COLLATE pg_catalog.\"default\", "
				+ "CONSTRAINT user_table_pkey PRIMARY KEY (user_id) " + ");";
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Table Created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void Insert(Connection connection, String query) {
//		String InsertQuery = "INSERT INTO public.user_table (user_id, name, age, phone) VALUES (3, 'Jenny', 34, NULL);" ;
//		String InsertQuery = "INSERT INTO public.user_table (user_id, name, age, phone) VALUES (2, 'Tom', 29, '1-800-123-1234'); " ;
//		String InsertQuery = "INSERT INTO public.user_table (user_id, name, age, phone) VALUES (1, 'John', 28, NULL);" ;
		try {
			PreparedStatement statement = connection.prepareStatement(query) ;
			int rows = statement.executeUpdate() ;
			if (rows>0) {
				System.out.println("Values Inserted");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	
	public static void Read(Connection connection, String query) {
		Serializer serializer = new Serializer() ;
		
		String tmp = "\t[" ;

		try {
			PreparedStatement statement = connection.prepareStatement(query) ;
			ResultSet res = statement.executeQuery() ;
			
			

			
			while(res.next()) {
				tmp += "\n\t{\n\t\t" ;
				Map<String, String> mp = new HashMap<String, String>() ;
				mp.put("user_id", res.getString("user_id")) ;
				mp.put("name", res.getString("name")) ;
				mp.put("age", res.getString("age")) ;
				mp.put("phone", res.getString("phone")) ;
				serializer.data.add(mp) ;
				
				tmp+="user_id : " ;
				tmp+= res.getString("user_id") ;
				tmp+=",\n\t\t" ;
				
				tmp+="name : \"" ;
				tmp+= res.getString("name") ;
				tmp+="\",\n\t\t" ;
				
				tmp+="age : \"" ;
				tmp+= res.getString("age") ;
				tmp+="\",\n\t\t" ;
				
				tmp+="phone : \"" ;
				tmp+= res.getString("phone") ;
				tmp+="\"\t" ;
				
				tmp += "\n\t}," ;
			}
			serializer.status_code = 200 ;
			
			
		}
		catch(Exception e) {
			serializer.status_code = 500 ;
			System.out.println(e);
		}
		
		tmp+= "\n\t]";
		
		System.out.println("{\n\t status_code : " + serializer.status_code + ",\n\t data :" + tmp + "\n}");
	}
	
	public static void main(String[] args) {

		try {
			String dbname = "public.user_table";
			String userName = "postgres" ;
			String password = "253";
			
			// Establish Connection
			Connection connection = connect_to_db(dbname, userName, password);
			
			// Create Table
//			CreateTableIfNotExist(connection) ;

			// Insert			
//			String InsertQuery = "INSERT INTO public.user_table (user_id, name, age, phone) VALUES (1, 'John', 28, NULL);" ;
//			Insert(connection,InsertQuery) ;
			
			// View
			String ReadQuery = "SELECT * FROM " + dbname ;
			Read(connection, ReadQuery) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
