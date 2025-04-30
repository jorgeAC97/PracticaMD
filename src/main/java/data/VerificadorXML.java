package data;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

public class VerificadorXML
{
    private static final String XML= "Prueba.xml";
    private static final String XSD= "Prueba.xsd";

    private static final String MENSAJE_VALIDO="Archivo xml valido";
    private static final String MENSAJE_INVALIDO="Archivo xml no valido";
    private static final String MENSAJE_ERROR="No se pudo verificar el xml";
    private static final String MENSAJE_NULL="No se encontraron los archivos";

    public static String verificar()
    {
        try
        {
            InputStream xsd=VerificadorXML.class.getClassLoader().getResourceAsStream(XSD);
            InputStream xml=VerificadorXML.class.getClassLoader().getResourceAsStream(XML);

            if(xsd == null || xml == null)
            {
                return "No se encontraron los documentos XSD o XML";
            }

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));

            Validator validator =schema.newValidator();
            validator.validate(new StreamSource(xml));

            return MENSAJE_VALIDO;


        } catch (SAXException e) {
            return MENSAJE_INVALIDO+e.getMessage();
        } catch (IOException e) {
            return MENSAJE_ERROR+e.getMessage();
        }
    }
}
