package lambda.type;

public abstract class Type {
    public abstract boolean equals(Object o);
    public abstract Type clone();

    public boolean is_Subtype_of(Type t) {
        return this.equals(t);
    }
}
