package data;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LectorXML
{
    private static final String XML_FILE="Prueba.xml";

    public static List<String[]> obtenerTitulos()
    {
     List<String[]> titulos =new ArrayList<>();

     try {
         InputStream xml =LectorXML.class.getClassLoader().getResourceAsStream(XML_FILE);

         if ( xml == null)
         {
             titulos.add(new String[]{"Archivo no enocntrado","",""});
             return titulos;
         }

         DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
         DocumentBuilder builder =factory.newDocumentBuilder();
         Document doc = builder.parse(xml);
         doc.getDocumentElement().normalize();

         NodeList listaTitulos = doc.getElementsByTagName("Titulo");

         for ( int i=0; i <listaTitulos.getLength();i++)
         {
             Element titulo = (Element) listaTitulos.item(i);
             String codigo= titulo.getAttribute("Codigo");
             String descripcion= titulo.getAttribute("Descripcion");
             String zona =titulo.getAttribute("Zona");

             titulos.add(new String[]{codigo, descripcion, zona});
         }



    }catch (Exception e)
     {
         titulos.add(new String[]{"Error","", e.getMessage()});
     }
     return titulos;
    }
    public static List<String[]>obtenerColectivosPorTitulo(String codigo_titlulo)
    {
        List<String[]> colectivos = new ArrayList<>();

        try {
            InputStream xml = LectorXML.class.getClassLoader().getResourceAsStream(XML_FILE);
            if (xml== null)
            {
                colectivos.add(new String[]{"Archivo no encontrado","",""});
                return colectivos;
            }
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc=builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList lista_titulos= doc.getElementsByTagName("Titulo");

            for (int i =0 ; i<lista_titulos.getLength(); i++)
            {
                Element titulo =(Element) lista_titulos.item(i);
                if (codigo_titlulo.compareTo(titulo.getAttribute("Codigo"))==0)
                {
                    NodeList colectivosXML= titulo.getElementsByTagName("Colectivo");

                    for ( int j  =0 ;j < colectivosXML.getLength();j++)
                    {
                        Element colectivo = (Element) colectivosXML.item(j);
                        String cod = colectivo.getAttribute("Codigo");
                        String desc= colectivo.getAttribute("Descripcion");

                        String tarifa1= getUnidadesTarifa(colectivo,"Tarifa_Venta_1");
                        String tarifa2 =getUnidadesTarifa(colectivo,"Tarifa_Venta_2");
                        String tarifa3 =getUnidadesTarifa(colectivo,"Tarifa_Venta_3");

                        colectivos.add(new String[]{cod, desc, tarifa1,tarifa2,tarifa3});
                    }
                    break;
                }
            }


        }
        catch (Exception e)
        {
            colectivos.add(new String[]{"Error","",e.getMessage()});
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

    public static String[] buscarTituloPorCodigo(String codigoBuscado) {
        try {
            InputStream xml = LectorXML.class.getClassLoader().getResourceAsStream(XML_FILE);
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
    public static List<String[]> obtenerColectivosParaEscritura(String codigoTitulo) {
        List<String[]> datos = new ArrayList<>();

        try {
            InputStream xml = LectorXML.class.getClassLoader().getResourceAsStream(XML_FILE);
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

                    NodeList colectivos = titulo.getElementsByTagName("Colectivo");
                    for (int j = 0; j < colectivos.getLength(); j++) {
                        Element colectivo = (Element) colectivos.item(j);
                        String codColectivo = colectivo.getAttribute("Codigo");
                        String descColectivo = colectivo.getAttribute("Descripcion");

                        String tarifa1 = getUnidadesTarifa(colectivo, "Tarifa_Venta_1");
                        String tarifa2 = getUnidadesTarifa(colectivo, "Tarifa_Venta_2");
                        String tarifa3 = getUnidadesTarifa(colectivo, "Tarifa_Venta_3");

                        datos.add(new String[]{
                                codigoTitulo, descTitulo,
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


}
