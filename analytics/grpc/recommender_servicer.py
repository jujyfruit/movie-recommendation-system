import recommender_pb2_grpc

class MovieRecommenderServicer(recommender_pb2_grpc.MovieRecommenderServicer):

    def recommend_movies(self, request, context):
        return ["m1", "m2"]