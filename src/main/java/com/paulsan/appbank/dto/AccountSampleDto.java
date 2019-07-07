package com.paulsan.appbank.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountSampleDto {
    @ApiModelProperty(value = "Account correlative id")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "Account number", readOnly = true)
    @Size(min = 13, max = 13, message = "The length of the account must have 13 characters.")
    private String number;

    public AccountSampleDto(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
