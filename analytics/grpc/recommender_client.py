import grpc
from grpc.recommender_pb2_grpc import MovieRecommenderStub
from grpc.recommender_pb2 import RecommendMoviesRequest

def run():
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = MovieRecommenderStub(channel)
        # print("-------------- GetFeature --------------")
        # guide_get_feature(stub)
        # print("-------------- ListFeatures --------------")
        # guide_list_features(stub)
        # print("-------------- RecordRoute --------------")
        # guide_record_route(stub)
        # print("-------------- RouteChat --------------")
        # guide_route_chat(stub)
        print(stub.recommend_movies(RecommendMoviesRequest(reference_movies=["sdfds"])))


if __name__ == '__main__':
    # logging.basicConfig()
    run()