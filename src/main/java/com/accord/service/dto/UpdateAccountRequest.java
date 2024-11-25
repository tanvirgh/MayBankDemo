package com.accord.service.dto;


import lombok.Getter;
import lombok.Setter;


/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */

@Getter
@Setter
public class UpdateAccountRequest {

    private String description;
    private Long version;

}
