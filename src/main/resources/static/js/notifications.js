let options = {}
options.labels = {
    tip : "Совет",
    info : "Информация",
    success : "Успех",
    warning : "Предупреждение",
    alert : "Ошибка",
    async : "Загрузка"
}
options.messages = {
    async : "Пожалуйста, подождите..."
}
const n = new AWN(options)
document.addEventListener("submit", (e) => {
    e.preventDefault()
    let notifier = n.async(
        axios.post(e.target.action + `?${e.submitter.name}=` + e.submitter.value),
        resp => {
            n.success(`Плейлист успешно создан`)
        },
        resp => {
            n.alert(`Произошла ошибка ${resp.response.status}\n${resp.response.body}`)
        }
    )
})