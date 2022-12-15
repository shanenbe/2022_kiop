package lambda.type;

import java.sql.Ref;

public class RefType extends Type {

    public final Type elementType;

    public RefType(Type et) {
        elementType = et;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof RefType)) {
            return false;
        }

        RefType that = (RefType) o;
        return elementType.equals(that.elementType);
    }

    @Override
    public Type clone() {
        return new RefType(elementType.clone());
    }
}
