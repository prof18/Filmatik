//
//  Koin.swift
//  Filmatik
//
//  Created by Marco Gomiero on 03.12.22.
//  Copyright © 2022. All rights reserved.
//

import Foundation
import shared

func startKoin() {
    
    var key = ""
    if let path = Bundle.main.path(forResource: "Info", ofType: "plist") {
        if let keys = NSDictionary(contentsOfFile: path) {
            key = keys["TMDBKey"] as? String  ?? ""
        }
    }
    
    let koinApplication = KoinIOSKt.doInitKoinIos(
        apiKey: ApiKey(key: key),
        deviceLocale: DeviceLocale(
            country: NSLocale().countryCode ?? "US",
            language: NSLocale().languageCode
        )
    )
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin?
var koin: Koin_coreKoin {
    return _koin!
}
