//
//  BookDetailView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/24/23.
//

import SwiftUI

struct BookDetailView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    var book: BooksModel?
    
    var body: some View {
        VStack(spacing: 12.0) {
            actionButtons
                .padding(.bottom, 24)
            bookImage
            bookTitle
            bookAuthors
            bookDescription
            Spacer()
        }
        .padding()
        .onAppear() {
            viewModel.fetchPreviewImage(fromBook: book)
        }
        .onDisappear() {
            viewModel.bookImage = nil
        }
    }
    
    var actionButtons: some View {
        HStack {
            Button(action: {
                //TODO: - Add action -
            }, label: {
                Label("+ Library", systemImage: "book")
            })
            Spacer()
            Button(action: {
                //TODO: - Add action -
            }, label: {
                Label("+ Wishlist", systemImage: "star")
            })
        }
    }
    
    @ViewBuilder
    var bookImage: some View {
        if let image = viewModel.bookImage {
            Image(uiImage: image)
                .resizable(resizingMode: .stretch)
                .frame(width: 200, height: 320)
        } else {
            Image(systemName: "photo.fill")
                .resizable()
                .foregroundColor(.gray)
                .frame(width: 320, height: 280)
        }
    }
    
    var bookTitle: some View {
        Text(book?.volumeInfo?.title ?? "")
            .font(.title)
            .fontWeight(.bold)
    }
    
    var bookAuthors: some View {
        HStack {
            Text(book?.volumeInfo?.authors ?? [""],
                 format: .list(type: .and))
                .font(.caption)
                .foregroundColor(.gray)
            publishedDate
        }
    }
    
    var bookDescription: some View {
        ScrollView(.vertical, showsIndicators: true) {
            Text(book?.volumeInfo?.description ?? "")
                .font(.subheadline)
                .foregroundColor(.gray)
        }
    }
    
    var publishedDate: some View {
        Text(book?.volumeInfo?.publishedDate.convertPublishedString() ?? Date(),
             formatter: DateFormatter.publishedDateFormatter)
            .font(.caption)
            .foregroundColor(.accentColor)
    }
}

struct BookDetailView_Previews: PreviewProvider {
    static var previews: some View {
        BookDetailView(book: BooksModel.sampleBook).environmentObject(NetworkViewModel())
    }
}
