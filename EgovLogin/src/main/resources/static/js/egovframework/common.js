const contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

function notNullCheck(value) {
    return !(value === '' || value == null || (typeof value == 'object' && !Object.keys(value).length));
}
