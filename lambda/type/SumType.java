package lambda.type;

public class SumType extends Type {

    public final Type left;
    public final Type right;

    public SumType(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SumType))
            return false;
        SumType that = (SumType) o;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }

    @Override
    public SumType clone() {
        return new SumType(left.clone(), right.clone());
    }
}
