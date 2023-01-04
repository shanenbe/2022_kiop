package lambda.type;
public class UnitType extends Type{
    @Override
    public boolean equals(Object o) {
        return o instanceof UnitType;
    }
    @Override
    public Type clone() {
        return new UnitType();
    }
}
