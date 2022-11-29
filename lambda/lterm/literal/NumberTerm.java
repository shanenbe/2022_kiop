package lambda.lterm.literal;

import lambda.Environment;
import lambda.lterm.LTerm;
import lambda.type.NumType;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Set;

public class NumberTerm implements LTerm {

    public final int value;

    public NumberTerm(int value) {
        this.value = value;
    }

    @Override
    public LTerm reduce() {
        throw new RuntimeException("Numbers cannot be reduced");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NumberTerm) && ((((NumberTerm) obj).value == value));
    }

    @Override
    public boolean isReducible() {
        return false;
    }

    @Override
    public Type type_of(Environment e) {
        return new NumType();
    }

    @Override
    public boolean isFunction() {
        return LTerm.super.isFunction();
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return this.clone();
    }

    @Override
    public LTerm clone() {
        return new NumberTerm(this.value);
    }

    @Override
    public Set<String> freeVariables() {
        return new HashSet<String>();
    }
}
