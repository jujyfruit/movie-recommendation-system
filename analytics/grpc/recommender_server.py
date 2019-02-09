import grpc
from time import sleep
from concurrent import futures
from grpc.recommender_pb2_grpc import add_MovieRecommenderServicer_to_server
from grpc.recommender_servicer import MovieRecommenderServicer

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_MovieRecommenderServicer_to_server(
        MovieRecommenderServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    try:
        while True:
            sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    serve()
