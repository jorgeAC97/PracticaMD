package data;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.InputStream;

public class EscritorXML {
    private static final String ARCHIVO_ORIGINAL = "Prueba.xml";
    private static final String ARCHIVO_DESTINO = "res/XML_copia.xml";

    public static String copiarYModificarXML(String nuevoCodigo) {
        try {

            InputStream input = LectorXML.class.getClassLoader().getResourceAsStream(ARCHIVO_ORIGINAL);
            if (input == null) return "Archivo original no encontrado.";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document originalDoc = builder.parse(input);
            originalDoc.getDocumentElement().normalize();

            // Modificación de ejemplo: cambia el código del primer título
            NodeList titulos = originalDoc.getElementsByTagName("Titulo");
            if (titulos.getLength() > 0) {
                Element primerTitulo = (Element) titulos.item(0);
                primerTitulo.setAttribute("Codigo", nuevoCodigo); // cambio simple
            }

            // Guardar nuevo documento modificado
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(originalDoc);
            StreamResult result = new StreamResult(new File(ARCHIVO_DESTINO));
            transformer.transform(source, result);

            return "Archivo modificado guardado como copia.";
        } catch (Exception e) {
            return "Error al modificar el XML: " + e.getMessage();
        }
    }
}
