package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;

import java.util.List;

public interface SwiftCodeService {
    MessageResponse  upsert(SwiftCodeRequest swiftCode);
    SwiftCodeDetailResponse getBySwiftCode(String swiftCode);
    SwiftCodeByCountryResponse getByCountryISO2(String iso2Code);
    MessageResponse deleteBySwiftCode(String swiftCode);
    boolean isDatabaseInitialized();
}
