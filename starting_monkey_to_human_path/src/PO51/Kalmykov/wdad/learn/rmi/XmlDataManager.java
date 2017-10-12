package PO51.Kalmykov.wdad.learn.rmi;

import java.util.Date;
import PO51.Kalmykov.wdad.utils.Officiant;
import PO51.Kalmykov.wdad.utils.Item;
import PO51.Kalmykov.wdad.utils.Order;
import java.rmi.Remote;
import java.util.List;
import javax.xml.crypto.dsig.TransformException;

public interface XmlDataManager  extends Remote{
    public int earningsTotal(Officiant officiant, Date date) throws TransformException;
    public void removeDay(Date date) throws TransformException;
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws TransformException;
    public List<Order> getOrders(Date date);
    public Date lastOfficiantWorkDate(Officiant officiant);
}
