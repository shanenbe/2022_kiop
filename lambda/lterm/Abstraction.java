package lambda.lterm;

import lambda.Environment;
import lambda.Memory;
import lambda.type.FunctionType;
import lambda.type.Type;

import java.util.Objects;
import java.util.Set;

/**
 * ToDo: vollständige Betaruduktion vs. CallByValue
 */
public class Abstraction extends Function {

    public final String header;
    public final LTerm body;
    public final Type header_type;


    public Abstraction(String header, Type header_type, LTerm body) {
        this.header = header;
        this.header_type = header_type;
        this.body = body;
    }

    @Override
    public LTerm reduce(Memory memory) {
        throw new RuntimeException("Reduzier keine Abstraction");
    }

    public boolean isFunction() {
        return true;
    }

    @Override
    /* [x := t1] (Ly.t2) -> Ly.[x := t1] t2,
                                             wenn y !=x und
                                             y nicht Element von FI(t1)
    */
    public LTerm replace(String v, LTerm t) {

        if(v.equals(this.header)) {

            return this.clone();

        } else if (t.freeVariables().contains(this.header)) {
            Abstraction converted = this;
            while(t.freeVariables().contains(converted.header)) {
                converted.alpha_convert();
            }

            return new Abstraction(converted.header,header_type.clone(), converted.body.replace(v, t));

        }

        return new Abstraction(header, header_type.clone(), body.replace(v, t));

//        throw new RuntimeException("wtf");
    }

    private Abstraction alpha_convert() {
        return new Abstraction(header + "'", header_type.clone(), body.replace(
                header, new Variable(header + "'")));
    }

    //    (Lx.body) t -> [x := t] body (.....für Nebenbedingungen)
    public LTerm apply(LTerm t) {
        return body.replace(header, t);
    }

    public Abstraction clone() {
        return new Abstraction(header, header_type.clone(), body.clone());
    }

    @Override
    public Set<String> freeVariables() {
        Set ret = body.freeVariables();

        if(ret.contains(header)) {
            ret.remove(header);
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abstraction that = (Abstraction) o;
        return header.equals(that.header) && body.equals(that.body);
    }

    @Override
    public boolean isReducible() {
        return false;
    }

    /*
    T-App:
                     E,(x:T)|-t:T'
               =======================
                    E |- Lx:T.t : T->T'
     */

    @Override
    public Type type_of(Environment e) {
        Environment e_new = e.new_with(this.header, this.header_type);
        return new FunctionType(header_type.clone(), this.body.type_of(e_new));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, body);
    }
}
