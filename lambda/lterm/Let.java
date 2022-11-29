package lambda.lterm;

import lambda.Environment;
import lambda.type.Type;

import java.util.Set;


public class Let implements LTerm {

    public final String varName;
    public final LTerm term;
    public final LTerm body;

    public Let(String varName, LTerm term, LTerm body) {
        this.varName = varName;
        this.term = term;
        this.body = body;
    }

    /**
     *   let x = ((Ly:Num. z:Num. + z 1) 1) in + (x 1) (x 1)
     *   -> let x = (z:Num. + z 1)  in + (x 1) (x 1)
     *   -> [x:=(z:Num. + z 1)] + (x 1) (x 1)
     *   -> + ((z:Num. + z 1) 1) ((z:Num. + z 1) 1)
     */


    public LTerm reduce() {
        if(term.isReducible())
            return new Let(varName, term.reduce(), body.clone());
        return body.replace(varName, term);
    }

    @Override
    public LTerm replace(String header, LTerm t) {

        if(header.equals(varName))
            return new Let(varName,
                    term.replace(header, t.clone()),
                    body.clone());

        return new Let(varName,
                        term.replace(header, t.clone()),
                        body.replace(header, t.clone()));

    }

    @Override
    public LTerm clone() {
        return new Let(varName, term.clone(), body.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set<String> ret = term.freeVariables();
        ret.addAll(body.freeVariables());
        ret.remove(varName);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Let))
            return false;

        Let that = (Let) o;
        return this.varName.equals(that.varName) &&
                this.term.equals(that.term) &&
                this.body.equals(that.body);
    }

    @Override
    public boolean isReducible() {
        return true;
    }


    /**
     * T-let:
     *               E|- t1: T1     E, (x:T1) |- t2: T
     *               =======================================
     *                       E |- let x = t1 in t2: T
     */
    public Type type_of(Environment e) {

        Type T1 = term.type_of(e);
        Environment newE = e.new_with(varName, T1);
        Type T = body.type_of(newE);
        return T;

    }
}
