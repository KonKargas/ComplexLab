
package com.kkargas.complexlab.dto;

import com.kkargas.complexlab.enums.CalculationOperation;
import com.kkargas.complexlab.model.ComplexNumber;

public record ComplexCalculationResponse(

    ComplexNumberDTO first,
    ComplexNumberDTO second,
    CalculationOperation operation,
    ComplexNumberDTO result,
    String formattedResult

) {

    public static ComplexCalculationResponse of(
            ComplexNumber first,
            ComplexNumber second,
            CalculationOperation operation,
            ComplexNumber result) {

        return new ComplexCalculationResponse(
            ComplexNumberDTO.fromModel(first),
            ComplexNumberDTO.fromModel(second),
            operation,
            ComplexNumberDTO.fromModel(result),
            result.toString()
        );
    }
}
