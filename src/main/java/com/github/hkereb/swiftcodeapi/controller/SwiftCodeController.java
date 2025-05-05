package com.github.hkereb.swiftcodeapi.controller;

import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;
import com.github.hkereb.swiftcodeapi.service.SwiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public SwiftCodeDetailResponse getSwiftByCode(@PathVariable String swiftCode) {
        return swiftCodeService.getBySwiftCode(swiftCode);
    }

    @GetMapping("/country/{countryISO2}")
    public SwiftCodeByCountryResponse getAllByCountryISO2(@PathVariable String countryISO2) {
        return swiftCodeService.getByCountryISO2(countryISO2);
    }

    @PostMapping
    public MessageResponse addSwiftRecord(@RequestBody SwiftCodeRequest request) {
        return swiftCodeService.upsert(request);
    }

    @DeleteMapping("/{swiftCode}")
    public MessageResponse deleteBySwiftCode(@PathVariable String swiftCode) {
        return swiftCodeService.deleteBySwiftCode(swiftCode);
    }
}
