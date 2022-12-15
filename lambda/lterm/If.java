package lambda.lterm;

import lambda.Environment;
import lambda.Memory;
import lambda.lterm.literal.False;
import lambda.lterm.literal.True;
import lambda.type.BoolType;
import lambda.type.Type;

import java.util.Set;

public class If implements LTerm {
    public final LTerm condition;
    public final LTerm _then;
    public final LTerm _else;


    public If(LTerm condition, LTerm then, LTerm anElse) {
        this.condition = condition;
        _then = then;
        _else = anElse;
    }

    @Override
    /**
     * E-if:
     *                       t1 -> t1'
     *                   ===============================================
     *                   if t1 then t2 else t3 -> if t1' then t2 else t3
     *
     * E-ifTrue:    if true then t2 else t3 -> t2
     * E-ifFalse: if flase then t2 else t3 -> t3
     *
     */
    public LTerm reduce(Memory memory) {
        if(condition.isReducible())
            return new If(condition.reduce(memory), _then.clone(), _else.clone());

        if(condition.equals(new True()))
            return _then.clone();

        if(condition.equals(new False()))
            return _else.clone();

        throw new RuntimeException("Something is strange");
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return new If(condition.replace(header, t),
                      _then.replace(header, t),
                      _else.replace(header, t));
    }

    @Override
    public LTerm clone() {
        return new If(this.condition.clone(), this._then.clone(), this._else.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set<String> ret = condition.freeVariables();
        ret.addAll(_then.freeVariables());
        ret.addAll(_else.freeVariables());
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof If))
            return false;
        else {
            If that = (If) o;
            return this.condition.equals(that.condition) &&
                    this._then.equals(that._then) &&
                    this._else.equals(that._else);
        }

    }

    @Override
    public boolean isReducible() {
        return true;
    }

    /**
     * T-if:
     *              E|- t:Bool E|-t1: T   E|-t2:T
     *              ================================
     *                E |- if t then t1 else t2: T
     */

    @Override
    public Type type_of(Environment e) {
        BoolType condType = (BoolType) condition.type_of(e);
        if(_then.type_of(e).equals(_else.type_of(e)))
            return _then.type_of(e);

        throw new RuntimeException("Fehler in T-if");
    }
}
