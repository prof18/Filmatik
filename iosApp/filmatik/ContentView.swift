import SwiftUI
import shared

struct ContentView: View {    
    @StateObject private var viewModel = MainViewModel(movieRepository: KotlinDependencies.shared.getMovieRepository() )
    
    var body: some View {
        NavigationStack {
            MovieListScreen(
                homeState: viewModel.homeState,
                onRetryClick: {
                    viewModel.getHomeState()
                }
            )
            .navigationDestination(for: Route.self) { route in
                switch route {
                case let .movieDetail(movieId):
                    MovieDetailScreen(
                        movieDetailState: viewModel.movieDetailState,
                        onAppear: {
                            viewModel.getMovie(movieId: movieId)
                        }
                    )
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
