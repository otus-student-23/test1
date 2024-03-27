const saveDialog = document.getElementById('entity-dialog');
const submitButton = document.getElementById('submit-button');

document.getElementById('add-button').addEventListener('click', () => {
    resetEntityDialog();
    submitButton.value = 'POST';
    submitButton.innerHTML = 'Добавить';
    document.getElementById('entity-fieldset').disabled = false;
    saveDialog.showModal();
});

document.getElementById('list-button').addEventListener('click', () => {
    loadEntities();
});

saveDialog.addEventListener('close', async (e) => {
    var httpMethod = saveDialog.returnValue;
    saveDialog.returnValue = '';
    if (['PUT','POST','DELETE'].indexOf(httpMethod) < 0 || !document.forms['entity-form'].checkValidity()) {
        //saveDialog.reportValidity();
        return;
    };
    let entity = getEntityJson();
    let response = await fetch(api + ((entity.id === '') ? '' : '/' + entity.id), {
        method: httpMethod,
        headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
        },
        redirect: 'manual',
        body: JSON.stringify(entity)
    });
    if (response.ok) {
        loadEntities();
    } else {
        let body = await response.text();
        saveDialog.showModal();
        if (response.status === 0) {
            document.getElementById('auth-dialog').showModal();
        } else {
            document.getElementById('entity-result').innerHTML = `[CODE: ${response.status}] ${escapeHtml(body)}`;
        }
    }
});

function resetEntityDialog() {
    document.forms['entity-form'].reset();
    submitButton.value = '';
    submitButton.innerHTML = 'X';
    document.getElementById('entity-result').innerHTML = '';
    document.getElementById('entity-fieldset').disabled = true;
    document.getElementById('entity.id').value = '';
}

function showEntityDialog(id) {
    resetEntityDialog();
    fetch(api + '/' + id, {
            headers: {
                'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
            },
            redirect: 'manual'
        })
        .then(response => response.json())
        .then(json => {
            fillEntityForm(json);
            saveDialog.showModal();
        })
        .catch((error) => {
            console.log(error);
            window.location.reload();
        });
}

function editEntityDialog(id) {
    //alert(elem.parentNode.parentNode.id);
    showEntityDialog(id);
    submitButton.value = 'PUT';
    submitButton.innerHTML = 'Сохранить';
    document.getElementById('entity-fieldset').disabled = false;
}

function deleteEntityDialog(id) {
    showEntityDialog(id);
    submitButton.value = 'DELETE';
    submitButton.innerHTML = 'Удалить';
}

function loadEntities() {
    fetch(api, {
            headers: {
                'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
            },
            redirect: 'manual'
        })
        .then(response => response.json())
        .then(json => {
            let rows = '';
            json.map(row => {
                rows += buildEntityRow(row);
            })
            document.getElementById('entities').innerHTML = rows;
            getEntitiesRows(json);
        })
        .catch((error) => {
            console.log(error);
            //window.location.reload();
        });
}

window.onload = function() {
    addAuthDialog();
    loadEntities();
}

function escapeHtml(unsafe) {
    return unsafe
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
}