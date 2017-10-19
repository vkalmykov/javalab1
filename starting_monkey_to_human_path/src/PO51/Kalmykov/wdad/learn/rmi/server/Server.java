package PO51.Kalmykov.wdad.learn.rmi.server;

import PO51.Kalmykov.wdad.data.managers.PreferencesManager;
import PO51.Kalmykov.wdad.utils.PreferencesConstantManager;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Server {
    private static final int EXECUTOR_PORT = 4090;
    private static final String EXECUTOR_NAME = "XmlDataManager";
    public static void main(String[] s) throws Exception {
        PreferencesManager prefManager = PreferencesManager.getInstance();
        String securityPolicyPath = prefManager.getProperty(PreferencesConstantManager.POLICYPATH);
        int registryPort = Integer.parseInt(prefManager.getProperty(PreferencesConstantManager.REGISTRYPORT));
        System.setProperty("java.rmi.server.logCalls", "true");
        System.setProperty("java.security.policy", securityPolicyPath);
        System.setSecurityManager(new SecurityManager());
        Registry registry = null;
        try {
            if (prefManager.getProperty(PreferencesConstantManager.CREATEREGISTRY).equals("yes"))
                registry = LocateRegistry.createRegistry(registryPort);
            else
                registry = LocateRegistry.getRegistry(registryPort);
        } catch (RemoteException ex) {
            System.err.println("cant locate object");
            ex.printStackTrace();
        }
            try {
                System.out.println("export");
                XmlDataManagerImpl xdmi = new XmlDataManagerImpl();
                UnicastRemoteObject.exportObject(xdmi, EXECUTOR_PORT);
                registry.rebind(EXECUTOR_NAME, xdmi);
                prefManager.addBindedObject(EXECUTOR_NAME, XmlDataManager.class.getCanonicalName());
                prefManager.writeXml();
                System.out.println("idle");
            } catch (Exception ex) {
                System.err.println("cant export");
                ex.printStackTrace();
            }
        
    }
}
