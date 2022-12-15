package lambda.lterm;

import lambda.Environment;
import lambda.Memory;
import lambda.type.RecordType;
import lambda.type.Type;

import java.util.Set;

public class Projection implements LTerm {
    LTerm term;
    String label;

    public Projection(LTerm term, String label) {
        this.term = term;
        this.label = label;
    }


    @Override
    public LTerm reduce(Memory memory) {
        if(term.isReducible())
            return new Projection(term.reduce(memory), label);

        return ((Record) term).get(label);
    }

    @Override
    public Projection replace(String header, LTerm t) {
        return new Projection(term.replace(header, t), label);
    }

    @Override
    public Projection clone() {
        return new Projection(term.clone(), label);
    }

    @Override
    public Set<String> freeVariables() {
        return term.freeVariables();
    }

    /**
     *
     * E-Proj:
     *                {l1=t1, ..... ln=tn}.li -> ti
     */

    @Override
    public boolean isReducible() {
        return true;
    }

    /**
     *
     * T-Proj:
     *            E |- t: {l1:T1, .....li:Ti......ln:Tn}
     *           ========================================
     *                 E |-       t.li : Ti
     *
     */
    @Override
    public Type type_of(Environment e) {
        RecordType RT = (RecordType) term.type_of(e);
        return RT.get(this.label);

    }
}
