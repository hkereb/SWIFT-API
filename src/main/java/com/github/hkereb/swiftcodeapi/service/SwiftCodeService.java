package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeResponse;

import java.util.List;

public interface SwiftCodeService {
    // TODO cleanup between service and repo
    MessageResponse  upsert(SwiftCodeRequest swiftCode);
    SwiftCodeResponse getBySwiftCode(String swiftCode);
    List<SwiftCodeResponse> getByCountryISO2(String iso2Code);
    MessageResponse deleteBySwiftCode(String swiftCode);
    //List<SwiftCode> getBranchesBySwiftCode(String swiftCode);
    boolean isDatabaseInitialized();
}
