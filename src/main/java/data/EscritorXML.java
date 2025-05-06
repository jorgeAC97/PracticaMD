package data;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.InputStream;
import java.util.List;

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
    public static String guardarComoXMLCopia(List<String[]> datos) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("Titulos");
            doc.appendChild(raiz);

            for (String[] fila : datos) {
                Element titulo = doc.createElement("Titulo");
                titulo.setAttribute("Codigo", fila[0]);
                titulo.setAttribute("Descripcion", fila[1]);

                Element colectivo = doc.createElement("Colectivo");
                colectivo.setAttribute("Codigo", fila[2]);
                colectivo.setAttribute("Descripcion", fila[3]);

                for (int j = 4; j <= 6; j++) {
                    Element tarifa = doc.createElement("Tarifa_Venta_" + (j - 3));
                    Element unidades = doc.createElement("Unidades");
                    unidades.setTextContent(fila[j]);
                    tarifa.appendChild(unidades);
                    colectivo.appendChild(tarifa);
                }

                titulo.appendChild(colectivo);
                raiz.appendChild(titulo);
            }

            // Asegurar que la carpeta exista
            File directorio = new File("res");
            if (!directorio.exists()) directorio.mkdirs();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/XML_copia.xml"));
            transformer.transform(source, result);

            return "Archivo XML_copia.xml guardado correctamente.";
        } catch (Exception e) {
            return "Error al guardar el XML: " + e.getMessage();
        }
    }

}
