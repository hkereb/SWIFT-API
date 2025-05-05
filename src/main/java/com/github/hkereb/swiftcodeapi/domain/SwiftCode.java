package com.github.hkereb.swiftcodeapi.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "SWIFT_CODES")
public class SwiftCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String swiftCode;

    private String codeType;

    @NotNull
    @Column(nullable = false)
    private String bankName;

    private String bankAddress;

    @NotNull
    @Column(nullable = false)
    private String countryISO2;

    @NotNull
    @Column(nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String townName;

    @Column(nullable = false)
    private String timeZone;

    @NotNull
    @Column(nullable = false)
    private Boolean isHeadquarter;
}
