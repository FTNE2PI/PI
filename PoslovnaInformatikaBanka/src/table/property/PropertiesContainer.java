package table.property;

import java.util.HashMap;
import java.util.Map;

import xmlGeneration.XmlManipulator;


/**
 * Singleton klasa koje pri njenom konstruisanju uzima sve xml-ove i od njih stvara Table Properties.</br>
 * Te Table Propertije stavlja u mapu, gde je kljuc naziv tabele.
 * @author Kosan
 *
 */
public class PropertiesContainer {

	private static PropertiesContainer instance = null;
	private Map<String, TableProperties> tablesMap;
	
	public static PropertiesContainer getInstance(){
		if(instance == null)
			instance = new PropertiesContainer();
		return instance;
	}
	
	private PropertiesContainer(){
		tablesMap = XmlManipulator.getAllTablePropertiesFromXMLs();
		
	}

	public Map<String, TableProperties> getTablesMap() {
		return tablesMap;
	}
	
	
	
}
