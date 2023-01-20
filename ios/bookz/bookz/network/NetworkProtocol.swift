//
//  NetworkProtocol.swift
//  bookz
//
//  Created by Aldana, Sal on 1/13/23.
//

import Foundation
import Alamofire


enum OrderBy: String {
    case newest, relevance
}

class NetworkProtocol {
    static let shared = NetworkProtocol()
    static let baseURL : String = "https://www.googleapis.com/books/v1/volumes"
    
    func buildQuery(withSearch searchTerm: String,
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
}
