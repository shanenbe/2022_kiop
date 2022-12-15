package lambda.lterm.ref;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.LTerm;
import lambda.type.RefType;
import lambda.type.Type;

import java.util.Set;

public class Ref implements LTerm {

    LTerm element;

    public Ref(LTerm element) {
        this.element = element;
    }

    @Override
    public LTerm reduce(Memory memory) {

        if(element.isReducible())
            return new Ref(element.reduce(memory));

        return memory.create_ref_in_memory(element);

    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return new Ref(element.replace(header, t));
    }

    @Override
    public LTerm clone() {
        return new Ref(element.clone());
    }

    @Override
    public Set<String> freeVariables() {
        return element.freeVariables();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Ref))
            return false;

        return element.equals(((Ref) o).element);
    }

    @Override
    public boolean isReducible() {
        return true;
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public Type type_of(Environment e) {
        return new RefType(element.type_of(e));
    }
}
