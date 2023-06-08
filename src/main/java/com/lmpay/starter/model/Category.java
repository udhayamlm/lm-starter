package com.lmpay.starter.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.io.Serializable;

/**
 * Author: udhayam
 * Date: 07/06/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Category.class)
public class Category implements Serializable {

    private String code;
    private String description;
}
