package es.um.tds.utils;

public class StringUtils {
	/**
	 * Método auxiliar para ver si un string contiene a otro como subcadena sin tener en 
	 * cuenta las mayúsculas.
	 * @param str String en el que buscamos la subcadena
	 * @param subStr Posible subcadena de str
	 * @return Si subStr está contenido en str, case insensitive
	 */
	public static boolean containsIgnoreCase(String str, String subStr) {
        return str.toLowerCase().contains(subStr.toLowerCase());
    }
}
