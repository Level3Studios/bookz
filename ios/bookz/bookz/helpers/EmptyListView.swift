//
//  EmptyListView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/23/23.
//

import SwiftUI

struct EmptyListView: View {
    var viewName: String
    
    var body: some View {
        VStack(spacing: 32.0) {
            Image(systemName: "book")
                .resizable()
                .frame(width: 160, height: 120)
                .foregroundColor(.gray)
            Text("No Books in your \(viewName)")
                .foregroundColor(.gray)
                .fontWeight(.bold)
                .font(.title)
        }
    }
}

struct EmptyListView_Previews: PreviewProvider {
    static var previews: some View {
        EmptyListView(viewName: "Test View")
    }
}
