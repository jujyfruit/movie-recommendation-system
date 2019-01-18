$(document).ready(function () {
    console.log('ready');
    var pageSize = 20;

    $('#select-container').select2({
        ajax: {
            url: "/api/movies",
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    searchTerm: params.term || '', // search term
                    page: params.page || 1,
                    size: pageSize
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;

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
        placeholder: 'Search for a repository'
    });
});