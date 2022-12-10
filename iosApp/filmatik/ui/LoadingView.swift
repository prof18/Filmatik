//
//  LoadingView.swift
//  Filmatik
//
//  Created by Marco Gomiero on 10.12.22.
//  Copyright © 2022. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        VStack {
            Spacer()
            ProgressView()
            Spacer()
        }
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
