package PO51.Kalmykov.wdad.learnxml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.Calendar;


public class XmlTask {
    private final File file;
    private Document doc;
    
    public XmlTask(String path) throws Exception {
        file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
    }
    
    public int earningsTotal(String officiantSecondName, Calendar calendar) {
        int total=0, day, year, month;
        Calendar thisCalendar = null;
        NodeList nodeList = doc.getElementsByTagName("date");
        NodeList orderList;
        String secondName;
        Element element;
        for (int i=0; i<nodeList.getLength(); i++)
        {
            element = (Element) nodeList.item(i);
            year = Integer.parseInt(element.getAttribute("year"));
            month = Integer.parseInt(element.getAttribute("month"));
            day = Integer.parseInt(element.getAttribute("day"));
            thisCalendar.set(year, month, day);
            if (calendar.equals(thisCalendar))
            {
                orderList = element.getElementsByTagName("order");
                for (int k=0; k<orderList.getLength(); k++)
                {
                    element = (Element) orderList.item(k);
                }
            }
        }
        return total;
    }
    
    public void removeDay(Calendar calendar) {
        
    }
    
    public void changeOfficiantName(String oldFirstName, String oldSecondName,
            String newFirstName, String newSecondName) {
        
    }
    
    public void test(){
        NodeList orderList = doc.getElementsByTagName("officiant");
        Element element;
        for (int i=0; i<orderList.getLength(); i++)
        {
            element = (Element) orderList.item(i);
            System.out.println(element.getAttribute("secondname"));
        }
    }
}
