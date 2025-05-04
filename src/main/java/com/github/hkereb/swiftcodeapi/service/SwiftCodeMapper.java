package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeResponse;

import java.util.*;
import java.util.stream.Collectors;

public class SwiftCodeMapper {

    public static SwiftCode mapToEntity(SwiftCodeRequest request) {
        SwiftCode code = new SwiftCode();

        code.setSwiftCode(request.getSwiftCode());
        code.setBankName(request.getBankName());
        code.setBankAddress(request.getAddress());
        code.setCountryISO2(request.getCountryISO2());
        code.setCountry(request.getCountryName());
        code.setIsHeadquarter(request.isHeadquarter());

        // omitted in request
        code.setCodeType(null);
        code.setTimeZone(null);

        return code;
    }

    public static SwiftCodeResponse mapToResponse(SwiftCode entity, List<SwiftCodeResponse> branches) {
        return new SwiftCodeResponse(
                entity.getBankAddress(),
                entity.getBankName(),
                entity.getCountryISO2(),
                entity.getCountry(),
                Boolean.TRUE.equals(entity.getIsHeadquarter()),
                entity.getSwiftCode(),
                branches
        );
    }

    public static SwiftCodeResponse mapToResponse(SwiftCode entity) {
        return mapToResponse(entity, Collections.emptyList());
    }

    public static List<SwiftCodeResponse> mapToResponseList(List<SwiftCode> codes) {
        return codes.stream()
                .map(SwiftCodeMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}