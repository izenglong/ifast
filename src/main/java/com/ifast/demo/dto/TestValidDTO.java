package com.ifast.demo.dto;

import com.ifast.common.validation.ValidForm;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/9/4 16:49 | Aron</small>
 */
@ValidForm
@Data
public class TestValidDTO {

    @NotNull
    @Length(max = 20, min = 6)
    private String name;

    @NotNull
    @Range(min = 1, max = 120)
    private Integer age;

    @NotNull
    @Range(min = 1, max = 3)
    private Integer sex;
}

