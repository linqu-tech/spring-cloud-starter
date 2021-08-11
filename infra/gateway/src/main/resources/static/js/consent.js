$("#reset-button").click(function () {
    var form = $('#consent-form');
    form.trigger("reset");
    form.submit();
})

function showError(error) {
    const element = $("#error");
    error && element.text(error);
    element.show();
}

function render(data) {
    $('#tips-client-id').text(data.clientId);
    $('#tips-principal-name').text(data.principalName);
    $('#input-client-id').val(data.clientId);
    $('#input-state').val(data.state);
    const scopes = [].concat(data.scopes).concat(data.approvedScopes);
    scopes.forEach(scope => {
        var checked = data.approvedScopes.indexOf(scope) > -1 ? 'checked="true"' : '';
        var ele = $('<div class="permission">' +
            '   <input class="permission-input" type="checkbox" name="scope" ' + checked + ' value="' + scope + '" id="' + scope + '">' +
            '   <label class="permission-label" for="ui">' + scope + '</label>' +
            '</div>');
        $('#permissions').append(ele);
    })
}

function initialize() {
    const query = window.location.href.split('?')[1];
    $.ajax({
        url: 'uaa/oauth2/consent' + (query ? '?' + query : ''),
        type: "get",
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        success: function (data) {
            console.log(data);
            render(data);
        },
        error: function () {
            showError("An error during consent initialization. Please, try again.");
        }
    });
}

initialize();
