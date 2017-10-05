package PO51.Kalmykov.wdad.data.managers;

import java.io.File;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class PreferencesManager {
    private static volatile PreferencesManager instance;
    private final Document doc;
    private static final String PATH_XML = "src\\PO51\\Kalmykov\\wdad\\resources\\configuration\\appconfig.xml";

    private PreferencesManager() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(new File(PATH_XML));
    }
    
    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
        }
        return instance;
    }
    
    public String getCreateregistry() {
        return doc.getElementsByTagName("createregistry").item(0).getTextContent();
    }
    
    public void setCreateregistry(String createregistry) {
        doc.getElementsByTagName("createregistry").item(0).setTextContent(createregistry);
    }
    
    public String getRegistryaddress() {
        return doc.getElementsByTagName("registryaddress").item(0).getTextContent();
    }
    public void setRegistryaddress(String registryaddress) {
        doc.getElementsByTagName("registryaddress").item(0).setTextContent(registryaddress);
    }
    
    public String getRegistryport() {
        return doc.getElementsByTagName("registryport").item(0).getTextContent();
    }
    public void setRegistryport(String registryport) {
        doc.getElementsByTagName("registryport").item(0).setTextContent(registryport);
    }
    
    public String getPolicypath() {
        return doc.getElementsByTagName("policypath").item(0).getTextContent();
    }
    public void setPolicypath(String policypath) {
        doc.getElementsByTagName("policypath").item(0).setTextContent(policypath);
    }
    
    public String getUsecodebaseonly() {
        return doc.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }
    public void setUsecodebaseonly(String usecodebaseonly) {
        doc.getElementsByTagName("usecodebaseonly").item(0).setTextContent(usecodebaseonly);
    }
    
    public String getClassprovider() {
        return doc.getElementsByTagName("classprovider").item(0).getTextContent();
    }
    public void setClassprovider(String classprovider) {
        doc.getElementsByTagName("classprovider").item(0).setTextContent(classprovider);
    }
    
   
    public void writeXml() throws TransformerException {                       
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(PATH_XML));
        t.transform(source, result);
    }
    
    public void setProperty(String key, String value) {
        
    }
    public String getProperty(String key) {
        
    }
    public void setProperties(Properties prop) {
        
    }
    public Properties getProperties() {
        
    }
    public void addBindedObject(String name, String className) {
        
    }
    public void removeBindedObject(String name) {
        
    }
}


