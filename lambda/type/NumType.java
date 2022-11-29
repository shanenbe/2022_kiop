package lambda.type;

public class NumType extends Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NumType;
    }

    @Override
    public Type clone() {
        return new NumType();
    }
}
