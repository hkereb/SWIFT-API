package com.github.hkereb.swiftcodeapi.controller;

import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;
import com.github.hkereb.swiftcodeapi.exceptions.MissingPathVariableException;
import com.github.hkereb.swiftcodeapi.service.SwiftCodeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private final SwiftCodeService swiftCodeService;

    public SwiftCodeController(SwiftCodeService swiftCodeService) { this.swiftCodeService = swiftCodeService; }

    @GetMapping("/{swiftCode}")
    public SwiftCodeDetailResponse getSwiftByCode(@PathVariable String swiftCode) {
        return swiftCodeService.getBySwiftCode(swiftCode);
    }
    @GetMapping("/")
    public SwiftCodeDetailResponse handleMissingSwiftCodeGetParam() throws MissingPathVariableException {
        throw new MissingPathVariableException("Parameter swiftCode is required for this path (e.g. v1/swift-codes/AAISALTRXXX)");
    }

    @GetMapping("/country/{countryISO2}")
    public SwiftCodeByCountryResponse getAllByCountryISO2(@PathVariable String countryISO2) {
        return swiftCodeService.getByCountryISO2(countryISO2);
    }
    @GetMapping("/country/")
    public SwiftCodeByCountryResponse handleMissingCountryParam() throws MissingPathVariableException {
        throw new MissingPathVariableException("Parameter countryISO2 is required for this path (e.g. v1/swift-codes/country/PL)");
    }

    @PostMapping("/")
    public MessageResponse addSwiftCode(@RequestBody SwiftCodeRequest request) {
        return swiftCodeService.upsert(request);
    }

    @DeleteMapping("/{swiftCode}")
    public MessageResponse deleteBySwiftCode(@PathVariable String swiftCode) {
        return swiftCodeService.deleteBySwiftCode(swiftCode);
    }
    @DeleteMapping("/")
    public MessageResponse handleMissingSwiftCodeDeleteParam() throws MissingPathVariableException {
        throw new MissingPathVariableException("Parameter swiftCode is required for this path (e.g. v1/swift-codes/AAISALTRXXX)");
    }
}
