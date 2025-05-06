package controlador;

import dao.JdbcDao;
import dao.JdbcDaoPrueba;

public class GestionaQueriesBaseDatos {

	public static void main(String[] args) {
		
			try {
				JdbcDaoPrueba p = new JdbcDaoPrueba();
				p.insertaPrueba();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				JdbcDaoPrueba p = new JdbcDaoPrueba();
				p.consultaPrueba();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}


	}

}
