//
//  NetworkViewModel.swift
//  bookz
//
//  Created by Aldana, Sal on 1/15/23.
//

import Foundation
import Alamofire

class NetworkViewModel: ObservableObject {
    // MARK: - Private variables
    private let networkLayer = NetworkProtocol.shared
    private var fantasyBooks: [BooksModel] = []
    private var adventureBooks: [BooksModel] = []
    private var romanceBooks: [BooksModel] = []
    private var scifiBooks: [BooksModel] = []
    
    // MARK: - Published variables
    @Published var top5Books: [BooksModel] = []
    @Published var scrollIndex: Int = -1
    
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
        scrollIndex = 0
        guard self.needsSearch(genre: genre) else {
            self.updateTop5List(forGenre: genre)
            return
        }
        networkLayer.fetchTop5(forQuery: genre.searchString, completion: { response in
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
}
