//
//  NetworkViewModel.swift
//  bookz
//
//  Created by Aldana, Sal on 1/15/23.
//

import Foundation
import AlamofireImage
import SwiftUI
import CoreData

class NetworkViewModel: ObservableObject {
    // MARK: - Private variables
    private let networkLayer = NetworkProtocol.shared
    private let dbController = DatabaseController.shared
    private var fantasyBooks: [BooksModel] = []
    private var adventureBooks: [BooksModel] = []
    private var romanceBooks: [BooksModel] = []
    private var scifiBooks: [BooksModel] = []
    
    // MARK: - Published variables
    @Published var top5Books: [BooksModel] = []
    @Published var libraryBooks: [BooksModel] = []
    @Published var wishlistBooks: [BooksModel] = []
    @Published var searchResults: [BooksModel] = []
    @Published var bookImage: UIImage?
    @Published var bookSaved: Bool = false
    
    //MARK: - Helpers
    public enum BookStoreType: Int, CaseIterable {
        case library, wishlist
        
        var label: String {
            switch self {
            case .library:
                return "Library"
            case .wishlist:
                return "Wishlist"
            }
        }
    }
    
    //MARK: - Genre Tab
    private func needsSearch(genre: GenreType) -> Bool {
        var needsSearch: Bool = true
        switch genre {
        case .adventure:
            needsSearch = adventureBooks.isEmpty
        case .fantasy:
            needsSearch = fantasyBooks.isEmpty
        case .scifi:
            needsSearch = scifiBooks.isEmpty
        case .romance:
            needsSearch = romanceBooks.isEmpty
        }
        return needsSearch
    }
    
    public func updateSelectedGenre(genre: GenreType) {
        guard self.needsSearch(genre: genre) else {
            self.updateTop5List(forGenre: genre)
            return
        }
        networkLayer.searchForBooks(withQuery: genre.searchString, maxResults: 5, completion: { response in
            switch response {
            case .success(let books):
                self.updateBooks(response: books.items, genre: genre)
            case .failure(let error):
                print(error.localizedDescription)
            }
        })
    }
    
    private func updateBooks(response: [BooksModel], genre: GenreType) {
        switch genre {
        case .adventure:
            self.adventureBooks = response
        case .fantasy:
            self.fantasyBooks = response
        case .scifi:
            self.scifiBooks = response
        case .romance:
            self.romanceBooks = response
        }
        self.updateTop5List(forGenre: genre)
    }
    
    private func updateTop5List(forGenre genre: GenreType) {
        switch genre {
        case .adventure:
            self.top5Books = adventureBooks
        case .fantasy:
            self.top5Books = fantasyBooks
        case .scifi:
            self.top5Books = scifiBooks
        case .romance:
            self.top5Books = romanceBooks
        }
    }
    
    //MARK: - Images
    func fetchPreviewImage(fromBook book: BooksModel?)  {
        guard let bookId = book?.id else { return }
        networkLayer.fetchImage(forBook: bookId) { response in
            switch response {
            case .success(let image):
                self.bookImage = image
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    
    //MARK: - Library & Wishlist View Methods
    func fetchBooks(forView view: BookStoreType) {
        let context = self.dbController.container.viewContext
        let fetchRequest = SavedBooks.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "type = %@", view.label)
        let books = try? context.fetch(fetchRequest)
        books?.forEach({ book in
            networkLayer.fetchBook(forId: book.id ?? "") { response in
                switch response {
                case .success(let book):
                    self.updateView(view: view, withBook: book)
                case .failure(let error):
                    print(error.localizedDescription)
                }
            }
        })
    }
    
    func updateView(view: BookStoreType, withBook book: BookResponse) {
        let model = book.convertToModel()
        switch view {
        case .library:
            if !self.libraryBooks.contains(where: { $0.id == model.id}) {
                self.libraryBooks.append(model)
            }
        case .wishlist:
            if !self.wishlistBooks.contains(where: { $0.id == model.id}) {
                self.wishlistBooks.append(model)
            }
        }
    }
    
    
    //MARK: - Database Helpers
    func isBookSaved(book: BooksModel?) {
        let context = self.dbController.container.viewContext
        let fetchRequest = SavedBooks.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id = %@", (book?.id ?? ""))
        let books = try? context.fetch(fetchRequest)
        self.bookSaved = (books?.count ?? 0) > 0
    }
    
    func saveBook(book: BooksModel?, forView view: BookStoreType) {
        let context = self.dbController.container.viewContext
        let newBook = SavedBooks(context: context)
        newBook.id = book?.id
        newBook.type = view.label
        try? context.save()
        self.isBookSaved(book: book)
    }
    
    func moveBook(book: BooksModel?, toView view: BookStoreType) {
        let context = self.dbController.container.viewContext
        let fetchRequest = SavedBooks.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id = %@", (book?.id ?? ""))
        let books = try? context.fetch(fetchRequest)
        guard let savedBook = books?.first else { return }
        savedBook.type = view.label
        try? context.save()
        switch view {
        case .library:
            self.wishlistBooks.removeAll(where: { $0.id == book?.id })
        case .wishlist:
            self.libraryBooks.removeAll(where: { $0.id == book?.id })
        }
    }
    
    func deleteBook(book: BooksModel?, forView view: BookStoreType) {
        let context = self.dbController.container.viewContext
        let fetchRequest = SavedBooks.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id = %@", (book?.id ?? ""))
        let books = try? context.fetch(fetchRequest)
        for book in (books ?? []) {
            context.delete(book)
        }
        try? context.save()
        switch view {
        case.library:
            self.libraryBooks.removeAll(where: { $0.id == book?.id })
        case .wishlist:
            self.wishlistBooks.removeAll(where: { $0.id == book?.id })
        }
    }
    
    //MARK: Search Screen
    func searchForBooks(withQuery query: String) {
        networkLayer.searchForBooks(withQuery: query, maxResults: 30) { response in
            switch response {
            case .success(let books):
                self.searchResults = books.items
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
}
