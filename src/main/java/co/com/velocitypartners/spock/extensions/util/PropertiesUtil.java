package co.com.velocitypartners.spock.extensions.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertiesUtil {

    private static final Map<String,Properties> props=new HashMap<>();


    public static void load(String file){

        String fileProp=null;

        if(file==null || file.isEmpty()){
            fileProp="/default.properties";
        }else{
            fileProp=file;
        }

        InputStream in=PropertiesUtil.class.getResourceAsStream(fileProp);
        Properties prop=new Properties();
        try {
            prop.load(in);
            props.put(file,prop);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static  String get(String file,String key){
        Properties prop=null;
        if(file==null || file.isEmpty()){
            List<Properties> list=new ArrayList<>(props.values());
            if(props.size()>0) {
                prop = list.get(0);
            }
        }else{
            prop=props.get(file);

        }
        if(prop==null){
            load(file);
            prop=props.get(file);
            if(prop==null){
                throw  new RuntimeException("No existe ningun archivo de propiedades....");
            }
        }

        return prop.getProperty(key);
    }
}
