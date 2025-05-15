package dam.primero.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.swing.plaf.multi.MultiPopupMenuUI;

import java.io.IOException;
import java.util.*;

import dam.primero.dao.JdbcDaoPrueba;
import dam.primero.dao.UsuarioDao;
import dam.primero.modelos.Modalidad;
import dam.primero.modelos.Prueba;
import dam.primero.modelos.Tipo;
import dam.primero.modelos.Usuario;

public class MiServlet extends HttpServlet {

	private static final long serialVersionUID = 2051990309999713971L;
	private TemplateEngine templateEngine;
	private JdbcDaoPrueba prueba;

	@Override
	public void init() throws ServletException {
		System.out.println("En init");
		ServletContext servletContext = getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("En get");

		ServletContext servletContext = getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		IServletWebExchange webExchange = application.buildExchange(request, response);
		WebContext context = new WebContext(webExchange, request.getLocale());
		response.setContentType("text/html;charset=UTF-8");
		// Datos de ejemplo: lista de nombres

		String pathInfo = request.getServletPath();// Ejemplo: /listarUsuarios o null
		
		System.out.println("En get:"+pathInfo);
		
		if (pathInfo == null || pathInfo.trim().isEmpty() || pathInfo.trim().equalsIgnoreCase("/principal")) {
			// Redirigir a la página de login
			templateEngine.process("principal", context, response.getWriter());
		} else {
			// Dividimos por segmentos
		    String[] partes = pathInfo.substring(1).split("/");
		    String accion = partes[0]; // ej: "detalleUsuario"
		    String parametro1 = partes.length > 1 ? partes[1] : null;
		    
		    System.out.println("Servlet invocado. accion: " + accion);

		    switch (accion) {
		    
		    case "pruebas":
                // Cargar datos necesarios para pruebas.html
		    	//List<Prueba> pruebas = daoPrueba.consultaPruebas();
                context.setVariable("pruebas", parametro1);
                templateEngine.process("pruebas", context, response.getWriter());
                break;
		    case "aniadir":
		        // Asignar listas de enums como texto para el formulario
		        List<String> tipos = Arrays.asList("RESISTENCIA", "FUERZA", "VELOCIDAD", "FLEXIBILIDAD");
		        context.setVariable("tipos", tipos);

		        List<String> modalidades = Arrays.asList("GRUPO", "INDIVIDUAL");
		        context.setVariable("modalidades", modalidades);

		        // Renderiza el formulario
		        templateEngine.process("aniadir", context, response.getWriter());
		        break;
                
		    case "busqueda":
		        // Obtener lista de nombres de pruebas
		        List<String> pruebasBusqueda = this.getListaPruebas(request, response, context);
		        context.setVariable("pruebas", pruebasBusqueda);
		        templateEngine.process("busqueda", context, response.getWriter());
		        break;
                
		    case "modificar":
		        String nombrePrueba = request.getParameter("prueba"); // se llama "prueba" en el select
		        Prueba prueba = obtenerPruebaPorId(nombrePrueba);
		        
		        if (prueba != null) {
		            context.setVariable("prueba", prueba);

		            // Listas de enums para los selects
		            List<String> tipos2 = Arrays.asList("RESISTENCIA", "FUERZA", "VELOCIDAD", "FLEXIBILIDAD");
		            List<String> modalidades2 = Arrays.asList("GRUPO", "INDIVIDUAL");
		            context.setVariable("tipos", tipos2);
		            context.setVariable("modalidades", modalidades2);

		            templateEngine.process("modificar", context, response.getWriter());
		        } else {
		            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prueba no encontrada");
		        }
		        break;
	            
			case "listarUsuarios":
				List<Usuario> usuarios = this.getListaUsuarios(request, response, context);
				// Guardamos los usuarios para que lo tenga el frontend
				context.setVariable("usuarios", usuarios);
				// Redirigimos a listaUsuarios.html
				templateEngine.process("listaUsuarios", context, response.getWriter());
				break;
				
			case "detalleUsuario":
		        Usuario usuario = this.getDetalleUsuario(parametro1);
		        context.setVariable("usuario", usuario);
		        templateEngine.process("detalleUsuario", context, response.getWriter());
		        break;
		        
			case "index":
				templateEngine.process("index", context, response.getWriter());
				break;
				
			default:
				// Ruta no reconocida
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no válida: " + pathInfo);
			}
		}
	}

