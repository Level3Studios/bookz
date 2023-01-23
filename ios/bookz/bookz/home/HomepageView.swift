//
//  HomepageView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/21/23.
//

import SwiftUI

struct HomepageView: View {
    @EnvironmentObject private var viewModel: NetworkViewModel
    var body: some View {
        VStack {
            GenreTabView()
                .frame(height: 220)
            Top5ListView(books: viewModel.top5Books)
        }
        .padding()
    }
}

struct HomepageView_Previews: PreviewProvider {
    static var previews: some View {
        HomepageView()
    }
}
