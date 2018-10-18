package shared.primitive;

public interface HasValOperations {

    Val add(Val value);
    Val isAddedBy(Val value);

    Val divide(Val value);
    Val isDividedBy(Val value);

    Val multiply(Val value);
    Val isMultipliedBy(Val value);

    Val subtract(Val value);
    Val isSubtractedBy(Val value);

}
