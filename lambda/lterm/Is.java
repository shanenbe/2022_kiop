package lambda.lterm;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.literal.False;
import lambda.lterm.literal.True;
import lambda.type.BoolType;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Set;

public class Is implements LTerm{

    public final LTerm term;
    public final Type type;

    public Is(LTerm term, Type type) {
        this.term = term;
        this.type = type;
    }

    @Override
    public LTerm reduce(Memory memory) {
        if(this.term.type_of(new Environment()).equals(this.type)) return new True();
        else return new False();
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return this.clone();
    }

    @Override
    public LTerm clone() {
        return new Is(this.term.clone(),this.type.clone());
    }

    @Override
    public Set<String> freeVariables() {
        return new HashSet<>();
    }

    @Override
    public boolean isReducible() {
        return true;
    }

    @Override
    public Type type_of(Environment e) {
        return new BoolType();
    }
}
