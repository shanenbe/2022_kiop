package lambda.lterm;

import etc.Pair;
import lambda.Environment;
import lambda.type.RecordType;
import lambda.type.Type;

import java.util.*;

public class Record implements LTerm {

    public final List<Pair<String, LTerm>> elements = new ArrayList<>();

    public void addElement(String s, LTerm t) {
        elements.add(new Pair<>(s, t));
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Record))
            return false;

        Record _that = (Record) that;

        if(this.elements.size()!=_that.elements.size())
            return false;

        for(int i=0; i< elements.size(); i++) {
            if( elements.get(i) != _that.elements.get(i))
                return false;
        }

        return true;
    }

    @Override
    public Record clone() {
        Record ret = new Record();

        for(int i=0; i< elements.size(); i++) {
            ret.addElement(
                this.elements.get(i).left,
                this.elements.get(i).right.clone());
        }

        return ret;
    }


    /**
     *
     *     {l1.....ln} -> {.....li'.....)
     */

    public Record reduce() {

        Record ret = this.clone();

        for(int i=0; i< elements.size(); i++) {
            if(this.elements.get(i).right.isReducible()) {
                ret.elements.set(i,
                    new Pair<String, LTerm>(
                        this.elements.get(i).left,
                        this.elements.get(i).right.reduce())
                );
                return ret;
            }
        }

        throw new RuntimeException("Cannot reduce Record");

    }

    @Override
    public LTerm replace(String header, LTerm t) {

        Record ret = new Record();

        for(int i=0; i< elements.size(); i++) {
                ret.addElement(
                        this.elements.get(i).left,
                        this.elements.get(i).right.replace(header, t)
                        );
        }

        return ret;

    }

    @Override
    public Set<String> freeVariables() {
        HashSet<String> ret = new HashSet<>();

        for(int i=0; i< elements.size(); i++) {
            ret.addAll(elements.get(i).right.freeVariables());
        }

        return ret;
    }

    @Override
    public boolean isReducible() {
        for(int i=0; i< elements.size(); i++) {
            if(this.elements.get(i).right.isReducible()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public RecordType type_of(Environment e) {
        RecordType ret = new RecordType();

        for(int i=0; i< elements.size(); i++) {
            ret.addElement(
                this.elements.get(i).left,
                this.elements.get(i).right.type_of(e)
            );
        }

        return ret;

    }

    public LTerm get(String label) {
        for(Pair<String, LTerm> element: elements) {
            if(element.left.equals(label))
                return element.right;
        }
        throw new RuntimeException("dummy");
    }
}
