package PO51.Kalmykov.wdad.learn.xml;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestXmlTask {
    public static void main (String[] str) throws Exception {
        XmlTask xml = new XmlTask("src\\PO51\\Kalmykov\\wdad\\learnxml\\test3.xml");
        String oldSecondName = "Ivanov";
        String oldFirstName = "Ivan";
        String newSecondName = "Petrov";
        String newFirstName = "Petr";
        Calendar calendar = new GregorianCalendar(2001, 10, 16);
        System.out.println(xml.earningsTotal(newSecondName, calendar));
        xml.changeOfficiantName(oldFirstName, oldSecondName, newFirstName, newSecondName);
        calendar.clear();
        calendar.set(2001, 10, 16);
        xml.removeDay(calendar);
        
    }

}
