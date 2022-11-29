package lambda.lterm.literal;

import lambda.Environment;
import lambda.lterm.LTerm;
import lambda.type.FunctionType;
import lambda.type.NumType;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Set;

public class Plus1 implements LTerm {

    public final int first;

    public Plus1(int first) {
        this.first = first;
    }

    @Override
    public LTerm reduce() {
        throw new RuntimeException("nicht reduzierbar");
    }

    @Override
    public boolean isFunction() {
        return LTerm.super.isFunction();
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        throw new RuntimeException("nicht einsetzbar");
    }

    @Override
    public LTerm clone() {
        return new Plus1(first);
    }

    @Override
    public Set<String> freeVariables() {
        return new HashSet<>();
    }

    public boolean isReducible() {
        return false;
    }

    /**
     * T-Plus: plus: Num -> Num -> Num
     * T-Plus: plus1: Num -> Num
     */
    @Override
    public Type type_of(Environment e) {
        return new FunctionType(new NumType(), new NumType());
    }
}
