//
//  Top5ListView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/20/23.
//

import SwiftUI

struct Top5ListView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    var books: [BooksModel]
    
    var body: some View {
        List {
            Section("Top 5 Books") {
                ForEach(books, id:\.self) { book in
                    BookModelListView(bookModel: book)
                }
            }
        }.listStyle(PlainListStyle())
    }
}

struct Top5ListView_Previews: PreviewProvider {
    static var previews: some View {
        Top5ListView(books: [BooksModel.sampleBook])
    }
}
