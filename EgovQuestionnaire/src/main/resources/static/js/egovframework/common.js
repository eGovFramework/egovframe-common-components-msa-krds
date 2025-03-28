const contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

function notNullCheck(value) {
    return !(value === '' || value == null || (typeof value == 'object' && !Object.keys(value).length));
}

// 키보드 접근성
document.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        const targetElement = event.target;
        if (targetElement.hasAttribute("onclick")) {
            targetElement.onclick();
        }
    }
});

