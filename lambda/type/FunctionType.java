package lambda.type;

public class FunctionType extends Type {
    public final Type left;
    public final Type right;

    public FunctionType(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FunctionType))
            return false;
        FunctionType that = (FunctionType) obj;
        return this.left.equals(that.left) &&
                this.right.equals(that.right);
    }

    @Override
    public Type clone() {
        return new FunctionType(left.clone(), right.clone());
    }


    @Override
    public boolean is_Subtype_of(Type that) {

        if(this.equals(that)) return true;
        if(!(that instanceof FunctionType)) return false;

        FunctionType _that = (FunctionType) that;

        return _that.left.is_Subtype_of(this.left) &&
                this.right.is_Subtype_of(_that.right);

    }
}
