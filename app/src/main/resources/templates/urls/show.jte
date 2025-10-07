@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.model.Url
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>
        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <th>ID</th>
                <th>${page.getUrl().getId()}</th>
            </tr>
            <tr>
                <th>Имя</th>
                <th>${page.getUrl().getName()}</th>
            </tr>
            <tr>
                <th>Дата создания</th>
                <th>${String.valueOf(page.getUrl().getCreatedAt())}</th>
            </tr>
            </tbody>
            </table>

        <h2 class="mt-5">Проверки</h2>
        <form method="post" action="/urls/${page.getUrl().getId()}/checks">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <th class="col-1">ID</th>
                    <th class="col-1">Код ответа</th>
                    <th>title</th>
                    <th>h1</th>
                    <th>description</th>
                    <th class="col-2">Дата проверки</th>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
`
)
