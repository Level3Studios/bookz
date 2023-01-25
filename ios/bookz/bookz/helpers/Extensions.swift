//
//  Extensions.swift
//  bookz
//
//  Created by Aldana, Sal on 1/16/23.
//

import Foundation
import SwiftUI
import DynamicColor

//MARK: - Color Extensions -
public extension Color {
    
    var topDownGradient: LinearGradient {
        return self.createLinearGradient(startPoint: .top, endPoint: .bottom)
    }
    
    var bottomUpGradient: LinearGradient {
        return self.createLinearGradient(startPoint: .bottom, endPoint: .top)
    }
    
    var leftRightGradient: LinearGradient {
        return self.createLinearGradient(startPoint: .leading, endPoint: .trailing)
    }
    
    var rightLeftGradient: LinearGradient {
        return self.createLinearGradient(startPoint: .trailing, endPoint: .leading)
    }
    
    private func createLinearGradient(startPoint: UnitPoint, endPoint: UnitPoint) -> LinearGradient {
        let color = UIColor(self)
        let gradient = Gradient(colors: [color.darkened().swiftColor, self, color.lighter(amount: 0.1).swiftColor])
        return LinearGradient(gradient: gradient, startPoint: startPoint, endPoint: endPoint)
    }
}

//MARK: - UIColor Extensions -
public extension UIColor {
    
    var swiftColor: Color {
        return Color(self)
    }
}

//MARK: - DateFormatter Extensions -
public extension String? {
    
    func convertPublishedString() -> Date {
        guard let date = self else {
            return Date()
        }
        let formatter = DateFormatter()
        formatter.dateFormat = "YYYY-MM-dd"
        return formatter.date(from: date) ?? Date()
    }
}

public extension DateFormatter {
    
    static let publishedDateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "YYYY"
        return formatter
    }()
}
