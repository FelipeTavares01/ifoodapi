package com.ifoodapi.core.validation.validator;

import com.ifoodapi.core.validation.annotations.FileContentType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(value) || this.allowedContentTypes.contains(value.getContentType());
    }
}
