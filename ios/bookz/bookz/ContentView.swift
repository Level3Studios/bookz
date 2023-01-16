//
//  ContentView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/13/23.
//

import SwiftUI
import Alamofire

struct ContentView: View {
    let networkLayer = NetworkProtocol()
    
    var body: some View {
        VStack {
            Button("Fetch", action: {
                let request = AF.request(networkLayer.buildQuery(withSearch: "+subject:adventure", maxResults: 5))
                request.responseDecodable(of: BooksResponse.self) { response in
                    switch response.result {
                    case .success(let books):
                        print(books.items.first)
                    case .failure(let error):
                        print(error.localizedDescription)
                    }
                }
            })
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) {
            ContentView().preferredColorScheme($0)
        }
    }
}
