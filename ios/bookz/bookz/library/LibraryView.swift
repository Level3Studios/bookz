//
//  LibraryView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/23/23.
//

import SwiftUI

struct LibraryView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    @State private var searchText: String = ""
    
    var body: some View {
        NavigationStack {
            VStack {
                if viewModel.libraryBooks.isEmpty {
                    EmptyListView(viewName: "Library")
                } else {
                    List {
                        ForEach(searchResults, id: \.self) { book in
                            BookModelListView(bookModel: book)
                        }
                    }
                }
            }
            .navigationTitle("My Library")
            .searchable(text: $searchText, prompt: "Search your library")
        }
    }
    
    var searchResults: [BooksModel]  {
        if searchText.isEmpty {
            return viewModel.libraryBooks
        } else {
            return viewModel.libraryBooks.filter({ $0.volumeInfo?.title?.contains(searchText) ?? false})
        }
    }
}

struct LibraryView_Previews: PreviewProvider {
    static var previews: some View {
        LibraryView().environmentObject(NetworkViewModel())
    }
}
