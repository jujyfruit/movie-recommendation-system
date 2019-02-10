var $document = $(document);
$document.ready(function () {
    var pageSize = 20;

    var $movieSelect = $('#select-container');

    $movieSelect.select2({
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
                addFavouriteMoviesToReferenceTable(movies);
            }
        });
    }


    // page handling

    var $referenceMoviesHeader = $('#reference-movies-header');
    var $referenceMoviesContainer = $('#reference-movies-container');

    var $recommendedMoviesHeader = $('#recommended-movies-header');
    var $recommendedMoviesContainer = $('#recommended-movies-container');

    var $referenceMoviesTableBody = $('#reference-movies-table-body');
    var $recommendedMovieTableBody = $('#recommended-movies-table-body');

    $referenceMoviesHeader.click(function () {
        selectTab('reference');
    });

    $recommendedMoviesHeader.click(function () {
        selectTab('recommended');
    });

    function selectTab(tabName) {
        if (tabName === 'reference') {
            $referenceMoviesHeader.addClass('selected');
            $referenceMoviesContainer.addClass('selected');

            $recommendedMoviesHeader.removeClass('selected');
            $recommendedMoviesContainer.removeClass('selected');
        } else if (tabName === 'recommended') {
            $referenceMoviesHeader.removeClass('selected');
            $referenceMoviesContainer.removeClass('selected');

            $recommendedMoviesHeader.addClass('selected');
            $recommendedMoviesContainer.addClass('selected');
        }
    }


    // reference movies container

    var referencesMovies = [];

    $movieSelect.on('select2:select', function (e) {
        referencesMovies.push({name: e.params.data.text, source: 'system'});
        $movieSelect.val('').trigger('change');

        refreshReferenceMoviesTable();
    });

    $document.on('click', '.delete-cell', function (el) {
        var $target = $(el.target);
        removeReferenceMovie($target.data('name'), $target.data('source'));
    });

    function removeReferenceMovie(name, source) {
        var idx = -1;
        for (var i = 0; i < referencesMovies.length; i++) {
            if (referencesMovies[i].name == name && referencesMovies[i].source == source) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            return;
        }

        referencesMovies.splice(idx, 1);
        refreshReferenceMoviesTable();
    }

    function refreshReferenceMoviesTable() {
        $referenceMoviesTableBody.empty();

        for (var i = 0; i < referencesMovies.length; i++) {
            addReferenceMovieRow(referencesMovies[i]);
        }
    }

    function addReferenceMovieRow(movie) {
        var movieName = movie.name;
        var year = '';

        if (movie.source == 'system') {
            movieName = movieName.substring(0, movieName.length - 7);
            year = movie.name.substring(movie.name.length - 5, movie.name.length - 1);
        }

        var $tr = $('<tr/>', {});
        var $nameCell = $('<td/>', {});
        $nameCell.append($('<div/>', {
            class: 'name-cell',
            text: movieName
        }));
        $tr.append($nameCell);

        var $yearCell = $('<td/>', {});
        $yearCell.append($('<div/>', {
            text: year
        }));
        $tr.append($yearCell);

        var $iconCell = $('<td/>', {});
        var $iconCellDiv = $('<div/>', {});
        var $iconWrapper = $('<div/>', {class: 'icon-box small'});
        var $iconCellDef = $('<img/>', {src: '/img/themoviedb-logo.png', alt: 'source image'});
        if (movie.source == 'system') {
            $iconCellDef = $('<i/>', {class: 'fas fa-film'});
        }
        $iconWrapper.append($iconCellDef);
        $iconCellDiv.append($iconWrapper);
        $iconCell.append($iconCellDiv);
        $tr.append($iconCell);

        var $deleteCell = $('<td/>', {class: 'delete-cell', 'data-name': movie.name, 'data-source': movie.source});
        var $deleteCellDiv = $('<div/>', {'data-name': movie.name, 'data-source': movie.source});
        $deleteCellDiv.append($('<i/>', {
            class: 'fas fa-times', 'data-name': movie.name, 'data-source': movie.source
        }));
        $deleteCell.append($deleteCellDiv);
        $tr.append($deleteCell);

        $referenceMoviesTableBody.append($tr);
    }

    function addFavouriteMoviesToReferenceTable(favouriteMovies) {
        for (var i = 0; i < favouriteMovies.length; i++) {
            referencesMovies.push({name: favouriteMovies[i], source: 'themoviedb'});
        }

        refreshReferenceMoviesTable();
    }

    // recommended tab

    var $getRecommendations = $('#get-recommendations');
    var $recommendationsContainer = $('#recommendations-container');
    var $noRecommendationsContainer = $('#no-recommendations-container');
    var $startHere = $('#start-here');

    selectRecommendationsTabView('no-recommendations');

    $getRecommendations.click(function () {
        $.ajax({
            url: '/api/getRecommendations',
            data: {
                referenceMovies: referencesMovies.map(function (it) {
                    return it.name
                })
            },
            success: function (recommendedMovies) {
                displayRecommendedMovies(recommendedMovies);
                selectTab('recommended');
            }
        })
    });

    $startHere.click(function() {
       selectTab('reference');
    });

    function displayRecommendedMovies(recommendedMovies) {
        $recommendedMovieTableBody.empty();

        var view = recommendedMovies.length > 0 ? 'has-recommendations' : 'no-recommendations';
        selectRecommendationsTabView(view);

        for (var i = 0; i < recommendedMovies.length; i++) {
            addRecommendedMovieRow(recommendedMovies[i], i + 1);
        }
    }

    function selectRecommendationsTabView(view) {
        if (view == 'has-recommendations') {
            $recommendationsContainer.show();
            $noRecommendationsContainer.hide();
        } else {
            $recommendationsContainer.hide();
            $noRecommendationsContainer.show();
        }
    }

    function addRecommendedMovieRow(movie, idx) {
        var movieName = movie.substring(0, movie.length - 7);
        var year = movie.substring(movie.length - 5, movie.length - 1);

        var $tr = $('<tr/>', {});
        var $idxCell = $('<td/>', {});
        $idxCell.append($('<div/>', {
            text: idx
        }));
        $tr.append($idxCell);

        var $nameCell = $('<td/>', {});
        $nameCell.append($('<div/>', {
            class: 'name-cell',
            text: movieName
        }));
        $tr.append($nameCell);

        var $yearCell = $('<td/>', {});
        $yearCell.append($('<div/>', {
            text: year
        }));
        $tr.append($yearCell);

        var $detailsCell = $('<td/>', {});
        var $detailsWrapper = $('<div/>', {});
        $detailsWrapper.append($('<a/>', {
            class: 'details-link',
            text: 'Find on IMDb',
            target: '_blank',
            rel: 'noopener',
            href: 'https://imdb.com/find?q=' + movie
        }));
        $detailsCell.append($detailsWrapper);
        $tr.append($detailsCell);

        $recommendedMovieTableBody.append($tr);
    }
});