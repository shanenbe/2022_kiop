package lambda.lterm.literal;

import lambda.Environment;
import lambda.lterm.Function;
import lambda.lterm.LTerm;
import lambda.type.BoolType;
import lambda.type.FunctionType;
import lambda.type.Type;

import java.util.Set;

public class Not extends Function {
    @Override
    public LTerm reduce() {
        return null;
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        throw new RuntimeException("wtf");
    }

    @Override
    public LTerm clone() {
        return new Not();
    }

    @Override
    public Set<String> freeVariables() {
        return null;
    }

    @Override
    public LTerm apply(LTerm t) {
        if(t instanceof True)
            return new False();
        else if (t instanceof False)
            return new True();

        throw new RuntimeException("Sollte nicht passieren, wegen Typsystem");
    }

    public boolean isReducible() {
        return false;
    }

    /*
      T-not: Bool->Bool
     */
    @Override
    public Type type_of(Environment e) {
        return new FunctionType(new BoolType(), new BoolType());
    }
}
