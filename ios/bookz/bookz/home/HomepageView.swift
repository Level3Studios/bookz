//
//  HomepageView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/21/23.
//

import SwiftUI

struct HomepageView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    @State private var openSearch: Bool = false
    
    var body: some View {
        VStack {
            HStack {
                Spacer()
                Button(action: {
                    openSearch = true
                }, label: {
                    Label("Search", systemImage: "magnifyingglass")
                }).tint(.accentColor)
            }
            GenreTabView()
                .frame(height: 220)
            Top5ListView(books: viewModel.top5Books)
        }
        .padding()
        .sheet(isPresented: $openSearch) {
            SearchView()
        }
    }
}

struct HomepageView_Previews: PreviewProvider {
    static var previews: some View {
        HomepageView().environmentObject(NetworkViewModel())
    }
}
