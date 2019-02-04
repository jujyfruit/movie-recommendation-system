import recommender_pb2_grpc

from recommender_pb2 import RecommendMoviesResponse

class MovieRecommenderServicer(recommender_pb2_grpc.MovieRecommenderServicer):

    def recommend_movies(self, request, context):
        return RecommendMoviesResponse(recommended_movies=["m1", "m2"])