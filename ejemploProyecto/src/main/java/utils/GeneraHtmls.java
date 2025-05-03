package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class GeneraHtmls {
	public void generaHtmlListaUsuarios(List<String> usuarios, HttpServletResponse response) {
		// Preparar la respuesta HTML
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("text/html");
			out.println("<!DOCTYPE html>");
			out.println("<html lang='es'>");
			out.println("<head><meta charset='UTF-8'><title>Lista de Usuarios</title></head>");
			out.println("<body>");
			out.println("<h1>Lista de Usuarios</h1>");
			out.println("<table border='1'>");
			out.println("<thead><tr><th>Usuario</th></tr></thead>");
			out.println("<tbody>");

			// Iterar sobre la lista de usuarios y crear filas en la tabla
			for (String usuario : usuarios) {
				out.println("<tr><td>" + usuario + "</td></tr>");
			}
			out.println("</tbody></table>");
			out.println("<a href='login.html'>Volver al Login</a>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
