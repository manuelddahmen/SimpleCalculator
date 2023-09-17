/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pojo {
    public static boolean parseBoolean(String s) throws NumberFormatException{
        Boolean b =  ((s != null) && s.equalsIgnoreCase("true"));
        if(b)
            return true;
        if(!b && ((s!=null) && s.equalsIgnoreCase("false")))
            return false;
        throw new NumberFormatException("Boolean illegal string");
    }
    public static void setO(Object o, String propName, String value) {

        Double d;
        Integer i = 0;
        Boolean b = false;
        Logger.getAnonymousLogger().log(Level.INFO, "Pojo.before.setO. "
            +"\t"+o
            +"\t"+propName+"\t"+value);
        try {
            i = Integer.parseInt(value);
Logger.getAnonymousLogger().log(Level.INFO, "int value: "+i);
            setProperty(o, propName, i, int.class);
Logger.getAnonymousLogger().log(Level.INFO, "property "+propName+" is set to "+i);
            Logger.getAnonymousLogger().log(Level.INFO, ": " + i.getClass());
            
        } catch (NumberFormatException|InvocationTargetException | IllegalAccessException | NoSuchMethodException ex) {
           Logger.getAnonymousLogger().log(Level.INFO, "integer not set/nreason "+ex.getClass());
           try {
                d = (double) Double.parseDouble(value);
                setProperty(o, propName, d, double.class);
  Logger.getAnonymousLogger().log(Level.INFO, ": " + d.getClass());
          
            } catch (NumberFormatException|InvocationTargetException | IllegalAccessException | NoSuchMethodException ex1) {
                Logger.getAnonymousLogger().log(Level.INFO, "double not set");

                try {
                    b = (boolean) parseBoolean(value);
                    setProperty(o, propName, b, boolean.class);
                    Logger.getAnonymousLogger().log(Level.INFO, ": " + b.getClass());
          
                } catch (NumberFormatException|InvocationTargetException | IllegalAccessException | NoSuchMethodException ex2) {
                    Logger.getAnonymousLogger().log(Level.INFO, "boolean not set");

                    try {
                        if(value!=null && !"".equals(value)) {
                            setProperty(o, propName, value, String.class);
                            Logger.getAnonymousLogger().log(Level.INFO, ": " + value.getClass());
                          }
                    } catch (NumberFormatException|InvocationTargetException | IllegalAccessException | NoSuchMethodException e1) {
                      Logger.getAnonymousLogger().log(Level.INFO, "string not set");
                      //e1.printStackTrace();
                    }
                    //ex2.printStackTrace();
                }
            }
            Logger.getAnonymousLogger().log(Level.INFO, "Pojo.after.setO. "
            +"\t"+o
            +"\t"+propName+"\t"+value);
        }
        
    }
    public static void setP(Object p, String propName, 
                            String vType, String value) {
        try {
        Object o = null;
        Class c = Class.forName(vType);
        switch(vType) {
                case "double":
                case "Double":
                o = Double.parseDouble(value);
                    break;
                case "int":
                case "Integer":
                o = Integer.parseInt(value);
                    break;
                case "boolean":
                case "Boolean":
                    o = parseBoolean(value);
                    break;
                default:
                    break;
         }
         if(o!=null) {
                    
              setProperty(p, propName, o,
                               c);
         }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
   }


    public static boolean setProperties(Object o, Properties p) {
        try {
            Iterator it = p.keySet().iterator();

            while (it.hasNext()) {
                String pr = it.next().toString();
                String value = p.getProperty(pr);
                if(!value.equals(""))
                    setO(o, pr, value);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /***
     * @param o Pojo object
     * @param p Properties list String (name), String (scalar value b|d|i)
     * @return properties getter list value
     */
    public static Properties getProperties(Object o, Properties p) {

        return null;
    }

    public static Class getPropertyType(Object o, String propertyName) throws NoSuchMethodException {
        Method propertyGetter = null;
        propertyGetter = o.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.length() > 1 ? propertyName.substring(1) : ""));
        return propertyGetter.getReturnType();
    }

    public static void setProperty(Object o, String propertyName, Object value
       , Class cl) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;
        try {
            propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), cl);
        } catch(Exception ex) {}
            
            if(propertySetter==null) {
           String vType = cl.getName();

switch(vType) {
                case "double":
                case "Double":
                propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), double.class);
      
                    break;
                case "int":
                case "Integer":
                propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), int.class);
      
                    break;
                case "boolean":
                case "Boolean":
                    propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), boolean.class);
      
                    break;
                default:
                    break;
         }
} 
       propertySetter.invoke(o, value);
        Logger.getAnonymousLogger().log(Level.INFO, "type : " + o.getClass().getName() + " Property: " + propertyName + " New Value set " + getProperty(o, propertyName));
    }

    public static Object getProperty(Object o, String propertyName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;
        propertySetter = o.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + propertyName.substring(1));
        return propertySetter.invoke(o);
    }

}
