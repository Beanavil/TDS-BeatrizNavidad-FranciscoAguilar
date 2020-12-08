package modelo;

import java.util.Calendar;

import com.toedter.calendar.JCalendar;



public class Usuario implements RolUsuario, Descuento {
	private String nombre;
	private String apellido1;
	private String apellido2;
	private JCalendar fechaNacimiento;
	private String email;
	private String nombreUsuario;
	private String contrasena;
	private boolean premium;
	
	public Usuario (String nombre, String apellido1, String apellido2, int anyo, int mes, int dia, 
			String email, String nombreUsuario, String contrasena, boolean premium) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		Calendar fechaAux = Calendar.getInstance();
		fechaAux.set(anyo, mes, dia);
		this.fechaNacimiento = new JCalendar(fechaAux);
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.premium = premium;
	}

	// Getters
	public String getNombre() {
		return nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public JCalendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}
	
	public boolean isPremium() {
		return premium;
	}
	
	// Setters 
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public void setFechaNacimiento(int anyo, int mes, int dia) {
		Calendar fechaAux = Calendar.getInstance();
		fechaAux.set(anyo, mes, dia);
		this.fechaNacimiento = new JCalendar(fechaAux);
	}

	public void setEmail(String email) {
		// TODO: ver si aquí hay que comprobar también el formato del mail
		this.email = email;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Método para realizar un pago
	 */
	public void realizarPago() {
		
	}
	
	/**
	 * Método para calcular el descuento a aplicar en función 
	 * del tipo de cuenta.
	 * @param precio cantidad de la que calculamos el descuento
	 * @return descuento a aplicar
	 */
	public double calcDescuento(double precio) {
		return 0;
	}
	
}
