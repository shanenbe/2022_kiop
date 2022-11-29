package lambda.lterm.literal;

import lambda.Environment;
import lambda.lterm.LTerm;
import lambda.type.BoolType;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Set;

public class True implements LTerm {
    @Override
    public LTerm reduce() {
        throw new RuntimeException("Cannot reduce True");
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof True;
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return this.clone();
    }

    @Override
    public LTerm clone() {
        return new True();
    }

    @Override
    public Set<String> freeVariables() {
        return new HashSet();
    }

    public boolean isReducible() {
        return false;
    }

    @Override
    public Type type_of(Environment e) {
        return new BoolType();
    }
}
