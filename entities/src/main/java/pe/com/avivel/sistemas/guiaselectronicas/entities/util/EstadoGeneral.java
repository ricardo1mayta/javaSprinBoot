package pe.com.avivel.sistemas.guiaselectronicas.entities.util;

import lombok.experimental.FieldNameConstants;

@SuppressWarnings("unused")
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum EstadoGeneral {
    @FieldNameConstants.Include T, // Campo para traer todos los estados
    @FieldNameConstants.Include A,
    @FieldNameConstants.Include I;

    public String toString(String negative, String positive) {
        if(this.toString().equals(T.toString())){
            return null;
        }

        return this.toString().equals(A.toString()) ? positive : negative;
    }

    public boolean toBoolean(){
        return this.toString().equals(A.toString());
    }
}
