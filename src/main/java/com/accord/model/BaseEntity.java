package com.accord.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * User : Tanvir Ahmed
 * Date: 11/20/2024.
 */
@Getter
@Setter
@MappedSuperclass

public abstract class BaseEntity implements Serializable {


    @Serial
    private static final long serialVersionUID = -5976049577527247385L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Version
    @Column(name = "version")
    protected Long version = 0L;


}