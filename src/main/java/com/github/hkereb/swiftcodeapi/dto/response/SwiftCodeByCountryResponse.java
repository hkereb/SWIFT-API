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
        "countryISO2",
        "countryName",
        "swiftCodes",
})
public class SwiftCodeByCountryResponse {
    private String countryISO2;
    private String countryName;

    private List<SwiftCodePartialResponse> swiftCodes;
}
