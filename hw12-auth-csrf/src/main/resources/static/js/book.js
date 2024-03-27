const api = '/api/book';

function getEntityJson() {
    return {
        id: document.getElementById('entity.id').value,
        name: document.getElementById('entity.name').value,
        author: {
            name: document.getElementById('author').value
        },
        genre: {
            name: document.getElementById('genre').value
        }
    };
}

function fillEntityForm(json) {
    document.getElementById('entity.id').value = json.id;
    document.getElementById('entity.name').value = json.name;
    document.getElementById('author').value = json.author.name;
    document.getElementById('genre').value = json.genre.name;
}

function fillInputOptions(option_name) {
    fetch('/api/' + option_name, {redirect: 'manual'})
        .then(response => response.json())
        .then(json => {
            let rows = '';
            json.map(row => {
                rows += `<option value="${row.name}">`;
            });
            document.getElementById(option_name + 's').innerHTML = rows;
        })
        .catch((error) => {
            console.log(error);
        });
}

function getEntitiesRows(json) {
    fillInputOptions('author');
    fillInputOptions('genre');
}

function buildEntityRow(entity) {
    return `
        <tr id='${entity.id}'>
            <td><a href='#' onclick='showEntityDialog("${entity.id}")'>${entity.name}</a></td>
            <td>${entity.author.name}</td>
            <td>${entity.genre.name}</td>
            <td>
                <a href='/comment.html?bookId=${entity.id}'><button>Комментарии</button></a>
                <button onclick='editEntityDialog("${entity.id}")'>Править</button>
                <button onclick='deleteEntityDialog("${entity.id}")'>Удалить</button>
            </td>
        </tr>`;
}
