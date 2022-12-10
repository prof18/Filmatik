//
//  ErrorView.swift
//  Filmatik
//
//  Created by Marco Gomiero on 03.12.22.
//  Copyright © 2022. All rights reserved.
//

import SwiftUI
import shared

struct ErrorView: View {
    
    let errorData: ErrorData
    let onRetryClick: (() -> Void)?
    
    var body: some View {
        VStack {
            Spacer()
            
            Text(errorData.message)
            
            if let onRetry = onRetryClick {
                Button(action: onRetry) {
                    Text(errorData.buttonText)
                }
            }
            
            Spacer()
        }
    }
}
