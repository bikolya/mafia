package utils.resource;

import utils.parser.SaxReader;
import java.util.HashMap;
import java.util.Map;


public class ResourceFactory {
    private static ResourceFactory resourceFactory = null;
    private Map<String, Resource> resourceMap = new HashMap<>();


    public static synchronized ResourceFactory getInstance(){
        if(resourceFactory != null){
            return resourceFactory;
        }
        return resourceFactory = new ResourceFactory();
    }

    public void add(String path){
        resourceMap.put(path, (Resource) SaxReader.readXML(path));
    }

    public Resource get(String path){
        Resource resource = resourceMap.get(path);
        if(resource != null){
            return resource;
        }
        resource = (Resource)SaxReader.readXML(path);
        resourceMap.put(path, resource);
        return resource;
    }

}