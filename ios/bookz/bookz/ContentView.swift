//
//  ContentView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/13/23.
//

import SwiftUI
import Alamofire

struct ContentView: View {
    @StateObject var viewModel = NetworkViewModel()
    
    var body: some View {
        TabView {
            HomepageView().tabItem({
                Label("Home", systemImage: "house")
            })
            LibraryView().tabItem({
                Label("Library", systemImage: "building.columns")
            })
            WishlistView().tabItem({
                Label("Wishlist", systemImage: "star")
            })
        }
        .environmentObject(viewModel)
        .onAppear {
            viewModel.updateSelectedGenre(genre: .adventure)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) {
            ContentView().preferredColorScheme($0)
        }
    }
}
