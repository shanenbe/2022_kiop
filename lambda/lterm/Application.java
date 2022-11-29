package lambda.lterm;

import lambda.Environment;
import lambda.type.FunctionType;
import lambda.type.Type;

import java.util.Objects;
import java.util.Set;

public class Application implements LTerm {

    public final LTerm left;
    public final LTerm right;

    public Application(LTerm left, LTerm right) {
        this.left = left;
        this.right = right;
    }

    @Override
    // (Lx.t1) t2 -> [x:=t2] t1


    // EApp1:
    //            t1 -> t1'
    //        ===============
    //        t1 t2 -> t1' t2

    // EApp2
    //            t2 -> t2'
    //        ===============
    //        t1 t2 -> t1 t2'

    // EApp3
    //        (Lx:T.t) t2 -> [x:=t2] t

    public LTerm reduce() {

        if(left.isReducible())
            return new Application(left.reduce(), right.clone());

        if(right.isReducible())
            return new Application(left.clone(), right.reduce());

        if(left.isFunction())
            return ((Function) left).apply(right);

        throw new RuntimeException("Ich kann nicht reduzieren");
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    // [x:=t] t1 t2 -> [x:=t] t1 [x:=t] t2 ->
    public LTerm replace(String v, LTerm t) {
        return new Application(left.replace(v, t), right.replace(v, t));
    }

    public Application clone() {
        return new Application(left.clone(), right.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set<String> ret = left.freeVariables();
        ret.addAll(right.freeVariables());
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public boolean isReducible() {
        return left.isReducible() || right.isReducible() || left.isFunction();
    }

    /*
    T-App:
               E|-t1: T'->T E|- t2: T'
               =======================
                    E |- t1 t2: T
     */

    @Override
    public Type type_of(Environment e) {
        FunctionType t1 = (FunctionType) left.type_of(e);
        Type t = right.type_of(e);

        if(! (t.is_Subtype_of(t1.left)))
            throw new RuntimeException("Falscher Typ");

        return t1.right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

}
