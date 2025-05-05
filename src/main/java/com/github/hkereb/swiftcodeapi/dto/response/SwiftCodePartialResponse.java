package com.github.hkereb.swiftcodeapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "address",
        "bankName",
        "countryISO2",
        "isHeadquarter",
        "swiftCode",
})
public class SwiftCodePartialResponse {
    private String address;
    private String bankName;
    private String countryISO2;

    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;

    private String swiftCode;
}