	boolean validaUsuarioYClave(HttpServletRequest request, HttpServletResponse response, WebContext context)
			throws ServletException, IOException {

		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");
		boolean correcto = false;

		try {
			UsuarioDao dao = new UsuarioDao();
			correcto = dao.validar(usuario, clave);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return correcto;
	}

	private List<Usuario> getListaUsuarios(HttpServletRequest request, HttpServletResponse response, WebContext context)
			throws ServletException, IOException {
		// Aquí deberías implementar la lógica para obtener la lista de usuarios de la
		// base de datos
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			UsuarioDao dao = new UsuarioDao();
			usuarios = dao.obtenerUsuarios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuarios;
	}
	
	private List<String> getTiposPruebas(HttpServletRequest request, HttpServletResponse response, WebContext context)
	        throws ServletException, IOException {
	    List<String> tipos = new ArrayList<>();
	    try {
	        tipos.add("resistencia");
	        tipos.add("fuerza");
	        tipos.add("velocidad");
	        tipos.add("flexibilidad");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tipos;
	}

	private List<String> getListaModalidades(HttpServletRequest request, HttpServletResponse response, WebContext context)
	        throws ServletException, IOException {
	    List<String> modalidades = new ArrayList<>();
	    try {
	        modalidades.add("grupo");
	        modalidades.add("individual");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return modalidades;
	}

	private List<String> getListaPruebas(HttpServletRequest request, HttpServletResponse response, WebContext context)
            throws ServletException, IOException {
        List<String> pruebas = new ArrayList<>();
        try {
            JdbcDaoPrueba daoPrueba = new JdbcDaoPrueba();
            pruebas = daoPrueba.consultaPruebas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pruebas;
    }

    private Prueba obtenerPruebaPorId(String nombre) {
        Prueba prueba = null;
        try {
            JdbcDaoPrueba daoPrueba = new JdbcDaoPrueba();
            prueba = daoPrueba.getPruebaByNombre(nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prueba;
    }
	
	private Usuario getDetalleUsuario(String nombre)
	{
		Usuario u= null;
		try {
			UsuarioDao dao = new UsuarioDao();
			u = dao.getDetalleUsuario(nombre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		String servletPath = request.getServletPath();// Ejemplo: /listarUsuarios o null
		System.out.println(servletPath);
		ServletContext servletContext = getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		IServletWebExchange webExchange = application.buildExchange(request, response);
		WebContext context = new WebContext(webExchange, request.getLocale());

		switch (servletPath) {
		
		case "/altaPrueba":
	        // Cargar tipos y modalidades para los selects
	     /*   context.setVariable("tipos", Arrays.asList("resistencia", "fuerza", "velocidad", "flexibilidad"));
	        context.setVariable("modalidades", Arrays.asList("grupo", "individual"));*/
	        templateEngine.process("aniadir", context, response.getWriter());
	        
	        try {
	        	JdbcDaoPrueba dao = new JdbcDaoPrueba();
	        	
	    	    String nombre = request.getParameter("nombre");
			    String tipo = request.getParameter("tipo");
			    String unidad = request.getParameter("unidad");
			    String modalidad = request.getParameter("modalidad");
			    String lugar = request.getParameter("lugar");
			    String descripcion = request.getParameter("descripcion");
			   
			    Tipo tipoEnum = Tipo.valueOf(tipo);
			    Modalidad modalidadEnum = Modalidad.valueOf(modalidad);
			    
			    Prueba prueba = new Prueba(nombre,  tipoEnum, unidad,  modalidadEnum,  lugar,descripcion);
				dao.insertaPrueba(prueba);
				
				response.sendRedirect("/aniadir");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        break;
		
		case "/modificar":
		    String nombre = request.getParameter("nombre");
		    String tipo = request.getParameter("tipo");
		    String unidad = request.getParameter("unidad");
		    String modalidad = request.getParameter("modalidad");
		    String lugar = request.getParameter("lugar");
		    String descripcion = request.getParameter("descripcion");

		    Prueba pruebaModificada = new Prueba(
		        nombre,
		        Tipo.valueOf(tipo.toUpperCase()),
		        unidad,
		        Modalidad.valueOf(modalidad.toUpperCase()),
		        lugar,
		        descripcion
		    );

		    try {
		        JdbcDaoPrueba dao = new JdbcDaoPrueba();
		        dao.actualizaPrueba(pruebaModificada);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    response.sendRedirect("busqueda");
		    break;

	        
		case "/validaUsuario":
			// Lógica para listar usuarios
			boolean correcto = validaUsuarioYClave(request, response, context);
			if (correcto) {
				context.setVariable("error", false);
				templateEngine.process("index", context, response.getWriter());
			} else {
				context.setVariable("error", true);
				templateEngine.process("login", context, response.getWriter());

			}
			break;
		default:
			// Ruta no reconocida
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no válida: " + servletPath);
		}
	}
}
