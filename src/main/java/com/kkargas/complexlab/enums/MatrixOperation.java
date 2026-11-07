package com.kkargas.complexlab.enums;

public enum MatrixOperation {
    ADD("Add A + B", true),
    SUBTRACT("Subtract A - B", true),
    MULTIPLY("Multiply A × B", true),
    SCALAR_MULTIPLY("Scalar × A", false),
    TRANSPOSE("Transpose A", false),
    CONJUGATE_TRANSPOSE("Conjugate transpose A", false),
    DETERMINANT("Determinant of A", false),
    TRACE("Trace of A", false),
    INVERSE("Inverse of A", false),
    POWER("Power A^n", false),
    IDENTITY("Identity matrix (size from A)", false),
    RANDOM("Random matrix (dimensions from A)", false);

    private final String label;
    private final boolean needsB;

    MatrixOperation(String label, boolean needsB) {
        this.label = label;
        this.needsB = needsB;
    }

    public String getLabel() { return label; }
    public boolean isNeedsB() { return needsB; }
}
