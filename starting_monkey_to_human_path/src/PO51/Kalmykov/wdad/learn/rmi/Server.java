package PO51.Kalmykov.wdad.learn.rmi;

import PO51.Kalmykov.wdad.data.managers.PreferencesManager;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Server {
    private static final String SECURITY_POLICY_PATH = "src\\PO51\\Kalmykov\\wdad\\resource\\configuration\\security.policy";
    private static final int REGISTRY_PORT = 1099;
    private static final int EXECUTOR_PORT = 49100;
    private static final String EXECUTOR_NAME = "myExecuter";
    public static void main(String[] s) {
        System.setProperty("java.rmi.server.logCalls", "true");
        System.setProperty("java.security.policy", SECURITY_POLICY_PATH);
        System.setSecurityManager(new SecurityManager());
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            XmlDataManagerImpl xdmi = new XmlDataManagerImpl();
            
            
        } catch (RemoteException ex) {
            System.err.println("cant locate object");
            ex.printStackTrace();
        }
        if (registry != null) {
            try {
                System.out.println("export");
                XmlDataManagerImpl xdml = new XmlDataManagerImpl();
                UnicastRemoteObject.exportObject(xdml, EXECUTOR_PORT);
                registry.rebind(EXECUTOR_NAME, xdml);
                System.out.println("idle");
            } catch (RemoteException ex) {
                System.err.println("cant export");
                ex.printStackTrace();
            }
        }
    }
}
