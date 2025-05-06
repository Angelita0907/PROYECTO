package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Prueba;

public class JdbcDaoPrueba extends JdbcDao{

	public JdbcDaoPrueba() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void insertaPrueba(Prueba prueba) {
	    String query = "INSERT INTO Pruebas (nombre_prueba, tipo, unidad_medida, modalidad, lugar, descripcion) " +
	                   "VALUES ('prueba', 'fuerza', 'distancia', 'individual', 'patio', 'prueba')";
	    
	    try (Connection conn = this.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        
	        pstmt.setString(1, prueba.getNombre_prueba());
	        pstmt.setString(2, prueba.getTipo().toString().toLowerCase());
	        pstmt.setString(3, prueba.getUnidad_medida());
	        pstmt.setString(4, prueba.getModalidad().toString().toLowerCase());
	        pstmt.setString(5, prueba.getLugar() != null ? prueba.getLugar() : "");
	        pstmt.setString(6, prueba.getDescripcion() != null ? prueba.getDescripcion() : "");
	        
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void eliminaPrueba(Prueba e1) {
		String query = "delete from prueba where id_prueba = 20";
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection(); // Método para obtener la conexión
			stmt = conn.createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
	
	public List<String> consultaPrueba() {
		String query = "select nombre_prueba from pruebas";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> equipos = new ArrayList<>();

		try {
			conn = this.getConnection(); // Método para obtener la conexión
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return equipos;
	}
	
	public void actualizaDatos(Prueba e1)  {
		String query = "update pruebas set nombre_prueba = \"Deportivo\" where id_prueba = 15;";
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection(); // Método para obtener la conexión
			stmt = conn.createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}

}
  
