$("#username").click(function () {
    $("#error").hide();
})

$("#password").click(function () {
    $("#error").hide();
})

function getParameter(name, url = window.location.href) {
    return new URL(url).searchParams.get(name);
}

$(document).ready(function () {
    if (getParameter("error")) {
        $("#error").show();
    }
    const redirect = getParameter("redirect");
    if (redirect) {
        $('#login-form').attr('action', '/uaa/login?redirect=' + encodeURIComponent(redirect));
        $('#signup-url').attr('href', 'signup.html?redirect=' + encodeURIComponent(redirect));
    } else {
        $('#login-form').attr('action', '/uaa/login');
        $('#signup-url').attr('href', 'signup.html');
    }
});
