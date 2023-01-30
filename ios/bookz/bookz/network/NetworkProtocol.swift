//
//  NetworkProtocol.swift
//  bookz
//
//  Created by Aldana, Sal on 1/13/23.
//

import Foundation
import Alamofire
import AlamofireImage

enum OrderBy: String {
    case newest, relevance
}

class NetworkProtocol {
    static let shared = NetworkProtocol()
    static let baseURL : String = "https://www.googleapis.com/books/v1/volumes"
    static let baseSearchURL: String = "https://books.google.com/books/content"
    
    private func buildQuery(withSearch searchTerm: String,
                    orderOption: OrderBy = .newest,
                    language: String = "en",
                    type: String = "books",
                    maxResults: Int) -> String {
        var query = Self.baseURL
        query += "?q=\(searchTerm)"
        query += "&orderBy=\(orderOption.rawValue)"
        query += "&langRestrict=\(language)"
        query += "&printType=\(type)"
        query += "&maxResults=\(String(maxResults))"
        query += "&key=\(PrivateKeys.APIKey)"
        return query
    }
    
    func searchForBooks(withQuery query: String, maxResults: Int, completion: @escaping (Result<BooksResponse, Error>) -> Void) {
        let request = AF.request(self.buildQuery(withSearch: query, maxResults: maxResults))
        request.responseDecodable(of: BooksResponse.self) { response in
            switch response.result {
            case .success(let books):
                completion(.success(books))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    private func buildCoverQuery(id bookId: String) -> String {
        var query = Self.baseSearchURL
        query += "?id=\(bookId)"
        query += "&printsec=frontcover"
        query += "&img=1"
        query += "&zoom=1"
        query += "&edge=flat"
        query += "&source=gbs_api"
        query += "&key=\(PrivateKeys.APIKey)"
        return query
    }
    
    func fetchImage(forBook bookId: String, completion: @escaping (Result<Image, Error>)-> Void) {
        let requestURL = self.buildCoverQuery(id: bookId)
        AF.request(requestURL).responseImage { response in
            switch response.result {
            case.success(let image):
                completion(.success(image))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func fetchBook(forId bookId: String, completion: @escaping (Result<BookResponse, Error>) -> Void) {
        var query = Self.baseURL
        query += "/\(bookId)"
        query += "?key=\(PrivateKeys.APIKey)"
        let request = AF.request(query)
        request.responseDecodable(of: BookResponse.self) { response in
            switch response.result {
            case .success(let bookModel):
                completion(.success(bookModel))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
}
