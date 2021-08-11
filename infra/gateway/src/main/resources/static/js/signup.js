$("#username").click(function () {
    $("#error").hide();
})

$("#password").click(function () {
    $("#error").hide();
})

function showError(error) {
    const element = $("#error");
    error && element.text(error);
    element.show();
}

function getParameter(name, url = window.location.href) {
    return new URL(url).searchParams.get(name);
}

$('#signup').submit(function (e) {
    console.log(`submit form`);
    e.preventDefault();
    const username = $("input[id='username']").val();
    const password = $("input[id='password']").val();

    if (username.length < 3 || password.length < 6) {
        showError("Username must be at least 3 characters and password - at least 6");
        return;
    }

    if (username && password) {
        $.ajax({
            url: 'uaa/signup',
            datatype: 'json',
            type: "post",
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            data: JSON.stringify({
                username: username,
                password: password
            }),
            success: function () {
                alert("Sign Up successfully!");
            },
            error: function (xhr) {
                if (xhr.status === 400) {
                    showError("Sorry, account with the same name already exists.");
                } else {
                    showError("An error during account creation. Please, try again.");
                }
            }
        });
    } else {
        alert("Please, fill all the fields.");
    }
});

$(document).ready(function () {
    const redirect = getParameter("redirect");
    if (redirect) {
        $('#login-url').attr('href', 'login.html?redirect=' + encodeURIComponent(redirect));
    } else {
        $('#login-url').attr('href', 'login.html');
    }
});
