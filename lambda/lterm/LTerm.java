package lambda.lterm;

import lambda.Environment;
import lambda.Memory;
import lambda.type.Type;

import java.util.Set;

public interface LTerm {

   public LTerm reduce(Memory memory);

    default public boolean isFunction() {
        return false;
    }

    LTerm replace(String header, LTerm t);

    LTerm clone();

    Set<String> freeVariables();

    public boolean equals(Object o);

    public boolean isReducible();

    public default LTerm reduceAll(Memory memory) {
        LTerm t = this;
        while(t.isReducible())
            t = t.reduce(memory);
        return t;
    }

    public Type type_of(Environment e);


}
