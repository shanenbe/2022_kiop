package lambda.lterm;

import lambda.Environment;
import lambda.functions.Visitor;
import lambda.type.Type;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Variable implements LTerm {

    public final String varname;

    public Variable(String varname) {
        this.varname = varname;

    }

    @Override
    public LTerm reduce() {
        throw new RuntimeException("Reduzier keine Variablen");
    }

    @Override
    public boolean isFunction() {
        return  false;
    }

    @Override
    // [x := t] v -> t (wenn x = v), ansonsten v
    public LTerm replace(String varname, LTerm t) {
        if (this.varname.equals(varname)) {
            return t.clone();
        } else {
            return this.clone();
        }
    }

    public Variable clone() {
        return new Variable(this.varname);
    }

    @Override
    public Set<String> freeVariables() {
        return Set.of(varname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return varname.equals(variable.varname);
    }

    @Override
    public boolean isReducible() {
        return false;
    }

    /*
       T-Var:
                         x:T € E
                    ===================
                          E |- x:T
     */
    @Override
    public Type type_of(Environment e) {
        return e.contains(this.varname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varname);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
