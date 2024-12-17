package com.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShipInfo {
    private String firstName;
    private String lastName;
    private String zip;
}
