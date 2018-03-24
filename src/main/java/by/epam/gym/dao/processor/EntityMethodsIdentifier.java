package by.epam.gym.dao.processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EntityMethodsIdentifier {

    public List<Method> identifyMethodsByType(MethodType type, Class clazz){
        Method[] allMethods = findAllMethods(clazz);
        List<Method> sortedMethods = new ArrayList<>();

        for (Method method : allMethods) {
            boolean isTypeRight = false;

            switch (type){
                case GETTER:{
                    isTypeRight = isMethodGetter(method);
                    break;
                }
                case SETTER:{
                    isTypeRight = isMethodSetter(method);
                    break;
                }
            }

            if (isTypeRight){
                sortedMethods.add(method);
            }
        }

        return sortedMethods;
    }

    private Method[] findAllMethods(Class clazz){
        return clazz.getMethods();
    }

    private boolean isMethodGetter(Method method){
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }

    private boolean isMethodSetter(Method method){
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

}
