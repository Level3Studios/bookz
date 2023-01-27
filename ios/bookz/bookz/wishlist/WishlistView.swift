//
//  WishlistView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/27/23.
//

import SwiftUI

struct WishlistView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    @State private var searchText: String = ""
    @State private var showBookDetail: BooksModel?
    
    var body: some View {
        NavigationStack {
            VStack {
                if viewModel.wishlistBooks.isEmpty {
                    EmptyListView(viewName: "Wishlist")
                } else {
                    List {
                        ForEach(searchResults, id: \.self) { book in
                            BookModelListView(bookModel: book)
                                .swipeActions(allowsFullSwipe: false) {
                                    Button(action: {
                                        viewModel.deleteBook(book: book, forView: .wishlist)
                                    }, label: {
                                        Label("Delete", systemImage: "trash")
                                    }).tint(.red)
                                    Button(action: {
                                        viewModel.moveBook(book: book, toView: .library)
                                    }, label: {
                                        Label("Move to Library", systemImage: "building.columns")
                                    }).tint(.indigo)
                                }
                                .onTapGesture {
                                    self.showBookDetail = book
                                }
                        }
                    }
                }
            }
            .navigationTitle("My Wishlist")
            .searchable(text: $searchText, prompt: "Search your wishlist")
            .onAppear() {
                viewModel.fetchBooks(forView: .wishlist)
            }
            .sheet(item: $showBookDetail) { book in
                BookDetailView(book: book)
            }
        }
    }
    
    var searchResults: [BooksModel]  {
        if searchText.isEmpty {
            return viewModel.wishlistBooks
        } else {
            return viewModel.wishlistBooks.filter({ $0.volumeInfo?.title?.contains(searchText) ?? false})
        }
    }
}

struct WishlistView_Previews: PreviewProvider {
    static var previews: some View {
        WishlistView().environmentObject(NetworkViewModel())
    }
}
