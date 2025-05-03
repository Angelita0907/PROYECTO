package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JdbcDao;
import utils.GeneraHtmls;

public class ProyectoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo(); // Obtener el pathInfo

		// Si pathInfo es nulo, redirige a login.html
		if (pathInfo == null || pathInfo.equals("/")) {
			response.sendRedirect("login.html"); // Redirigir a login.html
		} else if (pathInfo.equals("/index")) {
			// Servir index.html si se accede a /index
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
		} else if (pathInfo.equals("/")) {
			// Servir login.html si se accede a /login
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
			dispatcher.forward(request, response);
		} else {
			// Si la subruta no se reconoce
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página no encontrada");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		validaUsuarioYClave(request, response);
	}

	void validaUsuarioYClave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");

		try {
			JdbcDao dao = new JdbcDao();
			if (dao.validar(usuario, clave)) {
				request.getSession().setAttribute("usuario", usuario);
				mostrarListaUsuarios(request, response);
			} else {
				response.sendRedirect("login.html?error=1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login.html?error=1");
		}
	}

	private void mostrarListaUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Aquí deberías implementar la lógica para obtener la lista de usuarios de la
		// base de datos

		try {
			JdbcDao dao = new JdbcDao();
			List<String> usuarios = dao.obtenerUsuarios();
			GeneraHtmls htmls = new GeneraHtmls();
			htmls.generaHtmlListaUsuarios(usuarios, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
