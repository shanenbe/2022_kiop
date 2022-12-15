package lambda;

import lambda.lterm.LTerm;
import lambda.lterm.ref.Address;

import java.util.ArrayList;
import java.util.List;

public class Memory {

    List<LTerm> list = new ArrayList<>();

    public Address create_ref_in_memory(LTerm t) {
        list.add(t);
        return new Address(list.size()-1);
    }

    public LTerm dereference(Address a) {
        return list.get(a.index);
    }

    public void assign(Address a, LTerm t) {
        list.set(a.index, t);
    }

}
