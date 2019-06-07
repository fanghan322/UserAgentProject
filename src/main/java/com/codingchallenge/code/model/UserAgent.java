package com.codingchallenge.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Data model class user agent
 * @author fang
 */
@Setter
@Getter
@ToString
public class UserAgent {

    private String id;
    private String country;
    private String ua;
    private String f1;
    private String f2;
    private String f3;
}
