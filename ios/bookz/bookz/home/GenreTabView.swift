//
//  GenreTabView.swift
//  bookz
//
//  Created by Aldana, Sal on 1/16/23.
//

import SwiftUI
import DynamicColor

struct GenreTabView: View {
    @State var pageIndex: GenreType = .adventure
    @EnvironmentObject private var viewModel: NetworkViewModel
    
    var body: some View {
        HStack {
            TabView(selection: $pageIndex) {
                ForEach(GenreType.allCases, id:\.self) { type in
                    GenreCardView(genre: type).tag(type.rawValue)
                }
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
            .onChange(of: pageIndex) { newValue in
                viewModel.updateSelectedGenre(genre: newValue)
            }
        }
    }
}

struct GenreCardView: View {
    var genre: GenreType
    
    var body: some View {
        HStack(alignment: .center) {
            Text(genre.displayLabel.uppercased())
                .font(.title2)
                .fontWeight(.heavy)
                .foregroundColor(.white)
                .padding(.leading, 6)
            
            Spacer()
            Image(genre.imageName)
                .resizable()
                .scaledToFill()
                .frame(width: 140)
                .padding(.trailing, 12)
            
        }
        .frame(maxWidth: .infinity, maxHeight: 200, alignment: .center)
        .background(genre.gradient)
        .cornerRadius(20)
        .padding(.all, 20)
    }
}

struct GenreTabView_Previews: PreviewProvider {
    static var previews: some View {
        GenreTabView()
    }
}
