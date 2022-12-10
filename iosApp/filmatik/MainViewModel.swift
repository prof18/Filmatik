//
//  MainViewModel.swift
//  Filmatik
//
//  Created by Marco Gomiero on 03.12.22.
//  Copyright © 2022. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

@MainActor
class MainViewModel: ObservableObject {
        
    @Published public var homeState: HomeState = HomeState(isLoading: true, errorData: nil, homeData: nil)
    
    @Published public var movieDetailState: DetailState = DetailState(isLoading: true, errorData: nil, movieItem: nil)
    
    let movieRepository: MovieRepository
    
    private let homeLoadingState = HomeState(isLoading: true, errorData: nil, homeData: nil)
    private let detailLoadingState = DetailState(isLoading: true, errorData: nil, movieItem: nil)
    private var movies: [Movie] = []
    
    init(movieRepository: MovieRepository) {
        self.movieRepository = movieRepository
        getHomeState()
    }
    
    func getHomeState()  {
        homeState = homeLoadingState
        Task {
            do {
                let result = try await asyncFunction(for: movieRepository.getPopularMoviesNative())
                print("Got result: \(result)")
                
                if let movies = (result as? DataResultSuccess)?.data {
                    self.movies = movies as? [Movie] ?? []
                    homeState = HomeState(
                        isLoading: false,
                        errorData: nil,
                        homeData: generateHomeData()
                    )
                } else {
                    homeState = HomeState(
                        isLoading: false,
                        errorData: getGenericErrorData(),
                        homeData: nil
                    )
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }
    
    // TODO: localize
    func getMovie(movieId: Int32) {
        movieDetailState = detailLoadingState
        if let movie = movies.first(where: { $0.id == movieId }) {
             movieDetailState = DetailState(
                isLoading: false,
                errorData: nil,
                movieItem: movie.toMovieDetailItem()
             )
        } else {
            movieDetailState = DetailState(
                isLoading: false,
                errorData: ErrorData(
                    message: "This is embarrassing. I was unable to found what you have requested",
                    buttonText: "Retry"
                ),
                movieItem: nil
            )
        }
    }
    
    // TODO: localize
    private func getGenericErrorData() -> ErrorData {
        ErrorData(
            message: "Something is not working :(",
            buttonText: "Retry"
        )
    }
    
    private func generateHomeData() -> HomeData {
        // TODO: localise
        let trendingList = movies.map { $0.toMovieItem() }
        
        let featuredMovie = movies.randomElement()?.toMovieItem()
        let featuredTitle: String?
        if featuredMovie != nil {
            featuredTitle =  "Your next movie"
        } else {
            featuredTitle = nil
        }
        
        return HomeData(
            trendingHeaderTitle: "Trending",
            trendingMovies: trendingList,
            featuredHeaderTitle: featuredTitle,
            featuredMovie: featuredMovie
        )
    }
}
