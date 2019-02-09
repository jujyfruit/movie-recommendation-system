import pandas as pd
import time


def get_data_titles():
    return ['movie id', 'movie title', 'release date', 'video release date', 'IMDb URL', 'unknown', 'Action',
            'Adventure',
            'Animation', 'Children\'s', 'Comedy', 'Crime', 'Documentary', 'Drama', 'Fantasy',
            'Film-Noir', 'Horror', 'Musical', 'Mystery', 'Romance', 'Sci-Fi', 'Thriller', 'War', 'Western']


def add_user_data(user_id, movies):
    ts = int(time.time())
    file = open("recommender/u.data", "a")

    for m_id in movies:
        file.write('{0}	{1}	{2}	{3}\n'.format(str(user_id), str(m_id), str(5), str(ts)))

    file.close()


def find_mapped_movies(movies):
    i_cols = get_data_titles()
    items = pd.read_csv('recommender/u.item', sep='|', names=i_cols, encoding='latin-1')
    print(items[['movie title', 'movie id']].loc[items['movie title'].isin(movies)]['movie id'])
    return items[['movie title', 'movie id']].loc[items['movie title'].isin(movies)]['movie id'].tolist()

class DataPreparator:

    def __init__(self):
        self.r_cols = ['user_id', 'movie_id', 'rating', 'unix_timestamp']
        self.ratings = pd.read_csv('recommender/u.data', sep='\t', names=self.r_cols, encoding='latin-1')
        self.ratings_train = pd.read_csv('recommender/ua.base', sep='\t', names=self.r_cols, encoding='latin-1')
        self.ratings_test = pd.read_csv('recommender/ua.test', sep='\t', names=self.r_cols, encoding='latin-1')
        i_cols = get_data_titles()
        self.items = pd.read_csv('recommender/u.item', sep='|', names=i_cols, encoding='latin-1')
        self.prepare_data()

    def prepare_data(self):
        self.ratings['user_id'] = self.ratings['user_id'].fillna(0)
        self.ratings['movie_id'] = self.ratings['movie_id'].fillna(0)
        self.ratings['rating'] = self.ratings['rating'].fillna(self.ratings['rating'].mean())


