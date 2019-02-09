import numpy as np
import turicreate

from recommender.data_preparator import *


class Model:

    def __init__(self):
        self.dp = DataPreparator()
        self.n_users = self.dp.ratings.user_id.unique().shape[0]
        self.n_items = self.dp.ratings.movie_id.unique().shape[0]
        self.data_matrix = np.zeros((self.n_users, self.n_items))
        self.fill_data_matrix()
        self.train_data = turicreate.SFrame(self.dp.ratings_train)
        self.test_data = turicreate.SFrame(self.dp.ratings_test)
        self.model = None
        self.recommendation = None

    def fill_data_matrix(self):
        for line in self.dp.ratings.itertuples():
            self.data_matrix[int(line[1]) - 1, int(line[2]) - 1] = line[3]

    def train_model(self, type_of_recommendation='popularity'):

        if type_of_recommendation == 'popularity':
            self.model = turicreate.popularity_recommender.create(self.train_data, user_id='user_id',
                                                                  item_id='movie_id',
                                                                  target='rating')
        else:
            self.model = turicreate.item_similarity_recommender.create(self.train_data, user_id='user_id',
                                                                       item_id='movie_id',
                                                                       target='rating', similarity_type='cosine')

    def predict(self, user_id):
        return self.model.recommend(users=[user_id], k=10)


if __name__ == '__main__':
    request = {'user_id': 711, 'movies': ['Toy Story (1995)', 'Brothers McMullen, The (1995)', 'Strange Days (1995)']}
    add_user_data(request['user_id'], find_mapped_movies(request['movies']))
    mm = Model()
    mm.train_model()
    movies = mm.predict(request['user_id'])
    print(movies)
