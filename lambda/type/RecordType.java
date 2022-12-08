package lambda.type;

import etc.Pair;
import lambda.lterm.LTerm;
import lambda.lterm.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordType extends Type {

    public final List<Pair<String, Type>> elements = new ArrayList<>();

    public RecordType() {}

    public RecordType(String k, NumType v) {
        addElement(k, v);
    }

    public RecordType(String k1, NumType v1, String k2, NumType v2) {
        addElement(k1, v1);
        addElement(k2, v2);
    }

    public RecordType(String k1, NumType v1, String k2, NumType v2, String k3, NumType v3) {
        addElement(k1, v1);
        addElement(k2, v2);
        addElement(k3, v3);
    }

    public void addElement(String s, Type t) {
        elements.add(new Pair<>(s, t));
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof RecordType))
            return false;

        RecordType _that = (RecordType) that;

        if(this.elements.size()!=_that.elements.size())
            return false;

        for(int i=0; i< elements.size(); i++) {
            if( elements.get(i) != _that.elements.get(i))
                return false;
        }

        return true;
    }



    @Override
    public Type clone() {
        RecordType ret = new RecordType();

        for(Pair<String, Type> element: elements) {
            ret.addElement(element.left, element.right.clone());
        }

        return ret;
    }

    @Override
    public boolean is_Subtype_of(Type _that) {
        if(this.equals(_that)) return true;
        if(!(_that instanceof RecordType)) return false;

        RecordType that = (RecordType) _that;

        for(int i=0; i<that.elements.size(); i++) {
            String label = that.elements.get(i).left;
            if(this.has_label(label)) {
              if(!this.get(label).is_Subtype_of(that.get(label))) {
                  return false;
              }
            } else {
                return false;
            }
        }

        return true;

    }

    public Type get(String label) {
        for(Pair<String, Type> element: elements) {
            if(element.left.equals(label))
                return element.right;
        }
        throw new RuntimeException("dummy");
    }

    public boolean has_label(String label) {
        for(Pair<String, Type> element: elements) {
            if(element.left.equals(label))
                return true;
        }
        return false;
    }

}
