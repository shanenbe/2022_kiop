package lambda.lterm.literal.sumtype;

import lambda.Environment;
import lambda.lterm.LTerm;
import lambda.type.SumType;
import lambda.type.Type;

import java.util.Set;

public class Case implements LTerm {

    public final LTerm condition;
    public final SumType sumType;
    public final String leftVar;
    public final LTerm left;
    public final String rightVar;
    public final LTerm right;

    public Case(LTerm condition, SumType sumType, String leftVar, LTerm left, String rightVar, LTerm right) {
        this.condition = condition;
        this.sumType = sumType;
        this.leftVar = leftVar;
        this.left = left;
        this.rightVar = rightVar;
        this.right = right;
    }


    @Override
    public LTerm reduce() {

        if(condition.isReducible())
            return new Case(condition.reduce(), sumType.clone(), leftVar, left.clone(), rightVar, right.clone());

        if(condition instanceof InL) {
            InL condInl = (InL) condition;
            return left.replace(leftVar, condInl.term);
        }

        if(condition instanceof InR) {
            InR condInR = (InR) condition;
            return right.replace(rightVar, condInR.term);
        }

        throw new RuntimeException("something seriously wrong");
    }

    @Override
    public LTerm replace(String h, LTerm t) {
        return new Case(condition.replace(h, t), sumType.clone(), leftVar, left.replace(h, t), rightVar, right.replace(h, t));
    }

    @Override
    public LTerm clone() {
        return new Case(condition.clone(),
                        sumType.clone(),
                        leftVar,
                        left.clone(),
                        rightVar,
                        right.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set<String> ret = left.freeVariables();
        ret.remove(leftVar);
        ret.addAll(right.freeVariables());
        ret.remove(rightVar);
        ret.addAll(condition.freeVariables());
        return ret;
    }

    @Override
    public boolean isReducible() {
        return true;
    }


    /**
     * T-Case:
     *                    E|- t:T1+T2  E,(y:T1) |- t1:T   E,(z:T2) |-   t2: T
     *                    ==================================================
     *                    E|-case t inl T1+T2 y => t1 | inr T1+T2 z => t2: T
     */
    @Override
    public Type type_of(Environment e) {
        SumType cType = (SumType) condition.type_of(e);
        if(!cType.equals(sumType)) throw new RuntimeException("...echt kapott");

        Environment e1 = e.new_with(leftVar, sumType.left);
        Type t = left.type_of(e1);

        Environment e2 = e.new_with(rightVar, sumType.right);
        if(!(t.equals(right.type_of(e2))))
            throw new RuntimeException("Inl und Inr brauhen gleichen Typen");

        return t;


    }
}
