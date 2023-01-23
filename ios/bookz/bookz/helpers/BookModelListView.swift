//
//  BookModelListView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/20/23.
//

import SwiftUI

struct BookModelListView: View {
    var bookModel: BooksModel
    let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "YYYY"
        return formatter
    }()
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8.0) {
            Text(bookModel.volumeInfo?.title ?? "")
                .bold()
            HStack {
                authorList
                publishedDate
            }
            ratingsStack
            Text(bookModel.volumeInfo?.description ?? "")
                .lineLimit(3, reservesSpace: true)
        }
    }
    
    var authorList: some View {
        Text(bookModel.volumeInfo?.authors ?? [""], format: .list(type: .and))
            .foregroundColor(.gray)
            .fontWeight(.bold)
    }
    
    var publishedDate: some View {
        Text(bookModel.volumeInfo?.publishedDate.convertPublishedString() ?? Date(), formatter: self.dateFormatter)
            .foregroundColor(.accentColor)
    }
    
    var ratingsStack: some View {
        HStack {
            Text(String(format: "%.1f", (bookModel.volumeInfo?.averageRating ?? 0.0)))
                .foregroundColor(.gray)
            Image(systemName: "star.fill")
                .foregroundColor(.yellow)
            Text("(\(bookModel.volumeInfo?.ratingsCount ?? 0))")
                .foregroundColor(.gray)
        }
    }
}

struct BookModelListView_Previews: PreviewProvider {
    static var previews: some View {
        BookModelListView(bookModel: BooksModel.sampleBook)
    }
}
