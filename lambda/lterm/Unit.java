package lambda.lterm;
import lambda.Environment;
import lambda.Memory;
import lambda.type.Type;
import lambda.type.UnitType;

import java.util.HashSet;
import java.util.Set;
public class Unit implements LTerm
{
    @Override
    public LTerm reduce(Memory memory) {
        throw new RuntimeException("unit ist nicht reduzierbar");
    }
    @Override
    public LTerm replace(String header, LTerm t) {
        return this.clone();
    }
    @Override
    public LTerm clone() {
        return new Unit();
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
        return new UnitType();
    }
}
