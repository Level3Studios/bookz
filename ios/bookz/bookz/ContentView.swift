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
                request.responseJSON { data in
                    print(data)
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
