package tests;

import junit.framework.TestCase;
import lambda.Environment;
import lambda.lterm.*;
import lambda.lterm.Record;
import lambda.lterm.literal.*;
import lambda.lterm.sumtype.Case;
import lambda.lterm.sumtype.InL;
import lambda.type.*;

public class Test_TermReduction extends TestCase {


    /**
     *
     * (Lp:{x:Num, y:Num}. p.x) {x=42, y=666} -> 42
     * (Lp:{x:Num, y:Num}. p.x) {x=42, y=666} : Num
     *
     */
    public void test_record_01() {
        Record r = Record("x", 42, "y", 666);
        Abstraction abs =
                Abs("p", RecordType("x", Num(), "y", Num()),
                        Proj(Var("p"), "x")
                );
        Application app = App(abs, r);
        LTerm result = app.reduceAll();
        assertEquals(result, Num(42));

    }

    private Projection Proj(LTerm p, String s) {
        return new Projection(p, s);
    }

    private RecordType RecordType(String x, NumType num) {
        return new RecordType(x, num);
    }

    private RecordType RecordType(String x, NumType num, String y, NumType num1) {
        return new RecordType(x, num, y, num1);
    }

    private RecordType RecordType(String x, NumType num, String y, NumType num1, String z, NumType num2) {
        return new RecordType(x, num, y, num1, z, num2);
    }


    public void test_iif() {
        Iif iif = Iif(True(), True(), Num(42));
        assertEquals(SumType(Bool(), Num()), iif.type_of(E()));
        assertEquals(Inl(SumType(Bool(), Num()), True()), iif.reduce());
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

    private Application App(LTerm abstraction, LTerm variable) {
        return new Application(abstraction, variable);
    }

    private Abstraction Abs(String x, Type type, LTerm t) {
        return new Abstraction(x, type, t);
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

    private Record Record(String k, int v, String k2, int v2) {
        return new Record(k, Num(v), k2, Num(v2));
    }

    private Record Record(String k, int v, String k2, int v2, String k3, int v3) {
        return new Record(k, Num(v), k2, Num(v2), k3, Num(v3));
    }

    private Record Record(String k, int v) {
        return new Record(k, Num(v));
    }

    private Record Record(String k, LTerm v) {
        return new Record(k, v);
    }

    public InL Inl(SumType st, LTerm t) {
        return new InL(st, t);
    }

    private Environment E() {
        return new Environment();
    }
}
