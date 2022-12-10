//
//  MovieListScreen.swift
//  Filmatik
//
//  Created by Marco Gomiero on 03.12.22.
//  Copyright © 2022. All rights reserved.
//

import SwiftUI
import shared

struct MovieListScreen: View {
    
    let homeState: HomeState
    let onRetryClick: (() -> Void)?
    
    var body: some View {
        VStack(alignment: .leading) {
            
            Text("Filmatik")
                .bold()
                .padding(16)
                .font(.largeTitle)
            
            if homeState.isLoading {
                LoadingView()
            } else if let errorData = homeState.errorData {
                ErrorView(errorData: errorData, onRetryClick: onRetryClick)
            } else if let homeData = homeState.homeData {
                VStack(alignment: .leading) {
                    Header(title: homeData.trendingHeaderTitle)
                    TrendingMoviesView(
                        homeData: homeData
                    )
                    
                    
                    if let feauturedHeaderTitle = homeData.featuredHeaderTitle,
                       let featuredMovie = homeData.featuredMovie {
                        FeaturedMovieView(
                            title: feauturedHeaderTitle,
                            movie: featuredMovie
                        )
                    }
                    
                    Spacer()
                }
            }
        }
    }
}

private struct Header: View {
    
    let title: String
    
    var body: some View {
        Text(title)
            .padding(.horizontal, 16)
            .padding(.bottom, -8)
            .font(.title)
    }
}

private struct TrendingMoviesView: View {
    
    let homeData: HomeData
    
    var body: some View {
        
        ScrollView(.horizontal) {
            HStack {
                ForEach(homeData.trendingMovies, id: \.self) { movie in
                    NavigationLink(value: Route.movieDetail(movie.id)) {
                        ZStack {
                            RoundedRectangle(
                                cornerRadius: 16,
                                style: .continuous
                            )
                            .fill(.white)
                            .frame(width: 200, height: 240)
                            .shadow(radius: 6)
                            
                            VStack(alignment: .leading) {
                                AsyncImage(
                                    url: URL(string: movie.imageUrl),
                                    content: { image in
                                        image.resizable()
                                            .scaledToFill()
                                            .frame(width: 200, height: 200, alignment: .center)
                                            .cornerRadius(16, corners: [.topLeft, .topRight])
                                            .clipped()
                                        
                                    },
                                    placeholder: {
                                        ProgressView()
                                            .frame(width: 200, height: 200, alignment: .center)
                                    }
                                )
                                                            
                                Text(movie.title)
                                    .font(.body)
                                    .padding(.top, 4)
                                    .padding(.bottom, 8)
                                    .padding(.horizontal, 8)
                                    .lineLimit(1)
                            }
                            .frame(width: 200)
                            
                        }
                        .padding(.vertical, 16)
                        .padding(.leading, 16)
                        .padding(.trailing, 8)
                    }
                    .buttonStyle(PlainButtonStyle())
                }
            }
        }
    }
}

private struct FeaturedMovieView: View {
    let title: String
    let movie: MovieItem
    
    var body: some View {
        NavigationLink(value: Route.movieDetail(movie.id)) {
            
            VStack(alignment: .leading) {
                Header(title: title)
                
                ZStack {
                    RoundedRectangle(
                        cornerRadius: 16,
                        style: .continuous
                    )
                    .fill(.white)
                    .frame(height: 200)
                    .shadow(radius: 6)
                    
                    ZStack(alignment: .bottomLeading) {
                        AsyncImage(
                            url: URL(string: movie.imageUrl),
                            content: { image in
                                image.resizable()
                                    .scaledToFill()
                                    .frame(height: 200, alignment: .center)
                                    .overlay(
                                        Color.black
                                            .opacity(0.3)
                                    )
                                    .cornerRadius(16)
                                    .clipped()
                            },
                            placeholder: {
                                ProgressView()
                                    .frame(height: 200, alignment: .center)
                            }
                        )
                        
                        Text(movie.title)
                            .font(.title3)
                            .foregroundColor(.white)
                            .padding(16)
                            .lineLimit(1)
                    }
                }
                .padding(.top, 16)
                .padding(.horizontal, 16)
            }
        }
        .buttonStyle(PlainButtonStyle())
    }
}

private struct MovieListScreen_Previews: PreviewProvider {
    
    static let homeLoadingState = HomeState(
        isLoading: true,
        errorData: nil,
        homeData: nil
    )
    
    static let errorHomeState = HomeState(
        isLoading: false,
        errorData: ErrorData(
            message: "Something wrong here :(",
            buttonText: "Retry"
        ),
        homeData: nil
    )
    
    static let dataHomeState = HomeState(
        isLoading: false,
        errorData: nil,
        homeData: HomeData(
            trendingHeaderTitle: "Trending",
            trendingMovies: [
                MovieItem(
                    id: 0,
                    title: "A movie",
                    imageUrl: "https://image.tmdb.org/t/p/original//AokFVAl1JVooW1uz2V2vxNUxfit.jpg"
                ),
                MovieItem(
                    id: 1,
                    title: "Another movie",
                    imageUrl: ""
                ),
                MovieItem(
                    id: 2,
                    title: "Yet Another movie",
                    imageUrl: ""
                )
            ],
            featuredHeaderTitle: "Your next movie",
            featuredMovie:  MovieItem(
                id: 0,
                title: "A movie",
                imageUrl: "https://image.tmdb.org/t/p/original//AokFVAl1JVooW1uz2V2vxNUxfit.jpg"
            )
        )
    )
    
    static let noFeaturedHomeState = HomeState(
        isLoading: false,
        errorData: nil,
        homeData: HomeData(
            trendingHeaderTitle: "Trending",
            trendingMovies: [
                MovieItem(
                    id: 0,
                    title: "A movie",
                    imageUrl: "https://image.tmdb.org/t/p/original//AokFVAl1JVooW1uz2V2vxNUxfit.jpg"
                ),
                MovieItem(
                    id: 1,
                    title: "Another movie",
                    imageUrl: ""
                ),
                MovieItem(
                    id: 2,
                    title: "Yet Another movie",
                    imageUrl: ""
                )
            ],
            featuredHeaderTitle: nil,
            featuredMovie:  nil
        )
    )
    
    static var previews: some View {
        MovieListScreen(homeState: homeLoadingState, onRetryClick: {})
        MovieListScreen(homeState: errorHomeState, onRetryClick: {})
        MovieListScreen(homeState: dataHomeState, onRetryClick: {})
        MovieListScreen(homeState: noFeaturedHomeState, onRetryClick: {})
    }
}
