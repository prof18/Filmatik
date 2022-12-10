//
//  VIew+.swift
//  Filmatik
//
//  Created by Marco Gomiero on 07.12.22.
//  Copyright © 2022. All rights reserved.
//

import SwiftUI

extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape( RoundedCorner(radius: radius, corners: corners) )
    }
}
