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
        user = randint(720, 1100)
        add_user_data(user, request['reference_movies'])
        self.model.train_model()
        movies = self.model.predict(user)
        return RecommendMoviesResponse(recommended_movies=movies)
