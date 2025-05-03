package models;

public class prueba {
	
	private static int contador;
	private int id;
	private String nombre_prueba;
	private Tipo tipo;
	private String unidad_medida;
	private Modalidad modalidad;
	private String lugar;
	private String descripcion;
	
	public prueba(int id, String nombre_prueba, Tipo tipo, String unidad_medida, Modalidad modalidad, String lugar,
			String descripcion) {
		super();
		this.id = id;
		this.nombre_prueba = nombre_prueba;
		this.tipo = tipo;
		this.unidad_medida = unidad_medida;
		this.modalidad = modalidad;
		this.lugar = lugar;
		this.descripcion = descripcion;
	}

	public static int getContador() {
		return contador;
	}

	public static void setContador(int contador) {
		prueba.contador = contador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre_prueba() {
		return nombre_prueba;
	}

	public void setNombre_prueba(String nombre_prueba) {
		this.nombre_prueba = nombre_prueba;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	public Modalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}

enum Tipo{
	RESISTENCIA, FUERZA, VELOCIDAD, FLEXIBILIDAD, COORDINACION, EQUILIBRIO
}

enum Modalidad {
	INDIVIDUAL, GRUPO
}