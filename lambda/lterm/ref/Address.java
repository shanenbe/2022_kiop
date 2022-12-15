package lambda.lterm.ref;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.LTerm;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Set;

public class Address implements LTerm {

    public Address(int index) {
        this.index = index;
    }

    public final int index;

    @Override
    public LTerm reduce(Memory memory) {
        throw new RuntimeException("Adressen können sich nicht reduzieren");
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return this.clone();
    }

    @Override
    public LTerm clone() {
        return new Address(this.index);
    }

    @Override
    public Set<String> freeVariables() {
        return new HashSet<>();
    }

    @Override
    public boolean isReducible() {
        return false;
    }

    @Override
    public Type type_of(Environment e) {
        throw new RuntimeException("Besser nicht nachfragen");
    }
}
