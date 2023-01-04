package lambda.lterm;

import lambda.Environment;
import lambda.Memory;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Fix implements LTerm{

    public final Abstraction identifier;
    public final LTerm term;

    public Fix(Abstraction identifier, LTerm term) {
        this.identifier = identifier;
        this.term = term;
    }

    @Override
    public LTerm reduce(Memory memory) {
        if (term.isReducible()) return new Fix(identifier.clone(),term.reduce(memory));

        return term.replace(identifier.header, this.clone());
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return new Fix(identifier.clone(),term.replace(header,t));
    }

    @Override
    public LTerm clone() {
        return new Fix(identifier.clone(),term.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set<String> ret = term.freeVariables();
        ret.remove(identifier.header);
        return ret;
    }

    //Hier bin ich mir unsicher theoretisch ja immer Reduzierbar, aber es gibt ja eine Abbruchbedingung.
    @Override
    public boolean isReducible() {
        return true;
    }


    //Glaube das ist nicht richtig
     @Override
    public Type type_of(Environment e) {
        return term.type_of(e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fix fix)) return false;
        return Objects.equals(identifier, fix.identifier) && Objects.equals(term, fix.term);
    }
}
