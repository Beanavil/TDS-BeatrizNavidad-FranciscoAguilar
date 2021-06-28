package es.um.tds.modelo;

/**
 * Representa un estilo musical.
 * 
 * @author Beatriz y Francisco
 */
public enum Estilo {
	ROCK("ROCK"), POP("POP"), POPPUNK("POP PUNK"), POPROCK("POP ROCK"), THEME("THEME"), KPOP("KPOP"), 
	ELECTROPOP("ELECTROPOP"), ELECTROHOUSE("ELECTROHOUSE"), ROCKJAPONES("ROCK JAPONES"), OPERA("OPERA"),
	ROMANTICA("ROMANTICA"), JAZZ("JAZZ"), FLAMENCO("FLAMENCO"), FOLK("FOLK"), CABARET("CABARET"), 
	ROCKSINFONICO("ROCK-SINFONICO"), TANGO("TANGO"), CLASICA("CLASICA"), CANTAUTOR("CANTAUTOR"), 
	BOLERO("BOLERO"), BLUES("BLUES"), GOSPEL("GOSPEL"), SOUL("SOUL"), METAL("METAL"), PUNK("PUNK"), 
	COUNTRY("COUNTRY"), HOUSE("HOUSE"), TECHNO("TECHNO"), REGGAETON("REGGAETON"), UNKNOWN("UNKNOWN");
	
	private String nombre;
	
	/**
	 * Constructor.
	 * @param nombre
	 */
	private Estilo (String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Getter del nombre.
	 * @return Nombre
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * Convierte string en un enum de Estilo, si no coincide con ninguno se le asigna "UNKNOWN".
	 * @param s String a convertir
	 * @return Estilo correspondiente o UNKNOWN
	 */
	public static Estilo valor(String s) {
		try {
			String capsOn = s.toUpperCase();
			Estilo estilo = Estilo.valueOf(capsOn);
			return estilo;
		} catch (IllegalArgumentException iae) {
			return Estilo.UNKNOWN;
		}
	}
}
