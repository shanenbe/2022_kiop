package lambda;

import lambda.type.Type;

import java.util.Hashtable;

public class Environment {
    public final Hashtable<String, Type> types = new Hashtable<String, Type>();

    public Environment new_with(String var, Type t) {
        Environment e = new Environment();
        e.types.putAll(types);
        e.types.put(var, t);
        return e;
    }

    public Type contains(String varName) {
        return types.get(varName);
    }


}
