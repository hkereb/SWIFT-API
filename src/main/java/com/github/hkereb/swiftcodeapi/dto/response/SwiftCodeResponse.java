package com.github.hkereb.swiftcodeapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "address",
        "bankName",
        "countryISO2",
        "countryName",
        "isHeadquarter",
        "swiftCode",
        "branches"
})
public class SwiftCodeResponse {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;

    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;

    private String swiftCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SwiftCodeResponse> branches;
}
