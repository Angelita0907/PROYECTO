package repository;

import dao.JdbcDaoPrueba;
import models.Prueba;
  
public class RepositorioPrueba { 
	public void altaEquipo(Prueba e) throws Exception {
		   JdbcDaoPrueba p = new JdbcDaoPrueba();
		   p.insertaPrueba(e);
	}
	/*
	public void eliminaEquipo(Prueba e) throws Exception {
			JdbcDaoPrueba p = new JdbcDaoPrueba();
		   p.eliminaPrueba(e);
	}
	public void consultaEquipo() throws Exception {
			JdbcDaoPrueba p = new JdbcDaoPrueba();
		   System.out.println(p.consultaPrueba());
	}
	public void actualizaDatos(Prueba e) throws Exception {
			JdbcDaoPrueba p = new JdbcDaoPrueba();
		   p.actualizaDatos(e);
	}*/
}
 
