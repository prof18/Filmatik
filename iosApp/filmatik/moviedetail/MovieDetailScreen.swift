//
//  MovieDetailScreen.swift
//  Filmatik
//
//  Created by Marco Gomiero on 08.12.22.
//  Copyright © 2022. All rights reserved.
//

import SwiftUI
import shared
import Foundation

struct MovieDetailScreen: View {
    
    let movieDetailState: DetailState
    let onAppear: () -> Void
    
    var body: some View {
        VStack {
            if movieDetailState.isLoading {
                LoadingView()
            } else if let errorData = movieDetailState.errorData {
                ErrorView(errorData: errorData, onRetryClick: nil)
            } else if let movieItem = movieDetailState.movieItem {
                MovieDetailView(movieItem: movieItem)
            }
        }
        .onAppear {
            onAppear()
        }
    }
}

struct MovieDetailView: View {
    
    let movieItem: MovieDetailItem
    
    var body: some View {
        VStack(alignment: .leading) {
            AsyncImage(
                url: URL(string: movieItem.imageUrl),
                content: { image in
                    image.resizable()
                        .frame(height: 250)
                        .clipped()
                },
                placeholder: {
                    ProgressView()
                        .frame(height: 200, alignment: .center)
                }
            )
            
            Text(movieItem.title)
                .padding(.bottom, -8)
                .padding(.horizontal, 16)
                .font(.largeTitle)
            
            Text(movieItem.content)
                .padding(.top, 16)
                .padding(.horizontal, 16)
                .font(.body)
            
            Spacer()
        }
    }
}

struct MovieDetailScreen_Previews: PreviewProvider {
    
    static let loadingState = DetailState(
        isLoading: true,
        errorData: nil,
        movieItem: nil
    )
    
    static let errorState = DetailState(
        isLoading: false,
        errorData: ErrorData(
            message: "Something wrong here :(",
            buttonText: "Retry"
        ),
        movieItem: nil
    )
    
    static let detailState = DetailState(
        isLoading: false,
        errorData: nil,
        movieItem: MovieDetailItem(
            id: 1,
            title: "A movie",
            imageUrl: "https://image.tmdb.org/t/p/original//AokFVAl1JVooW1uz2V2vxNUxfit.jpg",
            content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
        )
    )
    
    static var previews: some View {
        MovieDetailScreen(movieDetailState: loadingState, onAppear: {})
        MovieDetailScreen(movieDetailState: errorState, onAppear: {})
        MovieDetailScreen(movieDetailState: detailState, onAppear: {})
    }
}
