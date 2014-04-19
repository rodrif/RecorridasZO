package com.recorridaszo.recorridaszo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends FragmentActivity {
	Connection conexionMySQL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

////////////////////////////////////
		String QSLEjecutar = "select * from facturas limit 10";
		ResultSet rs = null;

		
		/*  conectarBDMySQL("root", "contraseña", 
    			  "ajpdsoft.com", "3306", "bdfacturas");*/
		  
		try
		  {      	  
       	  
       	  conectarBDMySQL("56462_recozo", "Fyg123fyg123", 
       			  "fdb2.leadhoster.com", "3306", "56462_recozo");
       	  
     		  Statement st = conexionMySQL.createStatement();
      		  rs = st.executeQuery("select * from RecorridasZO limit 10"); 

       	  String resultadoSQL = "";
       	  Integer numColumnas = 0;
				  
       	  //número de columnas (campos) de la consula SQL            	  
       	  numColumnas = rs.getMetaData().getColumnCount();            	  

       	  //mostramos el resultado
       	  while (rs.next()) 
       	  {  
       		  for (int i = 1; i <= numColumnas; i++)
                 {                          
                     if (rs.getObject(i) != null)
                     {
                   	  if (resultadoSQL != "")
                   		  if (i < numColumnas)
                   			  resultadoSQL = resultadoSQL + rs.getObject(i).toString() + ";";
                   		  else
                   			  resultadoSQL = resultadoSQL + rs.getObject(i).toString();
                   	  else
                   		  if (i < numColumnas)
                   			  resultadoSQL = rs.getObject(i).toString() + ";";
                   		  else
                   			  resultadoSQL = rs.getObject(i).toString();
                     }
                     else
                     {
                   	  if (resultadoSQL != "")
                   		  resultadoSQL = resultadoSQL + "null;";
                   	  else
                   		  resultadoSQL = "null;";
                     }                           
                 }
                 resultadoSQL = resultadoSQL + "\n";
       	  }

   		  Log.d("RecorridasZO", resultadoSQL);
   		  st.close();
   		  rs.close();        		  
		  }
      
         catch (Exception e) 
         {  
       	  Toast.makeText(getApplicationContext(),
	                    "Error2: " + e.getMessage(),
	                    Toast.LENGTH_SHORT).show();
         }	
     
/////////////////////////////////////////////////////////////////		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void conectarBDMySQL (String usuario, String contrasena, 
    		String ip, String puerto, String catalogo)
    {
    	if (conexionMySQL == null)    	
    	{
    		String urlConexionMySQL = "";
    		if (catalogo != "")
    			urlConexionMySQL = "jdbc:mysql://" + ip + ":" +	puerto + "/" + catalogo;
    		else
    			urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
    		if (usuario != "" & contrasena != "" & ip != "" & puerto != "")
    		{
    			try 
    			{
					Class.forName("com.mysql.jdbc.Driver");
	    			conexionMySQL =	DriverManager.getConnection(urlConexionMySQL, 
	    					usuario, contrasena);					
				} 
    			catch (ClassNotFoundException e) 
    			{
    		      	  Toast.makeText(getApplicationContext(),
    		                    "Error: " + e.getMessage(),
    		                    Toast.LENGTH_SHORT).show();
    			} 
    			catch (SQLException e) 
    			{
			      	  Toast.makeText(getApplicationContext(),
			                    "Error: " + e.getMessage(),
			                    Toast.LENGTH_SHORT).show();
				}
    		}
    	}
    }	

}
