package PO51.Kalmykov.wdad.learn.rmi;

import PO51.Kalmykov.wdad.utils.Officiant;
import PO51.Kalmykov.wdad.utils.Order;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlDataManagerImpl implements XmlDataManager {
    private final File file;
    private Document doc;
    private static final String PATH_XML = "src\\PO51\\Kalmykov\\wdad\\learnxml\\test2.xml";
    public XmlDataManagerImpl() throws Exception {
        file = new File(PATH_XML);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
        doc.normalize();
    }
    
    
    
    @Override
    public int earningsTotal(Officiant officiant, Date date) throws TransformException {
        int total=0, day, year, month;
        Date thisDate;
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
    
    
    }
    

    @Override
    public void removeDay(Date date) throws TransformException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws TransformException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getOrders(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date lastOfficiantWorkDate(Officiant officiant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
