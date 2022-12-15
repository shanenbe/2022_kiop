package lambda.lterm.ref;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.LTerm;
import lambda.type.RefType;
import lambda.type.Type;

import java.util.Set;

public class DeRef implements LTerm {

    LTerm element;

    public DeRef(LTerm element) {
        this.element = element;
    }

    @Override
    public LTerm reduce(Memory memory) {
        if(element.isReducible())
            return new DeRef(element.reduce(memory));

        return memory.dereference((Address) element);
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return new DeRef(element.replace(header, t));
    }

    @Override
    public LTerm clone() {
        return new DeRef(element.clone());
    }

    @Override
    public Set<String> freeVariables() {
        return element.freeVariables();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof DeRef))
            return false;
        return element.equals(((DeRef) o).element);
    }

    @Override
    public boolean isReducible() {
        return true;
    }


    /*
        T-DeRef:
                       E |- t:Ref(T)
                       =============
                        E |- !t : T
     */
    @Override
    public Type type_of(Environment e) {
        RefType refType = (RefType) this.element.type_of(e);
        return refType.elementType;
    }
}
