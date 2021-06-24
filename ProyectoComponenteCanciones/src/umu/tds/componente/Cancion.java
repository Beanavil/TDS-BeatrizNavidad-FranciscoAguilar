package umu.tds.componente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Cancion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Cancion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interprete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="titulo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cancion", propOrder = {
    "url",
    "estilo",
    "interprete"
})
public class Cancion {

    @XmlElement(name = "URL", required = true)
    protected String url;
    protected String estilo;
    protected String interprete;
    @XmlAttribute(name = "titulo", required = true)
    protected String titulo;

    /**
     * Obtiene el valor de la propiedad url.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Define el valor de la propiedad url.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Obtiene el valor de la propiedad estilo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstilo() {
        return estilo;
    }

    /**
     * Define el valor de la propiedad estilo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstilo(String value) {
        this.estilo = value;
    }

    /**
     * Obtiene el valor de la propiedad interprete.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterprete() {
        return interprete;
    }

    /**
     * Define el valor de la propiedad interprete.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterprete(String value) {
        this.interprete = value;
    }

    /**
     * Obtiene el valor de la propiedad titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el valor de la propiedad titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

}
