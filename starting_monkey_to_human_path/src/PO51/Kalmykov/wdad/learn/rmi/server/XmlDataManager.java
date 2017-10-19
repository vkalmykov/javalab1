package PO51.Kalmykov.wdad.learn.rmi.server;

import PO51.Kalmykov.wdad.utils.NoSuchOfficiantException;
import java.util.Date;
import PO51.Kalmykov.wdad.utils.Officiant;
import PO51.Kalmykov.wdad.utils.Item;
import PO51.Kalmykov.wdad.utils.Order;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

public interface XmlDataManager  extends Remote {
    public int earningsTotal(Officiant officiant, Date date) throws Exception, RemoteException;
    public void removeDay(Date date) throws Exception, RemoteException;
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws Exception, RemoteException;
    public List<Order> getOrders(Date date) throws Exception;
    public Date lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException, ParseException, RemoteException;
}
