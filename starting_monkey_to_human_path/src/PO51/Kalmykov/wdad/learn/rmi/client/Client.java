package PO51.Kalmykov.wdad.learn.rmi.client;

import PO51.Kalmykov.wdad.data.managers.PreferencesManager;
import PO51.Kalmykov.wdad.utils.Officiant;
import PO51.Kalmykov.wdad.learn.rmi.server.XmlDataManager;
import PO51.Kalmykov.wdad.utils.NoSuchOfficiantException;
import PO51.Kalmykov.wdad.utils.Order;
import PO51.Kalmykov.wdad.utils.Item;
import PO51.Kalmykov.wdad.utils.PreferencesConstantManager;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.List;

public class Client {
    public static void main(String[] s) throws Exception {
        PreferencesManager prefManager = PreferencesManager.getInstance();
        String securityPolicyPath = prefManager.getProperty(PreferencesConstantManager.POLICYPATH);
        int registryPort = Integer.parseInt(prefManager.getProperty(PreferencesConstantManager.REGISTRYPORT));
        String registryHost = prefManager.getProperty(PreferencesConstantManager.REGISTRYADDRESS);
        String executorName = prefManager.getExecutorName();
        System.setProperty("java.security.policy", securityPolicyPath);
        System.setSecurityManager(new SecurityManager());
        Registry registry = null;
        Date date, date2;
        Officiant officiant = new Officiant("Ivan", "Ivanov");
        Officiant newOfficiant = new Officiant("Petr", "Sidorov");
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        date = format.parse("15092000");
        date2 = format.parse("16102001");
        try {
            registry = LocateRegistry.getRegistry(registryHost, registryPort);
        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
        
        try {
            XmlDataManager xmlDataManager = (XmlDataManager) registry.lookup(executorName);
            System.out.println(xmlDataManager.earningsTotal(officiant, date));
            System.out.println(xmlDataManager.lastOfficiantWorkDate(officiant).getTime());
            xmlDataManager.changeOfficiantName(officiant, newOfficiant);
            List<Order> order = xmlDataManager.getOrders(date);
            List<Item> items;
            for (Order order1 : order) {
                System.out.println(order1.getOfficiant().getSecondName());
                System.out.println(order1.getOfficiant().getFirstName());
                items = order1.getItems();
                for (Item item : items) {
                    System.out.println(item.getName() + " " + item.getCost());
                }
            }
            xmlDataManager.removeDay(date2);
        } catch (NotBoundException | RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchOfficiantException nsoe) {
            System.err.println("No such officiant");
        }
        
    }
}
