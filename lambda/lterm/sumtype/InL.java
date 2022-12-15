package lambda.lterm.sumtype;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.LTerm;
import lambda.type.SumType;
import lambda.type.Type;

import java.util.Set;

public class InL implements LTerm {

    public final SumType sumtype;
    public final LTerm term;

    public InL(SumType sumtype, LTerm term) {
        this.sumtype = sumtype;
        this.term = term;
    }

    /**
     * Bsp: inl Bool+Num (not true) -> inl Bool+Num (false)
     * @param memory
     */
    @Override
    public LTerm reduce(Memory memory) {
        if (term.isReducible())
            return new InL(sumtype.clone(), term.reduce(memory));
        throw new RuntimeException("Cannot reduce ....");
    }

    /**
     * Lx:Bool
     *   inL Bool+Num x
     * @param header
     * @param t
     * @return
     */
    @Override
    public LTerm replace(String header, LTerm t) {
        return new InL(sumtype.clone(), term.replace(header, t));
    }

    @Override
    public LTerm clone() {
        return new InL(sumtype.clone(), term.clone());
    }

    @Override
    public Set<String> freeVariables() {
        return term.freeVariables();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof InL))
            return false;

        InL that = (InL) o;
        return this.sumtype.equals(that.sumtype) &&
                this.term.equals(that.term);
    }

    @Override
    public boolean isReducible() {
        return term.isReducible();
    }

    /**
     * T-Inl
     *              E |- t:T1
     *            ==============================
     *                E |- Inl T1+T2 t: T1+T2
     */
    @Override
    public Type type_of(Environment e) {
        if(term.type_of(e).equals(sumtype.left))
            return sumtype.clone();
        throw new RuntimeException("Invalid type");
    }
}
