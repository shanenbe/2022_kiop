package lambda.lterm.ref;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.LTerm;
import lambda.lterm.Unit;
import lambda.type.Type;
import lambda.type.UnitType;

import java.util.Objects;
import java.util.Set;

public class Assign implements LTerm {

    LTerm address;
    LTerm element;

    public Assign(LTerm address, LTerm element) {
        this.address = address;
        this.element = element;
    }
    @Override
    public LTerm reduce(Memory memory)
    {
        if (address.isReducible()) return new Assign(address.reduce(memory),element);
        if (element.isReducible()) return new Assign(address,element.reduce(memory));

        LTerm curr=memory.dereference((Address) address);
        if(curr.type_of(new Environment()).equals(element.type_of(new Environment())))
        {
            memory.assign((Address)address,element);
            return new Unit();
        }
        else throw new RuntimeException("Types dont match");
    }
    @Override
    public LTerm replace(String header, LTerm t) {
        return new Assign(address.replace(header,t),element.replace(header,t));
    }
    @Override
    public LTerm clone() {
        return new Assign(address.clone(),element.clone());
    }
    @Override
    public Set<String> freeVariables() {
        Set<String> ret=address.freeVariables();
        ret.addAll(element.freeVariables());
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assign)) return false;
        Assign assign = (Assign) o;
        return Objects.equals(address, assign.address) && Objects.equals(element, assign.element);
    }
    @Override
    public boolean isReducible() {
        return true;
    }
    @Override
    public Type type_of(Environment e) {
        return new UnitType();
    }
}
