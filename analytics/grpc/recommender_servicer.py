import sys
from os import path
sys.path.append( path.dirname( path.dirname( path.abspath(__file__) ) ) )

from recommender_pb2_grpc import *
from recommender.data_preparator import *
from random import randint
from recommender.model import *

from recommender_pb2 import RecommendMoviesResponse


class MovieRecommenderServicer(MovieRecommenderServicer):

    def __init__(self):
        self.model = Model()

    def recommend_movies(self, request, context):
        add_user_data(randint(720, 1100), request['movies'])
        self.model.train_model()
        movies = self.model.predict(request['user_id'])
        return RecommendMoviesResponse(recommended_movies=movies)
