package PO51.Kalmykov.wdad.learn.rmi;

import PO51.Kalmykov.wdad.data.managers.PreferencesManager;
import java.rmi.*;
import java.rmi.server.*;

public class Server {
    public static void main(String[] s) throws RemoteException, Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        
    }
}
