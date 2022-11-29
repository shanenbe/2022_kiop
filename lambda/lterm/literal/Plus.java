package lambda.lterm.literal;

import lambda.Environment;
import lambda.lterm.Function;
import lambda.lterm.LTerm;
import lambda.type.BoolType;
import lambda.type.FunctionType;
import lambda.type.NumType;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Set;

public class Plus extends Function {
    @Override
    public LTerm reduce() {
        throw new RuntimeException("nicht reduzierbar");
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        throw new RuntimeException("Nicht einsetzbar");
    }

    @Override
    public LTerm clone() {
        return new Plus();
    }

    @Override
    public Set<String> freeVariables() {
        return new HashSet();
    }

    @Override
    public LTerm apply(LTerm t) {
        return new Plus1(((NumberTerm) t).value);
    }

    public boolean isReducible() {
        return false;
    }

    // T-plus: +: Num -> (Num -> Num)
    public Type type_of(Environment e) {
        return new FunctionType(new NumType(), new FunctionType(new NumType(), new NumType()));
    }
}
