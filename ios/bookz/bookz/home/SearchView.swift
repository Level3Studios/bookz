//
//  SearchView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/27/23.
//

import SwiftUI

struct SearchView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    @State private var searchText = ""
    @State private var showBookDetail: BooksModel?
    
    var body: some View {
        NavigationStack {
            List {
                ForEach(viewModel.searchResults, id: \.self) { book in
                    BookModelListView(bookModel: book)
                        .onTapGesture {
                            self.showBookDetail = book
                        }
                }
            }
        }
        .searchable(text: $searchText, prompt: "Search for books")
        .onSubmit(of: .search) {
            viewModel.searchForBooks(withQuery: searchText)
        }
        .sheet(item: $showBookDetail) { book in
            BookDetailView(book: book)
        }
        
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView().environmentObject(NetworkViewModel())
    }
}
