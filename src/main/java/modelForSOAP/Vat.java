//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.10 at 09:53:45 PM CET 
//


package modelForSOAP;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vat.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="vat">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VAT_0"/>
 *     &lt;enumeration value="VAT_5"/>
 *     &lt;enumeration value="VAT_8"/>
 *     &lt;enumeration value="VAT_23"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "vat")
@XmlEnum
public enum Vat {

  VAT_0,
  VAT_5,
  VAT_8,
  VAT_23;

  public String value() {
    return name();
  }

  public static Vat fromValue(String v) {
    return valueOf(v);
  }

}
