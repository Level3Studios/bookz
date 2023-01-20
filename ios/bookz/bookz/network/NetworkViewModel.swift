//
//  NetworkViewModel.swift
//  bookz
//
//  Created by Aldana, Sal on 1/15/23.
//

import Foundation
import Alamofire

class NetworkViewModel: ObservableObject {
    // MARK: Private variables
    private let networkLayer = NetworkProtocol.shared
    
    // MARK: Published variables
    @Published var fantasyBooks: [BooksModel] = []
    @Published var adventureBooks: [BooksModel] = []
    @Published var romanceBooks: [BooksModel] = []
    @Published var scifiBooks: [BooksModel] = []
    
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
    }
    
    public func updateSelectedGenre(genre: GenreType) {
        guard self.needsSearch(genre: genre) else { return }
        self.fetchTop5(forQuery: genre.searchString, completion: { response in
            switch response {
            case .success(let books):
                print(books.items.count)
                self.updateBooks(response: books.items, genre: genre)
            case .failure(let error):
                print(error.localizedDescription)
            }
        })
    }
    
    private func fetchTop5(forQuery query: String, completion: @escaping (Result<BooksResponse, Error>) -> Void) {
        let request = AF.request(networkLayer.buildQuery(withSearch: query, maxResults: 5))
        request.responseDecodable(of: BooksResponse.self) { response in
            switch response.result {
            case .success(let books):
                completion(.success(books))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
}
