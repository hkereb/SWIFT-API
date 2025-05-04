package com.github.hkereb.swiftcodeapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SWIFT_CODES")
public class SwiftRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String swiftCode;
    private String codeType;
    private String bankName;
    private String bankAddress;
    private String countryIso2;
    private String country;
    private String timeZone;
    private Boolean isHeadquarters;
}
