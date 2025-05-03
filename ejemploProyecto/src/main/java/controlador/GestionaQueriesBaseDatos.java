package controlador;

import dao.JdbcDao;

public class GestionaQueriesBaseDatos {

	public static void main(String[] args) {
		
			JdbcDao dao;
			try {
				dao = new JdbcDao();
				System.out.println(dao.getClaveUser("angela1"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

}
