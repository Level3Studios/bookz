//
//  GenreType.swift
//  bookz
//
//  Created by Aldana, Sal on 1/18/23.
//

import Foundation
import SwiftUI
import DynamicColor

enum GenreType: Int, CaseIterable {
    case adventure, fantasy,
         scifi, romance
    
    var displayLabel: String {
        switch self {
        case .adventure:
            return "Adventure"
        case .fantasy:
            return "Fantasy"
        case .scifi:
            return "Sci-Fi"
        case .romance:
            return "Romance"
        }
    }
    
    var searchString: String {
        var option: String
        switch self {
        case .adventure:
            option = "adventure"
        case .fantasy:
            option = "fantasy"
        case .scifi:
            option = "science%20fiction"
        case .romance:
            return "romance"
        }
        return "+subject:\(option)"
    }
    
    var primaryColor: Color {
        switch self {
        case .adventure:
            return DynamicColor(hexString: "68e1fd").swiftColor
        case .fantasy:
            return DynamicColor(hexString: "fca868").swiftColor
        case .scifi:
            return DynamicColor(hexString: "68fc78").swiftColor
        case .romance:
            return DynamicColor(hexString: "fc68d7").swiftColor
        }
    }
    
    var gradient: LinearGradient {
        switch self {
        case .adventure:
            return self.primaryColor.topDownGradient
        case .fantasy:
            return self.primaryColor.leftRightGradient
        case .scifi:
            return self.primaryColor.bottomUpGradient
        case .romance:
            return self.primaryColor.rightLeftGradient
        }
    }
    
    var imageName: String {
        switch self {
        case .adventure:
            return "Adventure"
        case .fantasy:
            return "Fortnite"
        case .scifi:
            return "Alien"
        case .romance:
            return "Couple"
        }
    }
}
