function addAuthDialog() {
    document.querySelector('body').insertAdjacentHTML('beforeend', `
        <dialog id="auth-dialog">
            <form id="auth-form" method="dialog">
            <fieldset>
                <legend>Вход</legend>
                <p>
                    <label for="username">Пользователь</label>
                    <input type="text" name="username" required autofocus>
                </p>
                <p>
                    <label for="password">Пароль</label>
                    <input type="password" name="password" required autocomplete="on">
                </p>
            </fieldset>
            <hr>
            <button type="submit">Войти</button>
            </form>
        </dialog>`
    );

    document.getElementById('auth-dialog').addEventListener('cancel', (event) => {
        event.preventDefault();
    });

    document.getElementById('auth-dialog').addEventListener('close', (event) => {
        fetch('/login.html', {
                method: 'POST',
                body: new FormData(document.getElementById('auth-form'))
            }).then(response => {
                if (response.url.includes("error")) {
                    document.getElementById('auth-dialog').showModal();
                }
            });
    });
}
