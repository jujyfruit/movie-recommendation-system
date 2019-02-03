$(document).ready(function () {
    var pageSize = 20;

    $('#select-container').select2({
        ajax: {
            url: "/api/movies",
            dataType: 'json',
            delay: 250,
            width: 'resolve',
            data: function (params) {
                return {
                    searchTerm: params.term || '', // search term
                    page: params.page || 0,
                    size: pageSize
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 0;

                return {
                    results: data.content.map(function (it) {
                        return {id: it.id, text: it.name};
                    }),
                    pagination: {
                        more: (params.page * pageSize) < data.totalElements
                    }
                };
            },
            cache: true
        },
        placeholder: 'Search for a movie'
    });

    $('#get-recommendations').click(function () {
        $.ajax({
            url: '/api/getRecommendations',
            data: {
                referenceMovies: $('#select-container').select2('data').map(function (it) {
                    return it.text;
                })
            },
            success: function (recommendedMovies) {
                displayRecommendedMovies(recommendedMovies);
            }
        })
    });

    function displayRecommendedMovies(recommendedMovies) {
        var $recommendations = $('#recommendations');
        $recommendations.empty();

        for (var i = 0; i < recommendedMovies.length; i++) {
            var $div = $("<div>", {text: recommendedMovies[i]});
            $recommendations.append($div);
        }
    }


    var theMovieDbToken, theMovieDbSessionId;

    $('#the-movie-db').click(function () {
        if (!theMovieDbSessionId) {
            $.ajax({
                url: '/api/getMovieDbAllowAccess',
                success: function (data) {
                    theMovieDbToken = data.token;
                    handleGetMovieDbAllowAccessUrlSuccess(data.url);
                }
            });
        }
    });

    function handleGetMovieDbAllowAccessUrlSuccess(url) {
        window.open(url, '_blank');
        var interval = setInterval(function () {
            $.ajax({
                url: '/api/getMovieDbSessionId',
                data: {
                    token: theMovieDbToken
                },
                success: function (sessionId) {
                    console.log('session id', sessionId);
                    if (sessionId) {
                        getFavouriteMovies(sessionId);
                        clearInterval(interval);
                    }
                }
            });
        }, 2000);
    }

    function getFavouriteMovies(sessionId) {
        $.ajax({
            url: '/api/getMovieDbFavouriteMovies',
            data: {
                sessionId: sessionId
            },
            success: function (movies) {
                console.log('favourite movies', movies)
            }
        });
    }
});