package lambda.lterm;

import java.util.Set;

public abstract class Function implements LTerm {
    @Override
    public LTerm reduce() {
        return null;
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public LTerm replace(String header, LTerm t) {
        return null;
    }

    @Override
    public LTerm clone() {
        return null;
    }

    @Override
    public Set<String> freeVariables() {
        return null;
    }

    public abstract LTerm apply(LTerm t);
}
