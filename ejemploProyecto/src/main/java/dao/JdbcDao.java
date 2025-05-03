package dao;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcDao {
	private String url;
	private String user;
	private String password;

	public JdbcDao() throws Exception {
		Properties props = new Properties();
		InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
		props.load(input);

		url = props.getProperty("db.url");
		user = props.getProperty("db.user");
		password = props.getProperty("db.password");

	}

	public boolean validar(String usuario, String clave) {
		boolean resultado = false;

		Connection conn;
		try {
			conn = this.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT clave FROM usuarios WHERE usuario = ?");
			stmt.setString(1, usuario);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				resultado = clave.equals(rs.getString("clave"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	// Método para obtener la lista de usuarios
	public List<String> obtenerUsuarios() throws SQLException {

		List<String> usuarios = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection(); // Método para obtener la conexión
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT usuario FROM usuarios");

			while (rs.next()) {
				usuarios.add(rs.getString("usuario")); // Agrega cada usuario a la lista
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return usuarios; // Devuelve la lista de usuarios
	}

	// Método para establecer la conexión
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public String getClaveUser(String usuario) {
		
		String claveAngela = "";
		try {
		Connection conexion = this.getConnection();
		Statement st = conexion.createStatement();
		
		String query = "select clave from usuarios where usuario = '"+usuario+"'";
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			claveAngela = rs.getString("clave");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return claveAngela;
		
	}
	
}
