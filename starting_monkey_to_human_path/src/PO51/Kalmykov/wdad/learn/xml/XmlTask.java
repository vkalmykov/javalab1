package PO51.Kalmykov.wdad.learn.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class XmlTask {
    private final File file;
    private final Document doc;
    public XmlTask(String path) throws Exception {
        file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
        doc.normalize();
    }
    
    public int earningsTotal(String officiantSecondName, Calendar calendar) throws TransformerException {
        int total=0, day, year, month;
        Calendar thisCalendar = new GregorianCalendar();
        NodeList dateList = doc.getElementsByTagName("date");                   //массив date'ов
        NodeList orderList;                                                     // массив order'ов
        NodeList bufList;
        String secondName;
        Element element;
        Element orderElement;                                                   //переменная будет содержать item из orderList
        for (int i=0; i<dateList.getLength(); i++)
        {
            element = (Element) dateList.item(i);
            year = Integer.parseInt(element.getAttribute("year"));
            month = Integer.parseInt(element.getAttribute("month"));
            day = Integer.parseInt(element.getAttribute("day"));
            thisCalendar.clear();
            thisCalendar.set(year, month, day);
            if (calendar.equals(thisCalendar))
            {
                orderList = element.getElementsByTagName("order");
                for (int k=0; k<orderList.getLength(); k++)
                {
                    orderElement = (Element) orderList.item(k);
                    element = (Element) element.getElementsByTagName("officiant").item(0);
                    secondName = element.getAttribute("secondname");
                    if (officiantSecondName.equals(secondName))
                    {
                        
                        bufList = orderElement.getElementsByTagName("item");
                        for (int j=0; j<bufList.getLength(); j++)
                        {
                            element = (Element) bufList.item(j);
                            total += Integer.parseInt(element.getAttribute("cost"));
                        }
                        
                        if (orderElement.getElementsByTagName("totalcost").getLength() == 0)
                        {
                            element = doc.createElement("totalcost");
                            element.setTextContent(Integer.toString(total));
                            orderElement.appendChild(element);
                            writeXml();
                            
                        }
                        else if (Integer.parseInt(orderElement.getElementsByTagName("totalcost").item(0).getTextContent()) != total) {
                            orderElement.getElementsByTagName("totalcost").item(0).setTextContent(Integer.toString(total));
                            writeXml();
                        }
                    }
                }
            }
        }
        return total;
    }
    
    public void removeDay(Calendar calendar) throws TransformerException {
        int day, month, year;
        Calendar thisCalendar = new GregorianCalendar();
        NodeList dateList = doc.getElementsByTagName("date");
        Element element;
        for (int i=0; i<dateList.getLength(); i++)
        {
            element = (Element) dateList.item(i);
            year = Integer.parseInt(element.getAttribute("year"));
            day = Integer.parseInt(element.getAttribute("day"));
            month = Integer.parseInt(element.getAttribute("month"));
            thisCalendar.clear();
            thisCalendar.set(year, month, day);
            if (calendar.equals(thisCalendar))
            {
                Element el = (Element) doc.getElementsByTagName("restaurant").item(0);
                el.removeChild(element);
                writeXml();
                break;
            }
        }   
    }
    
    public void changeOfficiantName(String oldFirstName, String oldSecondName,
            String newFirstName, String newSecondName) throws TransformerException {
        Element element;
        NodeList officiantList = doc.getElementsByTagName("officiant");
        for (int i=0; i<officiantList.getLength(); i++) {
            element = (Element) officiantList.item(i);
            if (element.getAttribute("secondname").equals(oldSecondName) && 
                    element.getAttribute("firstname").equals(oldFirstName)) {
                element.setAttribute("secondname", newSecondName);
                element.setAttribute("firstname", newFirstName);
            }
        }
        writeXml();
    }
    
    private void writeXml() throws TransformerException {                       //приватный метод записи в xml
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        t.transform(source, result);
    }
}
