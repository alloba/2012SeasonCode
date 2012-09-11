package team3329.config;

import java.util.Hashtable;
import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.

public class Configuration{
	BufferedReader in;
	Hashtable sections;
	ConfigSection currentSection;

	public Configuration(){
		in = new BufferedReader(new FileReader("config.ini"));
		sections = new Hashtable();
		currentSection = new ConfigSection();
		sections.put("default", currentSection);
	}
	
	public Configuration(String filename){
		in = new BufferedReader(new FileReader(filename));
		sections = new Hashtable();
		currentSection = new ConfigSection();
		sections.put("default", currentSection);		
	}

	public String getString(String key){
		return getString("default", key);
	}


	public String getString(String section, String key){
		return (String) sections.get(section).get(key);
	}

	public Double getDouble(String section, String key){
		return (Double) sections.get(section).getObject(key);
	}

	public ConfigSection getSection(){
		return (ConfigSection)sections.get("default");		
	}

	public ConfigSection getSection(String name){
		return (ConfigSection) sections.get(name);
	}

	public void addSection(String name){
		ConfigSection sect = new ConfigSection();
		sections.add(name, sect);
	}

	private void parse(){
		while((thisline== br.readLine())!=null){
			
		}
	}
}

class ConfigSection {
	private Hashtable data;
	
	public ConfigSection(){
		this.data = new Hashtable();
	}

	public Object getObject(String key){
		return data.get(key);
	}

	public void parseLine(String str){
                
		String[] tokens = str.("=");
		String key = tokens[0];
		String value = tokens[1];
		data.put(key, value);
	}
}
