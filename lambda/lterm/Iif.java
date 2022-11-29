package lambda.lterm;

import lambda.Environment;
import lambda.lterm.literal.False;
import lambda.lterm.literal.True;
import lambda.lterm.literal.sumtype.InL;
import lambda.lterm.literal.sumtype.InR;
import lambda.type.BoolType;
import lambda.type.SumType;
import lambda.type.Type;

import java.util.Set;

public class Iif implements LTerm {

    public final LTerm condition;
    public final LTerm _then;
    public final LTerm _else;

    public Iif(LTerm condition, LTerm _then, LTerm _else) {
        this.condition = condition;
        this._then = _then;
        this._else = _else;
    }


    /**

     * T-iiF:
     *             E|- t:Bool  E|-t1:T1       E|-t2:T2    T1!=T2
     *             ===============================================
     *               E|- if t then t1 else t2: T1+T2
     *
     * T-iiF:
     *             E|- t:Bool  E|-t1:T       E|-t2:T
     *             ========================================
     *               E|- if t then t1 else t2: T     */
    @Override
    public Type type_of(Environment e) {
        if(!(this.condition.type_of(e).equals(new BoolType())))
            throw new RuntimeException("Condition must be Boolean");

        Type T1 = _then.type_of(e);
        Type T2 = _else.type_of(e);

        if(T1.equals(T2))
            return T1.clone();
        else
            return new SumType(T1, T2);
    }

    /**
     *   E-ifTrue:
     *
     *   |- t1: T1                |- t2: T2
     *   ============================================
     *     if true then t1 else t2 -> Inl T1+T2 t1
     */

    @Override
    public LTerm reduce() {
        if(condition.isReducible())
            return new Iif(condition.reduce(), _then.clone(), _else.clone());

        Type T1 = _then.type_of(new Environment());
        Type T2 = _else.type_of(new Environment());

        if(T1.equals(T2)) {
            if(condition.equals(new True()))
                return _then.clone();

            if(condition.equals(new False()))
                return _else.clone();

        } else {
            if(condition.equals(new True()))
                return new InL(new SumType(T1, T2), _then.clone());

            if(condition.equals(new False()))
                return new InR(new SumType(T1, T2), _else.clone());
        }




        throw new RuntimeException("something is wrong");

    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return new Iif(condition.replace(header, t),
                _then.replace(header, t),
                _else.replace(header, t));
    }

    @Override
    public LTerm clone() {
        return new Iif(this.condition.clone(), this._then.clone(), this._else.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set ret = condition.freeVariables();
        ret.addAll(_then.freeVariables());
        ret.addAll(_else.freeVariables());
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Iif))
            return false;
        Iif that = (Iif) o;
        return this.condition.equals(that.condition) &&
                this._then.equals(that._then) &&
                this._else.equals(that._else);
    }

    @Override
    public boolean isReducible() {
        return true;
    }


}
