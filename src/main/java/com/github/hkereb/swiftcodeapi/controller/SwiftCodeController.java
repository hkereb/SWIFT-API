package com.github.hkereb.swiftcodeapi.controller;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.service.SwiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public SwiftCode getSwiftByCode(@PathVariable String swiftCode) {
        return swiftCodeService.getBySwiftCode(swiftCode);
    }

    @GetMapping("/country/{countryISO2}")
    public List<SwiftCode> getAllByCountryISO2(@PathVariable String countryISO2) {
        return swiftCodeService.getByCountryISO2(countryISO2);
    }

    @PostMapping
    public String addSwiftRecord(@RequestBody SwiftCode swiftCode) {
        return swiftCodeService.upsert(swiftCode);
    }

    @DeleteMapping("/{swiftCode}")
    public Map<String, String> deleteBySwiftCode(@PathVariable String swiftCode) {
        String message = swiftCodeService.deleteBySwiftCode(swiftCode);
        return Map.of("message", message);
    }

}
