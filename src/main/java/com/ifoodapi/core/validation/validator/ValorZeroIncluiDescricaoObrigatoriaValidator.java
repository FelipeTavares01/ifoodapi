package com.ifoodapi.core.validation.validator;

import com.ifoodapi.core.validation.annotations.ValorZeroIncluiDescricaoObrigatoria;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public class ValorZeroIncluiDescricaoObrigatoriaValidator implements ConstraintValidator<ValorZeroIncluiDescricaoObrigatoria, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricaoObrigatoria constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objectValidacao, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidacao.getClass(), valorField)
                    .getReadMethod().invoke(objectValidacao);
            String descricao = (String) BeanUtils.getPropertyDescriptor(objectValidacao.getClass(), descricaoField)
                    .getReadMethod().invoke(objectValidacao);

            if(nonNull(valor,descricao) && isEqualZero(valor)) {
                valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valido;
    }

    private boolean isEqualZero(BigDecimal valor) {
        return BigDecimal.ZERO.compareTo(valor) == 0;
    }

    private boolean nonNull(BigDecimal valor, String descricao) {
        return Objects.nonNull(valor) && Objects.nonNull(descricao);
    }

}
