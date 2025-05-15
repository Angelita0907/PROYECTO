package dam.primero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dam.primero.modelos.Modalidad;
import dam.primero.modelos.Prueba;
import dam.primero.modelos.Tipo;

public class JdbcDaoPrueba extends JdbcDao {

    public JdbcDaoPrueba() throws Exception {
        super();
    }

    // Método para insertar una prueba
    public boolean insertaPrueba(Prueba prueba) {
        String query = "INSERT INTO pruebas (nombre_prueba, tipo, unidad_medida, modalidad, lugar, descripcion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, prueba.getNombre_prueba());
            pstmt.setString(2, prueba.getTipo().name());
            pstmt.setString(3, prueba.getUnidad_medida());
            pstmt.setString(4, prueba.getModalidad().name());
            pstmt.setString(5, prueba.getLugar());
            pstmt.setString(6, prueba.getDescripcion());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prueba.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizaPrueba(Prueba prueba) {
        String query = "UPDATE pruebas SET nombre_prueba = ?, tipo = ?, unidad_medida = ?, " +
                "modalidad = ?, lugar = ?, descripcion = ? WHERE id = ?";
        
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, prueba.getNombre_prueba());
            pstmt.setString(2, prueba.getTipo().name());
            pstmt.setString(3, prueba.getUnidad_medida());
            pstmt.setString(4, prueba.getModalidad().name());
            pstmt.setString(5, prueba.getLugar());
            pstmt.setString(6, prueba.getDescripcion());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prueba.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una prueba
    public boolean eliminaPrueba(int idPrueba) {
        String query = "DELETE FROM pruebas WHERE id = ?";
        
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, idPrueba);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todas las pruebas
    public List<String> consultaPruebas() {
        String query = "SELECT nombre_prueba FROM pruebas";
        List<String> pruebas = new ArrayList<>();
        
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // Procesar los resultados de la consulta
            while (rs.next()) {
                String nombrePrueba = rs.getString("nombre_prueba");
                pruebas.add(nombrePrueba); // Añadir cada prueba a la lista
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pruebas; // Retornar la lista con los resultados
    }

    // Método para obtener una prueba por ID
 // Método para obtener una prueba por nombre
    public Prueba getPruebaByNombre(String nombrePrueba) {
        String query = "SELECT * FROM pruebas WHERE nombre_prueba = ?";
        Prueba prueba = null;

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombrePrueba);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    prueba = new Prueba(
                        rs.getString("nombre_prueba"),
                        Tipo.valueOf(rs.getString("tipo")),
                        rs.getString("unidad_medida"),
                        Modalidad.valueOf(rs.getString("modalidad")),
                        rs.getString("lugar"),
                        rs.getString("descripcion")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prueba;
    }

}