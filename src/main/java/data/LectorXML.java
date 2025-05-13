package data;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LectorXML
{
    public static final String ARCHIVO_ORIGINAL = "Prueba.xml";
    public static final String ARCHIVO_COPIA = "XML_copia.xml";


    public static List<String[]> obtenerTitulos(String nombreArchivo) {
        List<String[]> titulos = new ArrayList<>();

        try (InputStream xml = obtenerInputStream(nombreArchivo)) {
            if (xml == null) {
                titulos.add(new String[]{"Archivo no encontrado", "", ""});
                return titulos;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList listaTitulos = doc.getElementsByTagName("Titulo");

            for (int i = 0; i < listaTitulos.getLength(); i++) {
                Element titulo = (Element) listaTitulos.item(i);
                String codigo = titulo.getAttribute("Codigo");
                String descripcion = titulo.getAttribute("Descripcion");
                String zona = titulo.getAttribute("Zona");

                titulos.add(new String[]{codigo, descripcion, zona});
            }

        } catch (Exception e) {
            titulos.add(new String[]{"Error", "", e.getMessage()});
        }

        return titulos;
    }


    public static List<String[]> obtenerColectivosPorTitulo(String archivoXML, String codigoTitulo) {
        List<String[]> colectivos = new ArrayList<>();

        try {
            InputStream xml = obtenerInputStream(archivoXML);

            if (xml == null) {
                colectivos.add(new String[]{"Archivo no encontrado", "", ""});
                return colectivos;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList listaTitulos = doc.getElementsByTagName("Titulo");

            for (int i = 0; i < listaTitulos.getLength(); i++) {
                Element titulo = (Element) listaTitulos.item(i);
                if (codigoTitulo.equals(titulo.getAttribute("Codigo"))) {
                    NodeList colectivosXML = titulo.getElementsByTagName("Colectivo");

                    for (int j = 0; j < colectivosXML.getLength(); j++) {
                        Element colectivo = (Element) colectivosXML.item(j);
                        String cod = colectivo.getAttribute("Codigo");
                        String desc = colectivo.getAttribute("Descripcion");

                        String tarifa1 = getUnidadesTarifa(colectivo, "Tarifa_Venta_1");
                        String tarifa2 = getUnidadesTarifa(colectivo, "Tarifa_Venta_2");
                        String tarifa3 = getUnidadesTarifa(colectivo, "Tarifa_Venta_3");

                        colectivos.add(new String[]{cod, desc, tarifa1, tarifa2, tarifa3});
                    }
                    break;
                }
            }

        } catch (Exception e) {
            colectivos.add(new String[]{"Error", "", e.getMessage()});
        }

        return colectivos;
    }


    private static String getUnidadesTarifa(Element colectivo, String nombre_tarifa)
    {
        try {
            Element tarifa =(Element) colectivo.getElementsByTagName(nombre_tarifa).item(0);
            return tarifa.getElementsByTagName("Unidades").item(0).getTextContent();
        }
        catch (Exception e) {
            return "N/A";
        }
    }

    public static String[] buscarTituloPorCodigo(String archivoXML, String codigoBuscado) {
        try {
            InputStream xml = obtenerInputStream(archivoXML);

            if (xml == null) return new String[]{"Error", "Archivo no encontrado", ""};

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList listaTitulos = doc.getElementsByTagName("Titulo");

            for (int i = 0; i < listaTitulos.getLength(); i++) {
                Element titulo = (Element) listaTitulos.item(i);
                if (codigoBuscado.equals(titulo.getAttribute("Codigo"))) {
                    String descripcion = titulo.getAttribute("Descripcion");
                    String zona = titulo.getAttribute("Zona");
                    return new String[]{codigoBuscado, descripcion, zona};
                }
            }

            return new String[]{"No encontrado", "", ""};

        } catch (Exception e) {
            return new String[]{"Error", "", e.getMessage()};
        }
    }


    public static String[] obtenerMetadatosTitulo(String archivoXML, String codigoTitulo)
    {
        try {
            InputStream xml = obtenerInputStream(archivoXML);

            if (xml == null) return new String[]{"Archivo no encontrado", "", "", "", ""};

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList listaTitulos = doc.getElementsByTagName("Titulo");

            for (int i = 0; i < listaTitulos.getLength(); i++) {
                Element titulo = (Element) listaTitulos.item(i);
                if (codigoTitulo.equals(titulo.getAttribute("Codigo"))) {
                    String empresa = obtenerTexto(titulo, "Empresa_Propietaria_Cod");
                    String fechaInicio = obtenerTexto(titulo, "Fecha_Inicio_Venta");
                    String cambio1 = obtenerTexto(titulo, "Fecha_Cambio_Venta1");
                    String cambio2 = obtenerTexto(titulo, "Fecha_Cambio_Venta2");
                    String fechaFin = obtenerTexto(titulo, "Fecha_Fin_Venta");

                    return new String[]{empresa, fechaInicio, cambio1, cambio2, fechaFin};
                }
            }

        } catch (Exception e) {
            return new String[]{"Error", "", "", "", e.getMessage()};
        }
        return new String[]{"No encontrado", "", "", "", ""};
    }

    private static String obtenerTexto(Element padre, String etiqueta) {
        NodeList elementos = padre.getElementsByTagName(etiqueta);
        return (elementos.getLength() > 0) ? elementos.item(0).getTextContent() : "N/A";
    }
    public static List<String[]> obtenerColectivosParaEscritura(String archivoXML, String codigoTitulo) {
        List<String[]> datos = new ArrayList<>();

        try {
            InputStream xml = obtenerInputStream(archivoXML);

            if (xml == null) {
                datos.add(new String[]{"Error", "Archivo no encontrado", "", "", "", "", ""});
                return datos;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList titulos = doc.getElementsByTagName("Titulo");

            for (int i = 0; i < titulos.getLength(); i++) {
                Element titulo = (Element) titulos.item(i);

                if (codigoTitulo.equals(titulo.getAttribute("Codigo"))) {
                    String descTitulo = titulo.getAttribute("Descripcion");
                    String zona = titulo.getAttribute("Zona");

                    NodeList colectivos = titulo.getElementsByTagName("Colectivo");
                    for (int j = 0; j < colectivos.getLength(); j++) {
                        Element colectivo = (Element) colectivos.item(j);
                        String codColectivo = colectivo.getAttribute("Codigo");
                        String descColectivo = colectivo.getAttribute("Descripcion");

                        String tarifa1 = getUnidadesTarifa(colectivo, "Tarifa_Venta_1");
                        String tarifa2 = getUnidadesTarifa(colectivo, "Tarifa_Venta_2");
                        String tarifa3 = getUnidadesTarifa(colectivo, "Tarifa_Venta_3");

                        datos.add(new String[]{
                                codigoTitulo, descTitulo, zona,
                                codColectivo, descColectivo,
                                tarifa1, tarifa2, tarifa3
                        });
                    }
                    break;
                }
            }

        } catch (Exception e) {
            datos.add(new String[]{"Error", "", "", "", "", "", e.getMessage()});
        }

        return datos;
    }
    private static InputStream obtenerInputStream(String nombreArchivo) throws IOException {
        File file = new File("res/"+ nombreArchivo);
        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            return LectorXML.class.getClassLoader().getResourceAsStream(nombreArchivo);
        }
    }

}
