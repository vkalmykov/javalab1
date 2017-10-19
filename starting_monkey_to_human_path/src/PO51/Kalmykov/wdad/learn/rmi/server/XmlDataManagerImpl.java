package PO51.Kalmykov.wdad.learn.rmi.server;

import PO51.Kalmykov.wdad.utils.Officiant;
import PO51.Kalmykov.wdad.utils.Order;
import PO51.Kalmykov.wdad.utils.Item;
import PO51.Kalmykov.wdad.utils.NoSuchOfficiantException;
import java.io.File;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlDataManagerImpl implements XmlDataManager {
    private final Document doc;
    private static final String PATH_XML = "PO51\\Kalmykov\\wdad\\learn\\rmi\\server\\ServerXml.xml";
    private final File file;

    public XmlDataManagerImpl() throws Exception {
        file = new File(PATH_XML);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
        doc.normalize();
    }
    
    
    
    @Override
    public int earningsTotal(Officiant officiant, Date date) throws RemoteException, Exception {
        int total = 0;
        String secondName;
        Date thisDate;
        NodeList orderList, bufList, dateList = doc.getElementsByTagName("date");                   
        Element element, orderElement;                                           
        for (int i = 0; i < dateList.getLength(); i++) {
            element = (Element) dateList.item(i);
            thisDate = getDate(element);
            if (date.getTime() == thisDate.getTime()) {
                orderList = element.getElementsByTagName("order");
                for (int k=0; k<orderList.getLength(); k++) {
                    orderElement = (Element) orderList.item(k);
                    element = (Element) orderElement.getElementsByTagName("officiant").item(0);
                    secondName = element.getAttribute("secondname");
                    if (officiant.getSecondName().equals(secondName)){
                        bufList = orderElement.getElementsByTagName("item");
                        for (int j=0; j<bufList.getLength(); j++){
                            element = (Element) bufList.item(j);
                            total += Integer.parseInt(element.getAttribute("cost"));
                        }
                        if (orderElement.getElementsByTagName("totalcost").getLength() == 0){
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
    

    @Override
    public void removeDay(Date date) throws RemoteException, Exception {
        Date thisDate;
        NodeList dateList = doc.getElementsByTagName("date");
        Element element;
        for (int i=0; i<dateList.getLength(); i++){
            element = (Element) dateList.item(i);
            thisDate = getDate(element);
            if (date.equals(thisDate)){
                Element el = (Element) doc.getElementsByTagName("restaurant").item(0);
                el.removeChild(element);
                writeXml();
                break;
            }
        }  


    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws RemoteException, Exception {
        Element element;
        NodeList officiantList = doc.getElementsByTagName("officiant");
        for (int i=0; i<officiantList.getLength(); i++) {
            element = (Element) officiantList.item(i);
            if (element.getAttribute("secondname").equals(oldOfficiant.getSecondName()) && 
                    element.getAttribute("firstname").equals(oldOfficiant.getFirstName())) {
                element.setAttribute("secondname", newOfficiant.getSecondName());
                element.setAttribute("firstname", newOfficiant.getFirstName());
            }
        }
        writeXml();
    }

    @Override
    public List<Order> getOrders(Date date) throws RemoteException, Exception {
        List<Order> orders = new ArrayList<>();
        List<Item> items;
        Item item;
        Order order;
        Officiant officiant;
        Date thisDate;
        NodeList orderList, itemList, nodeList = doc.getElementsByTagName("date");
        Element element, orderElement;
        for (int i=0; i<nodeList.getLength(); i++){
            element = (Element) nodeList.item(i);
            thisDate = getDate(element);
            if (date.equals(thisDate)){
                orderList = element.getElementsByTagName("order");
                for (int j=0; j<orderList.getLength(); j++){
                    orderElement = (Element) orderList.item(j);
                    element = (Element) orderElement.getElementsByTagName("officiant").item(0);
                    officiant = new Officiant(element.getAttribute("firstname"), element.getAttribute("secondname"));
                    itemList = orderElement.getElementsByTagName("item");
                    items = new ArrayList<>();
                    for (int k=0; k<itemList.getLength(); k++){
                        element = (Element) itemList.item(k);
                        item = new Item(element.getAttribute("name"), Integer.parseInt(element.getAttribute("cost")));
                        items.add(item);
                    }
                    order = new Order(officiant, items);
                    orders.add(order);
                }
                break;
            }
        }
        return orders;
    }

    @Override
    public Date lastOfficiantWorkDate(Officiant officiant) throws RemoteException, NoSuchOfficiantException, ParseException {
        Date date;
        Date dateMax = new Date(0);
        
        NodeList nodeList = doc.getElementsByTagName("date");
        Element element, dateElement;
        Officiant thisOfficiant = new Officiant();
        for (int i=0; i<nodeList.getLength(); i++){
            dateElement = (Element) nodeList.item(i);
            element = (Element) dateElement.getElementsByTagName("officiant").item(0);
            thisOfficiant.setFirstName(element.getAttribute("firstname"));
            thisOfficiant.setSecondName(element.getAttribute("secondname"));
            if (thisOfficiant.equals(officiant)){
                date = getDate(dateElement);
                if (date.getTime()>dateMax.getTime()) 
                    dateMax = date;
            }
        }
        if (dateMax.getTime()==0)
            throw new NoSuchOfficiantException();
        else return dateMax;
    }
    
    private Date getDate(Element element) throws ParseException {
        Date date;
        String stringDate;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        stringDate = element.getAttribute("day") 
                + element.getAttribute("month") 
                + element.getAttribute("year");
        date = format.parse(stringDate);
        return date;
    }
    
    private void writeXml() throws TransformerException {                      
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        t.transform(source, result);
    }
    
}
