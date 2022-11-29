package tests;

import junit.framework.TestCase;
import lambda.Environment;
import lambda.lterm.*;
import lambda.lterm.literal.*;
import lambda.lterm.literal.sumtype.Case;
import lambda.type.BoolType;
import lambda.type.NumType;
import lambda.type.SumType;
import lambda.type.Type;

public class Test_TermReduction extends TestCase {



    public void test_iif() {
        Iif iif = Iif(True(), True(), Num(42));
        assertEquals(SumType(Bool(), Num()), iif.type_of(E()));
        assertEquals(True(), iif.reduce());
        Case _case = Case(iif, SumType(Bool(), Num()), "a", True(), "b", True() );
        assertEquals(True(), _case.reduce().reduce());




    }


    /**
     * if(true) then 23 else 42 -> 23
     * if(true) then 23 else 42: Num
     * if(false) then 23 else 42 -> 42
     * if(true) then 23 else 42: Num
     *
     * if(true) then 23 else true: Fehler
     */
    public void test_if() {
        If IF = If(True(), Num(23), Num(42));
        assertEquals(Num(), IF.type_of(E()));
        assertEquals(Num(23), IF.reduce());

        IF = If(False(), Num(23), Num(42));
        assertEquals(Num(), IF.type_of(E()));
        assertEquals(Num(42), IF.reduce());

        IF = If(Not(True()), Num(23), Num(42));
        assertEquals(Num(), IF.type_of(E()));
        assertEquals(If(False(), Num(23), Num(42)), IF.reduce());
        assertEquals(Num(42), IF.reduce().reduce());
    }



//    // (+ 1) 1 -> 2
//    public void test_05_plus_1_1() {
//        LTerm application = App(App(Plus(), Num(1)),Num(1));
//        LTerm result = application.reduceAll();
//        assertEquals( Num(2), result);
//    }



    // not (not false) -> false
    public void test_04_not_not_false() {
        LTerm application = App(Not(),App(Not(), False()));
        LTerm result = application.reduceAll();
        assertEquals(result, False());
    }

    // not false -> true
    public void test_03_not_false() {
        Not n = new Not();
        False t = False();
        LTerm application = App(n, t);

        LTerm result = application.reduce();

        assertEquals(result, True());
    }

    // not true -> false
    public void test_02_not_true() {
        Not n = Not();
        True t = True();
        LTerm application = App(n, t);

        LTerm result = application.reduce();

        assertEquals(result, False());
    }


    // (Lx.x) x -> x
//    public void test_01_apply_var_to_abstraction() {
//        LTerm variable = Var("x");
//        LTerm abstraction = Abs("x", Var("x"));
//        LTerm application = App(abstraction, variable); // (Lx.x) x
//
//        LTerm result = application.reduce();
//        LTerm expected_result = Var("x");
//
//        assertEquals(result, expected_result);
//
//    }

    private LTerm App(LTerm abstraction, LTerm variable) {
        return new Application(abstraction, variable);
    }

    private LTerm Abs(String x, Type type, LTerm t) {
        return new Abstraction("x", type, t);
    }


    private LTerm Var(String varname) {
        return new Variable(varname);
    }

    private False False() { return new False();}
    private True True() { return new True();}
    private BoolType Bool() { return new BoolType();}
    private Not Not() { return new Not();}
    private If If(LTerm c, LTerm t, LTerm e) { return new If(c, t, e);}
    private Iif Iif(LTerm c, LTerm t, LTerm e) { return new Iif(c, t, e);}

    private Application Not(LTerm t) { return new Application(Not(), t);}

    private LTerm Num(int i) {
        return new NumberTerm(i);
    }

    private SumType SumType(Type left, Type right) {
        return new SumType(left, right);
    }

    private Case Case(LTerm condition, SumType sumType, String leftVar, LTerm left, String rightVar, LTerm right) {
        return new Case(condition, sumType, leftVar, left, rightVar, right);
    }

    private NumType Num() {
        return new NumType();
    }

    private LTerm Plus() {
        return new Plus();
    }


    private Environment E() {
        return new Environment();
    }
}
