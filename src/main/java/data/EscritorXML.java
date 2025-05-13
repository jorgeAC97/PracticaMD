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


    public static String guardarComoXMLCopia(List<String[]> datos, String[] metadatos) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("Informacion_Tarifas_Titulos");
            raiz.setAttribute("TipoTLV", "B7h");
            raiz.setAttribute("Version", "1.0");
            raiz.setAttribute("fecha", "2025-02-28T08:07:44");
            raiz.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            raiz.setAttribute("xsi:noNamespaceSchemaLocation", "Prueba.xsd");
            doc.appendChild(raiz);



            String codigoAnterior = "";
            Element titulo = null;

            for (String[] fila : datos) {
                String codigoActual = fila[0];

                if (!codigoActual.equals(codigoAnterior)) {
                    titulo = doc.createElement("Titulo");
                    titulo.setAttribute("Codigo", fila[0]);
                    titulo.setAttribute("Descripcion", fila[1]);
                    titulo.setAttribute("Zona", fila[2]);

                    if (metadatos != null && metadatos.length == 5 && !metadatos[0].equals("Archivo no encontrado")) {
                        Element empresa = doc.createElement("Empresa_Propietaria_Cod");
                        empresa.setTextContent(metadatos[0]);
                        titulo.appendChild(empresa);

                        Element inicio = doc.createElement("Fecha_Inicio_Venta");
                        inicio.setTextContent(metadatos[1]);
                        titulo.appendChild(inicio);

                        Element cambio1 = doc.createElement("Fecha_Cambio_Venta1");
                        cambio1.setTextContent(metadatos[2]);
                        titulo.appendChild(cambio1);

                        Element cambio2 = doc.createElement("Fecha_Cambio_Venta2");
                        cambio2.setTextContent(metadatos[3]);
                        titulo.appendChild(cambio2);

                        Element fin = doc.createElement("Fecha_Fin_Venta");
                        fin.setTextContent(metadatos[4]);
                        titulo.appendChild(fin);
                    }


                    raiz.appendChild(titulo);
                    codigoAnterior = codigoActual;
                }

                // Agregar Colectivo
                Element colectivo = doc.createElement("Colectivo");
                colectivo.setAttribute("Codigo", fila[3]);
                colectivo.setAttribute("Descripcion", fila[4]);

                for (int j = 5; j <= 7; j++) {
                    Element tarifa = doc.createElement("Tarifa_Venta_" + (j - 4));
                    Element tipoUnidades = doc.createElement("Tipo_Unidades");
                    tipoUnidades.setTextContent("02h");
                    Element unidades = doc.createElement("Unidades");
                    unidades.setTextContent(fila[j]);

                    tarifa.appendChild(tipoUnidades);
                    tarifa.appendChild(unidades);
                    colectivo.appendChild(tarifa);
                }

                titulo.appendChild(colectivo);
            }

            File directorio = new File(new File(ARCHIVO_DESTINO).getParent());
            if (!directorio.exists()) directorio.mkdirs();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-15");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(ARCHIVO_DESTINO));
            transformer.transform(source, result);

            return "Archivo XML_copia.xml guardado correctamente.";
        } catch (Exception e) {
            return "Error al guardar el XML: " + e.getMessage();
        }
    }

    private static void docAppendTextElement(Document doc, Element parent, String tagName, String value) {
        Element elem = doc.createElement(tagName);
        elem.setTextContent(value);
        parent.appendChild(elem);
    }
}
