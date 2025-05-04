package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeResponse;

import java.util.List;

public interface SwiftCodeService {
    MessageResponse  upsert(SwiftCodeRequest swiftCode);
    String upsertAll(List<SwiftCode> records);

    SwiftCode getById(Integer id);
    SwiftCodeResponse getBySwiftCode(String swiftCode);
    List<SwiftCode> getAllSwiftRecords();
    List<SwiftCodeResponse> getByCountryISO2(String iso2Code);

    String deleteById(Integer id);
    MessageResponse deleteBySwiftCode(String swiftCode);
    List<SwiftCode> getBranchesBySwiftCode(String swiftCode);
    boolean isDatabaseInitialized();
}
