package com.ruoyi.common.core.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountryTbl implements Serializable {
    /**
     *
     */
    private Integer countryId;

    /**
     *
     */
    private String englishName;

    /**
     *
     */
    private String chineseName;

    /**
     *
     */
    private String twoDigitAbbr;

    /**
     *
     */
    private String threeDigitAbbr;

    /**
     *
     */
    private Integer areaCode;

    private static final long serialVersionUID = 1L;
}