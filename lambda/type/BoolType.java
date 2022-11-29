package lambda.type;

public class BoolType extends Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public Type clone() {
        return new BoolType();
    }
}
