//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 

//


package gui.tasks.entities.clearing;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the zadaci.entities.clearingNalog package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ClearingNalog_QNAME = new QName("http://informatika.ftn.uns.ac.rs/xws/tim1/clearingNalog", "Clearing_nalog");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: zadaci.entities.clearingNalog
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClearingNalog }
     * 
     */
    public ClearingNalog createClearingNalog() {
        return new ClearingNalog();
    }

    /**
     * Create an instance of {@link StavkaClearingNaloga }
     * 
     */
    public StavkaClearingNaloga createStavkaClearingNaloga() {
        return new StavkaClearingNaloga();
    }

    /**
     * Create an instance of {@link ZaglavljeClearingNaloga }
     * 
     */
    public ZaglavljeClearingNaloga createZaglavljeClearingNaloga() {
        return new ZaglavljeClearingNaloga();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearingNalog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://informatika.ftn.uns.ac.rs/xws/tim1/clearingNalog", name = "Clearing_nalog")
    public JAXBElement<ClearingNalog> createClearingNalog(ClearingNalog value) {
        return new JAXBElement<ClearingNalog>(_ClearingNalog_QNAME, ClearingNalog.class, null, value);
    }

}
