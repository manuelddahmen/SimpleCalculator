package one.empty3.apps.tree.altree.functions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListMathDoubleFunction {
    public static CharSequence[] getList() {
        List<CharSequence> sequences = new ArrayList<CharSequence>();
        Method[] methods = Math.class.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String s = "("+method.getName()+"(";
            for (int j = 0; j < method.getParameterTypes().length; j++) {
                Class<?> parameterType = method.getParameterTypes()[j];
                if(parameterType.equals(Double.class)) {
                    s+=parameterType.getName();
                }
                if(j < method.getParameterCount()-1)
                    s+=",";
            }
            s+=") : "+method.getReturnType().getName();

            if(method.getParameterCount()<=1 && method.getReturnType().equals(double.class))
                sequences.add(s);
        }
        Field[] numbers = Math.class.getDeclaredFields();
        for (int i = 0; i < numbers.length; i++) {
            Field field = numbers[i];
            String s = ""+field.getName()+" : "+field.getType();
            //sequences.add(s);
        }

        CharSequence[] cs = new CharSequence[sequences.size()];

        return sequences.toArray(cs);

    }
}
