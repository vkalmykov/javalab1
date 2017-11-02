package PO51.Kalmykov.wdad.data.managers;

import PO51.Kalmykov.wdad.utils.PreferencesConstantManager;
import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
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

public class PreferencesManager {
    private static volatile PreferencesManager instance;
    private final Document doc;
    private static final String PATH_XML = "PO51\\Kalmykov\\wdad\\resources\\configuration\\appconfig.xml";
    private static final File FILE = new File(PATH_XML);
    
    private PreferencesManager() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(FILE);
    }
    
    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
        }
        return instance;
    }
    @Deprecated
    public String getCreateregistry() {
        return doc.getElementsByTagName("createregistry").item(0).getTextContent();
    }
    @Deprecated
    public void setCreateregistry(String createregistry) {
        doc.getElementsByTagName("createregistry").item(0).setTextContent(createregistry);
    }
    @Deprecated
    public String getRegistryaddress() {
        return doc.getElementsByTagName("registryaddress").item(0).getTextContent();
    }
    @Deprecated
    public void setRegistryaddress(String registryaddress) {
        doc.getElementsByTagName("registryaddress").item(0).setTextContent(registryaddress);
    }
    @Deprecated
    public String getRegistryport() {
        return doc.getElementsByTagName("registryport").item(0).getTextContent();
    }
    @Deprecated
    public void setRegistryport(String registryport) {
        doc.getElementsByTagName("registryport").item(0).setTextContent(registryport);
    }
    @Deprecated
    public String getPolicypath() {
        return doc.getElementsByTagName("policypath").item(0).getTextContent();
    }
    @Deprecated
    public void setPolicypath(String policypath) {
        doc.getElementsByTagName("policypath").item(0).setTextContent(policypath);
    }
    @Deprecated
    public String getUsecodebaseonly() {
        return doc.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }
    @Deprecated
    public void setUsecodebaseonly(String usecodebaseonly) {
        doc.getElementsByTagName("usecodebaseonly").item(0).setTextContent(usecodebaseonly);
    }
    @Deprecated
    public String getClassprovider() {
        return doc.getElementsByTagName("classprovider").item(0).getTextContent();
    }
    @Deprecated
    public void setClassprovider(String classprovider) {
        doc.getElementsByTagName("classprovider").item(0).setTextContent(classprovider);
    }
    
   
    public void writeXml() throws TransformerException {                       
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(FILE);
        t.transform(source, result);
    }
    
    public void setProperty(String key, String value) {
        doc.getElementsByTagName(getTag(key)).item(0).setTextContent(value);
    }
    public String getProperty(String key) {
        return doc.getElementsByTagName(getTag(key)).item(0).getTextContent();
    }
    public void setProperties(Properties prop) {
        Enumeration e = prop.elements();
        Enumeration keys = prop.keys();
        while(e.hasMoreElements())
        {
            doc.getElementsByTagName(getTag((String) keys.nextElement())).item(0).setTextContent((String) e.nextElement());
        }
    }
    public Properties getProperties() {
        Properties prop = new Properties();
        prop.put(PreferencesConstantManager.CREATEREGISTRY, doc.getElementsByTagName(getTag(PreferencesConstantManager.CREATEREGISTRY)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.CLASSPROVIDER, doc.getElementsByTagName(getTag(PreferencesConstantManager.CLASSPROVIDER)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.POLICYPATH, doc.getElementsByTagName(getTag(PreferencesConstantManager.POLICYPATH)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.REGISTRYADDRESS, doc.getElementsByTagName(getTag(PreferencesConstantManager.REGISTRYADDRESS)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.USECODEBASEONLY, doc.getElementsByTagName(getTag(PreferencesConstantManager.USECODEBASEONLY)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.REGISTRYPORT, doc.getElementsByTagName(getTag(PreferencesConstantManager.REGISTRYPORT)).item(0).getTextContent());
        
        return prop;
    }
    public void addBindedObject(String name, String className) {
        Element e = (Element) doc.createElement("bindedobject");
        e.setAttribute("name", name);
        e.setAttribute("class", className);
        doc.getElementsByTagName("server").item(0).appendChild(e);
    }
    public void removeBindedObject(String name) {
        NodeList nodeList = doc.getElementsByTagName("bindedobject");
        Element e;
        for (int i=0; i<nodeList.getLength(); i++)
        {
            e = (Element) nodeList.item(i);
            if (e.getAttribute("name").equals(name))
            {
                doc.getElementsByTagName("server").item(0).removeChild(e);
            }
        }
    }
    
    public String getExecutorName() {
        Element el = (Element) doc.getElementsByTagName("bindedobject").item(0);
        return el.getAttribute("name");
    }
    private String getTag(String s) {
        String[] sa = s.split("\\.");
        return sa[sa.length - 1];
    }
}


